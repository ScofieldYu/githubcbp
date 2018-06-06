/*
 * @(#)UserController.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.controller;

import com.alibaba.fastjson.JSONArray;
import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.BDLEmployee;
import com.blchina.cbp.dto.EmployeeDTO;
import com.blchina.cbp.service.interfaces.UserService;
import com.blchina.cbp.service.interfaces.WxService;
import com.blchina.common.util.blchina.BlchinaUtil;
import com.blchina.common.util.encode.EncodeUtil;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.redis.RedisUtil;
import com.blchina.common.util.string.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Properties;

/**
 * ipad操作controller
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
@RequestMapping("/user")
@RestController
public class UserController {
	private Logger log = Logger.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private WxService wxService;
	@Autowired
	private Properties systemConfig;

	/**
	 * 用户登录
	 *
	 * @param jsonObject
	 */
	@RequestMapping("/userLogin")
	public WeixinResult userLogin(@RequestBody JSONObject jsonObject) {
		WeixinResult res = new WeixinResult();
		res.setCode(CBPConstant.CODE_NULL_PARAM);
		res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
		try {
			String username = jsonObject.getString("username");
			String passwd = jsonObject.getString("passwd");
			String deviceid = jsonObject.getString("deviceid");
			log.info("用户登录，username=" + username + ",deviceid=" + deviceid);
			// 判断
			if (StringUtil.isNullorEmpty(username) || StringUtil.isNullorEmpty(passwd)
					|| StringUtil.isNullorEmpty(deviceid)) {
				return res;
			}

			int result = 0; //= userService.userLogin(username, passwd, deviceid);
			// 测试用户，上线之后删除
			// if ("admin".equals(username) || username.contains("SAP") || true)
			// {
			// result = 0;
			// }
			if (result == 1) {
				res.setCode(CBPConstant.CODE_FAILURE);
				res.setMessage("用户名不存在");
				return res;
			} else if (result == 2) {
				res.setCode(CBPConstant.CODE_FAILURE);
				res.setMessage("密码不正确");
				return res;
			} else {

				EmployeeDTO employeeDTO = new EmployeeDTO();
				String getEmployee = HttpUtil.postbody(
						systemConfig.getProperty("url.employee") + "/ee/getEmployeeByUserid",
						"{\"email\":\"" + username + "\"}");
				if (getEmployee == null || getEmployee.equals("")) {// 没有获取到员工信息
					return res;
				}
				String[] split = getEmployee.split(",");
				String response = HttpUtil.postbody(
						systemConfig.getProperty("url.employee") + "/ee/getEmployeeListByEmployeeId",
						"{\"employeeid\":" + split[0] + "}");
				System.out.println("ES系统返回" + response);
				List<BDLEmployee> list = JSONArray.parseArray(response, BDLEmployee.class);
				if (list == null || list.size() == 1 || list.size() == 0) {
					employeeDTO.setAccouttype("1");
				} else {
				   String response11 = HttpUtil.postbody(
							systemConfig.getProperty("url.employee") + "/ee/getEmployeeByEmployeeId",
							"{\"employeeid\":" + Integer.valueOf(split[0]) + "}");
				   JSONObject getJson=JSONObject.fromObject(response11);
		    	   BDLEmployee bdlEmployee = (BDLEmployee) JSONObject.toBean(getJson, BDLEmployee.class);
				   if(bdlEmployee.getTitleid().intValue()==CBPConstant.EmployeeTypeEnum.EMPLOYEE_SALES_DIRECTOR.getType()){
					  //销售总监
					  employeeDTO.setAccouttype("3");
				   }else if(bdlEmployee.getTitleid().intValue()==CBPConstant.EmployeeTypeEnum.EMPLOYEE_SALES_MANAGER.getType()){
					  //销售经理
					  employeeDTO.setAccouttype("2");
				   }else if("1001".equals(bdlEmployee.getCompanycode())){
					  //集团
					  employeeDTO.setAccouttype("4");
				   }
				}
				employeeDTO.setQrcodeurl(systemConfig.getProperty("bcp.url")+"/img/qrcode/"+split[1]+".jpg");
				String getwx = wxService.getEmployee(username);
				if (getwx == null) {
					employeeDTO.setAvatar("");
					employeeDTO.setWxname("");
				} else {
					String[] wxsplit = getwx.split(",");
					employeeDTO.setAvatar(wxsplit[0]);
					employeeDTO.setWxname(wxsplit[1]);
				}
				String token = EncodeUtil.getMD5For32(username + deviceid);
				employeeDTO.setEmployeeid(split[0]);
				employeeDTO.setName(split[3]);
				employeeDTO.setPhonenumber(split[2]);
				employeeDTO.setToken(token);
				employeeDTO.setBrandid(split[1]);
				employeeDTO.setStorename(split[4]);
				res.setData(employeeDTO);
				res.setCode(CBPConstant.CODE_SUCCESS);
				res.setMessage("登录成功");
				redisUtil.setex(token, BlchinaUtil.TTL_PAD_USER, username);
				return res;
			}
			// token存到redis里面去，接口调用验证用
			// redisUtil.setex(BlchinaUtil.REDIS_PAD_USER_KEY + username,
			// BlchinaUtil.TTL_PAD_USER, token);

		} catch (Exception e) {
			res.setCode(CBPConstant.CODE_FAILURE);
			res.setMessage("获取员工信息失败");
			e.printStackTrace();
			return res;
		}

	}

	/**
	 * 用户退出
	 * 
	 * @param jsonObject
	 */
	@RequestMapping("/logout")
	public WeixinResult logout(@RequestBody JSONObject jsonObject) {
		String token = jsonObject.getString("token");
		WeixinResult res = new WeixinResult();
		if (!StringUtil.isNullorEmpty(token)) {
			if (redisUtil.exists(token)) {
				redisUtil.del(token);
			}
			res.setCode(CBPConstant.CODE_SUCCESS);
			res.setMessage(CBPConstant.MESSAGE_SUCCESS);
		} else {
			res.setCode(CBPConstant.CODE_NULL_PARAM);
			res.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
		}
		return res;
	}

}
