/*
 * @(#)OderServiceImpl.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.impl;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.blchina.cbp.dao.CBPBoutiqueOrderMapper;
import com.blchina.cbp.dao.CBPCarMapper;
import com.blchina.cbp.dao.CBPCheckCarNumOrderMapper;
import com.blchina.cbp.dao.CBPDepositMapper;
import com.blchina.cbp.dao.CBPFourCustomerMapper;
import com.blchina.cbp.dao.CBPInsuranceOrderMapper;
import com.blchina.cbp.dao.CBPOrderFileMapper;
import com.blchina.cbp.dao.CBPOrderMapper;
import com.blchina.cbp.dao.CBPOutpocketMapper;
import com.blchina.cbp.dao.CBPReceiptMapper;
import com.blchina.cbp.dao.CBPTimeConfMapper;
import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.datamodel.sap.SAPResponse;
import com.blchina.cbp.datamodel.sap.SAPResult;
import com.blchina.cbp.datamodel.weixin.WeixinResult;
import com.blchina.cbp.dto.AllFeeDTO;
import com.blchina.cbp.dto.BDLBrandStore;
import com.blchina.cbp.dto.BDLFourCustomer;
import com.blchina.cbp.dto.BrandStore;
import com.blchina.cbp.dto.CardDTO;
import com.blchina.cbp.dto.ChangeAllFee;
import com.blchina.cbp.dto.ChooseCarDTO;
import com.blchina.cbp.dto.ContractSearchDTO;
import com.blchina.cbp.dto.CustomerInfoQueryDTO;
import com.blchina.cbp.dto.FileListDTO;
import com.blchina.cbp.dto.MonitorSearchDTO;
import com.blchina.cbp.dto.OrderDTO;
import com.blchina.cbp.dto.PaperCostsDTO;
import com.blchina.cbp.dto.ProcessDTO;
import com.blchina.cbp.dto.SapOrderDTO;
import com.blchina.cbp.dto.SearchCustomerDTO;
import com.blchina.cbp.dto.SignMonitorDTO;
import com.blchina.cbp.dto.UploadDTO;
import com.blchina.cbp.exception.BusinessException;
import com.blchina.cbp.exception.WXException;
import com.blchina.cbp.model.CBPBoutiqueOrder;
import com.blchina.cbp.model.CBPCar;
import com.blchina.cbp.model.CBPCheckCarNumOrder;
import com.blchina.cbp.model.CBPContract;
import com.blchina.cbp.model.CBPContractItem;
import com.blchina.cbp.model.CBPDeposit;
import com.blchina.cbp.model.CBPFourCustomer;
import com.blchina.cbp.model.CBPInsuranceOrder;
import com.blchina.cbp.model.CBPLogistics;
import com.blchina.cbp.model.CBPOrder;
import com.blchina.cbp.model.CBPTimeConf;
import com.blchina.cbp.service.interfaces.AuthService;
import com.blchina.cbp.service.interfaces.ConstractService;
import com.blchina.cbp.service.interfaces.ContractItemService;
import com.blchina.cbp.service.interfaces.ContractManageService;
import com.blchina.cbp.service.interfaces.DepositService;
import com.blchina.cbp.service.interfaces.InsuranceOrderService;
import com.blchina.cbp.service.interfaces.LogisticsService;
import com.blchina.cbp.service.interfaces.OrderService;
import com.blchina.cbp.service.interfaces.WxService;
import com.blchina.cbp.util.UploadUtil;
import com.blchina.common.util.encode.EncodeUtil;
import com.blchina.common.util.file.FileUtil;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.page.Page;
import com.blchina.common.util.string.StringUtil;

import net.sf.json.JSONObject;

/**
 * 订单操作OrderServiceImpl
 *
 * @author zhuchenglong
 * @since JDK 1.8
 */
@Service("OrderService")
public class OderServiceImpl implements OrderService {
	@Autowired
	private CBPOrderMapper cbpOrderMapper;
	@Autowired
	private Properties systemConfig;
	@Autowired
	private ConstractService constractService;
	@Autowired
	private AuthService authService;
	@Autowired
	private ContractItemService contractItemService;
	@Autowired
	private ContractManageService contractManageService;
	@Autowired
	private DepositService depositService;
	@Autowired
	private LogisticsService logisticsService;
	@Autowired
	private WxService wxService;
	@Autowired
	private CBPFourCustomerMapper fourCustomerMapper;
	@Autowired
	private CBPTimeConfMapper cbpTimeConfMapper;
	@Autowired
	private CBPOrderFileMapper cbpOrderFileMapper;
	@Autowired
	private CBPOutpocketMapper cbpOutpocketMapper;
	@Autowired
	private CBPReceiptMapper cbpReceiptMapper;
	@Autowired
	private CBPDepositMapper cbpDepositMapper;
	@Autowired
	private CBPCarMapper cbpCarMapper;
	@Autowired
	private CBPInsuranceOrderMapper cbpInsuranceOrderMapper;
	@Autowired
	private CBPBoutiqueOrderMapper cbpBoutiqueOrderMapper;
	@Autowired
	private CBPCheckCarNumOrderMapper cbpCheckCarNumOrderMapper;

	@Override
	public CBPOrder selectByPrimaryKey(Integer orderid) {
		return cbpOrderMapper.selectByPrimaryKey(orderid);
	}

	@Override
	public List<OrderDTO> getOrderListByphone(CBPOrder cbpOrder) {
		return cbpOrderMapper.getOrderListByphone(cbpOrder);
	}

	@Override
	public List<OrderDTO> getOrderByEmployeeAndContract(OrderDTO orderDTO) {
		return cbpOrderMapper.getOrderByEmployeeAndContract(orderDTO);
	}

	@Override
	public OrderDTO getOrderAndContractById(Integer orderid) {
		return cbpOrderMapper.getOrderAndContractById(orderid);
	}

	@Override
	public CBPOrder findOrderIDByVinNO(String vinno) {
		return cbpOrderMapper.getOrderIDByVinNO(vinno);
	}

	@Override
	public CBPOrder getOrderById(Integer orderid) {
		return cbpOrderMapper.selectByPrimaryKey(orderid);
	}

	@Override
	public List<CBPOrder> getOrderListByCustomerIdAndStatus(CBPOrder record) {
		return cbpOrderMapper.getOrderListByCustomerIdAndStatus(record);
	}

	@Override
	public List<CBPOrder> getOrderList(String customerid) {
		return cbpOrderMapper.getOrderList(customerid);
	}

	/**
	 * 比较订单是否修改
	 *
	 * @param order
	 * @param orderDTO
	 * @return
	 */
	@Override
	public Boolean compareOrder(CBPOrder order, OrderDTO orderDTO) {
		if ((order.getBuyername() == null && orderDTO.getBuyername() != null)
				|| ((order.getBuyername() != null) && !order.getBuyername().equals(orderDTO.getBuyername())))
			return false;
		if ((order.getVinno() == null && orderDTO.getVhcle() != null)
				|| ((order.getVinno() != null) && !order.getVinno().equals(orderDTO.getVhcle())))
			return false;
		if (!order.getEmployeeid().equals(orderDTO.getEmployeeid()))
			return false;
		if (!order.getPhonenumber().equals(orderDTO.getPhonenumber()))
			return false;
		if (!order.getCartype().equals(orderDTO.getCartype()))
			return false;
		if ((order.getOrganizecode() == null && orderDTO.getOrganizecode() != null)
				|| ((order.getOrganizecode() != null) && !order.getOrganizecode().equals(orderDTO.getOrganizecode())))
			return false;
		if (!order.getOrdertype().equals(orderDTO.getOrdertype()))
			return false;
		if (!order.getBrandid().equals(orderDTO.getBrandid()))
			return false;
		if (!order.getCustomername().equals(orderDTO.getCustomername()))
			return false;
		if ((order.getOrganizename() == null && orderDTO.getOrganizename() != null)
				|| ((order.getOrganizename() != null) && !order.getOrganizename().equals(orderDTO.getOrganizename())))
			return false;
		if ((order.getBuyeridcardnum() == null && orderDTO.getBuyeridcardnum() != null)
				|| ((order.getBuyeridcardnum() != null)
						&& !order.getBuyeridcardnum().equals(orderDTO.getBuyeridcardnum())))
			return false;
		CBPDeposit cbpDeposit = depositService.selectDepositByOrderId(String.valueOf(order.getOrderid()));
		if (cbpDeposit == null) {
			return true;
		} else {
			String depositsum = cbpDeposit.getDepositsum();
			String earnest = orderDTO.getEarnest();
			Float floatfirt = Float.parseFloat(depositsum);
			Float flatlast = Float.parseFloat(earnest);
			if (floatfirt.floatValue() != flatlast.floatValue())
				return false;
			return true;
		}
	}

	@Override
	public List<OrderDTO> getCustomerByEmployeeId(Integer employeeid) {
		return cbpOrderMapper.getCustomerByEmployeeId(employeeid);
	}

	@Override
	public List<OrderDTO> getCustomerByEmployeeIdBase(Integer eployeeid) {
		return cbpOrderMapper.getCustomerByEmployeeIdBase(eployeeid);
	}

	@Override
	public CBPOrder getOrderBySapId(String saporderid) {
		return cbpOrderMapper.getOrderBySapId(saporderid);
	}

	@Override
	public int insertOrder(CBPOrder order) {
		return cbpOrderMapper.insert(order);
	}

	@Override
	public int updateOrderBySapId(CBPOrder order) {
		return cbpOrderMapper.updateOrderBySapId(order);
	}

	@Override
	public List<CBPOrder> getOrderByVinNo(String vinno) {
		return cbpOrderMapper.getOrderByVinNo(vinno);
	}

	@Override
	public List<OrderDTO> searchContract(ContractSearchDTO constractSearch) {
		// TODO Auto-generated method stub
		return cbpOrderMapper.searchContract(constractSearch);
	}

	@Override
	public SAPResult orderDliverry(SapOrderDTO saporderDTO) {
		SAPResult sapResult = new SAPResult();
		SAPResponse res = new SAPResponse();
		OrderDTO orderDTO = saporderDTO.getRecords();
		String saporderid = orderDTO.getSaporderid();
		res.setBL_ID(orderDTO.getSaporderid());
		if (StringUtil.isNullorEmpty(saporderid)) {
			res.setSAP_ID(saporderid);
			res.setStatus(CBPConstant.SAPResponseStatusEnum.FAILURE.getType());
			res.setDescription("id不能为空");
			sapResult.setRecords(res);
			return sapResult;
		}
		// StringUtil.isNullorEmpty(orderDTO.getVhcle())||
		// ||StringUtil.isNullorEmpty(orderDTO.getAppearanceinterior())
		if (StringUtil.isNullorEmpty(orderDTO.getCustomername()) || StringUtil.isNullorEmpty(orderDTO.getIdcardnum())
				|| StringUtil.isNullorEmpty(orderDTO.getBrandid()) || StringUtil.isNullorEmpty(orderDTO.getTotalprice())
				|| StringUtil.isNullorEmpty(String.valueOf(orderDTO.getEmployeeid()))
				|| StringUtil.isNullorEmpty(orderDTO.getOrdertype()) || StringUtil.isNullorEmpty(orderDTO.getCartype())
				|| StringUtil.isNullorEmpty(orderDTO.getEarnest())) {
			res.setSAP_ID(saporderid);
			res.setStatus(CBPConstant.SAPResponseStatusEnum.FAILURE.getType());
			res.setDescription("订单信息不全");
			sapResult.setRecords(res);
			return sapResult;
		}
		if ((StringUtil.isNullorEmpty(orderDTO.getBuyeridcardnum())
				|| StringUtil.isNullorEmpty(orderDTO.getBuyername()))
				&& ((StringUtil.isNullorEmpty(orderDTO.getOrganizecode())
						|| StringUtil.isNullorEmpty(orderDTO.getOrganizename())))) {
			res.setSAP_ID(saporderid);
			res.setStatus(CBPConstant.SAPResponseStatusEnum.FAILURE.getType());
			res.setDescription("订单信息不正确");
			sapResult.setRecords(res);
			return sapResult;
		}
		if (StringUtil.isNullorEmpty(orderDTO.getOrganizecode())) {
			orderDTO.setOrganizecode(null);
		}
		if (StringUtil.isNullorEmpty(orderDTO.getBuyeridcardnum())) {
			orderDTO.setBuyeridcardnum(null);
		}
		if (StringUtil.isNullorEmpty(orderDTO.getOrganizename())) {
			orderDTO.setOrganizecode(null);
		}
		if (StringUtil.isNullorEmpty(orderDTO.getBuyername())) {
			orderDTO.setBuyername(null);
		}
		if (!StringUtil.isNullorEmpty(orderDTO.getVinno())) {
			orderDTO.setRealvinno(orderDTO.getVinno());
		} else {
			orderDTO.setRealvinno(null);
		}
		if (!StringUtil.isNullorEmpty(orderDTO.getVhcle())) {
			orderDTO.setVinno(orderDTO.getVhcle());
		} else {
			orderDTO.setVinno(null);
		}
		CBPOrder order = getOrderBySapId(saporderid);
		CBPContract contract = new CBPContract();
		contract.setAppearanceinterior(orderDTO.getAppearanceinterior());
		contract.setContractdate(orderDTO.getContractdate());
		contract.setContractstatus(String.valueOf(CBPConstant.ContractStatusEnum.WAITINSIGN.getType()));
		contract.setDerivename(orderDTO.getDerivename());
		contract.setTotalprice(orderDTO.getTotalprice());
		try {
			if (order == null) {
				order = (CBPOrder) orderDTO;
				String idcardnum = order.getIdcardnum();
				String getCusStr = null;
				JSONObject synJson = new JSONObject();
				synJson.put("brandid",orderDTO.getBrandid());
				synJson.put("customername", order.getCustomername());
				synJson.put("idcardnum", idcardnum);
				synJson.put("phonenumber", order.getPhonenumber());
				if (order.getOrganizecode() == null) {
					synJson.put("buyeridcardnum", order.getBuyeridcardnum());
					synJson.put("buyername", order.getBuyername());
				} else {
					synJson.put("organizename", order.getOrganizename());
					synJson.put("organizecode", order.getOrganizecode());
				}
				getCusStr = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/synOrderCustomer",
						synJson.toString());
				if (getCusStr.equals("error")) {
					throw new BusinessException(orderDTO.getSaporderid(), "同步用户失败");
				}
				String[] splitCus = getCusStr.split(",");
				if(splitCus.length>1){
					order.setOpenid(splitCus[1]);
				}
				order.setCustomerid(Integer.valueOf(splitCus[0]));
				order.setEmployeeid(Integer.valueOf(orderDTO.getEmployeeid()));
				order.setOrderstatus(String.valueOf(CBPConstant.OrderStatusEnum.WAITINGPAY.getType()));
				insertOrder(order);
				orderDTO.setOrderid(order.getOrderid());
				Boolean flag = contractManageService.uploadOrderData(order, orderDTO);
				if (!flag) {
					throw new BusinessException(saporderid, "上传订单到合同管理系统失败");
				}
				contract.setOrderid(order.getOrderid());
				// 插入合同
				constractService.addConstract(contract);
				// 插入合同item
				CBPContractItem cbpContractItem = new CBPContractItem();
				cbpContractItem.setContractid(contract.getContractid());
				cbpContractItem.setContractstatus(String.valueOf(CBPConstant.ContractStatusEnum.WAITINSIGN.getType()));
				cbpContractItem.setContractdes("合同下发");
				cbpContractItem.setRequesttime(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
				contractItemService.saveItem(cbpContractItem);
				File file = FileUtil.byte2File(FileUtil.hexStr2BinArr(orderDTO.getContracturl()),
						systemConfig.getProperty("file.url.pdf")
								+ new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()) + ".pdf",
						"合同下发" + new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
				// 上传合同到电子签章
				authService.uploadFile(file, String.valueOf(contract.getContractid()));
				UploadDTO uploadDTO = new UploadDTO();
				uploadDTO.setEmployeeid(order.getEmployeeid());
				uploadDTO.setOrderid(order.getOrderid());
				uploadDTO.setContractid(contract.getContractid());
				uploadDTO.setCustomerid(order.getCustomerid());
				uploadDTO.setIdcardnum(order.getIdcardnum());
				uploadDTO.setType(CBPConstant.FileEnum.CONTRACT.getType());
				uploadDTO.setSapOrderId(order.getSaporderid());
				// 上传到合同管理系统
				String titleId = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployeeTitleId",
						"{\"employeeid\":" + uploadDTO.getEmployeeid() + "}");
				List<File> fileList = new ArrayList<File>();
				fileList.add(file);
				List<String> urllist = new ArrayList<String>();
				String result = UploadUtil.uploadByFile(
						systemConfig.getProperty("contract.url") + "upload/fileUpload.do", fileList, urllist, uploadDTO,
						titleId, order.getBrandid(), systemConfig.getProperty("fadada.appSecret"));
				System.out.print(result);
				JSONObject getJson = JSONObject.fromObject(result);
				if (String.valueOf(getJson.get("code")).equals("200")) {
					getJson.remove("data");
					FileListDTO fileuuidList = (FileListDTO) JSONObject.toBean(getJson, FileListDTO.class);
					contract.setContractUUID(fileuuidList.getObj()[0].getUuid());
					constractService.updateConstract(contract);
					// 更新定金
					int i = depositService.insertOrUpdateByOrder(orderDTO.getSaporderid(), orderDTO.getEarnest());
					if (i == 0) {
						throw new BusinessException(orderDTO.getSaporderid(), "更新定金失败");
					}
				} else {
					throw new BusinessException(orderDTO.getSaporderid(), "上传合同失败");
				}
			} else {
				JSONObject synJson = new JSONObject();
				synJson.put("customername", orderDTO.getCustomername());
				synJson.put("idcardnum", orderDTO.getIdcardnum());
				synJson.put("phonenumber", orderDTO.getPhonenumber());
				synJson.put("brandid",orderDTO.getBrandid());
				if (order.getOrganizecode() == null) {
					synJson.put("buyeridcardnum", order.getBuyeridcardnum());
					synJson.put("buyername", orderDTO.getBuyername());
				} else {
					synJson.put("organizename", orderDTO.getOrganizename());
					synJson.put("organizecode", orderDTO.getOrganizecode());
				}
				// 再次同步用户信息
				String getCusStr = HttpUtil.postbody(systemConfig.getProperty("url.customer") + "/synOrderCustomer",
						synJson.toString());
				if (getCusStr.equals("error")) {
					throw new BusinessException(orderDTO.getSaporderid(), "同步用户失败");
				}
				String[] splitCus = getCusStr.split(",");
				if(splitCus.length>1){
					orderDTO.setOpenid(splitCus[1]);
				}
				orderDTO.setCustomerid(Integer.valueOf(splitCus[0]));
				orderDTO.setOrderid(order.getOrderid());
				Boolean getflag = contractManageService.uploadOrderData(order, orderDTO);
				if (!getflag) {
					throw new BusinessException(orderDTO.getSaporderid(), "上传订单失败");
				}
				String firstcartype = order.getCartype();
				String getcartype = orderDTO.getCartype();
				if (!StringUtil.isNullorEmpty(firstcartype) && !StringUtil.isNullorEmpty(getcartype)) {
					if (!firstcartype.equals(getcartype)) {
						orderDTO.setDelivertime("");
					}
				}
				updateOrderBySapId((CBPOrder) orderDTO);
				contract.setOrderid(order.getOrderid());
				constractService.addConstract(contract);
				File file = FileUtil.byte2File(FileUtil.hexStr2BinArr(orderDTO.getContracturl()),
						systemConfig.getProperty("file.url.pdf")
								+ new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()) + ".pdf",
						"合同下发" + new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
				constractService.updateisChangeByOrderId(order.getOrderid());
				// 插入合同item
				CBPContractItem cbpContractItem = new CBPContractItem();
				cbpContractItem.setContractid(contract.getContractid());
				cbpContractItem.setContractstatus(String.valueOf(CBPConstant.ContractStatusEnum.WAITINSIGN.getType()));
				cbpContractItem.setContractdes("合同下发");
				cbpContractItem.setRequesttime(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
				contractItemService.saveItem(cbpContractItem);
				// 上传合同到电子签章
				authService.uploadFile(file, String.valueOf(contract.getContractid()));
				UploadDTO uploadDTO = new UploadDTO();
				uploadDTO.setEmployeeid(order.getEmployeeid());
				uploadDTO.setOrderid(order.getOrderid());
				uploadDTO.setContractid(contract.getContractid());
				uploadDTO.setIdcardnum(order.getIdcardnum());
				uploadDTO.setType(CBPConstant.FileEnum.CONTRACT.getType());
				uploadDTO.setSapOrderId(order.getSaporderid());
				// 上传到合同管理系统
				List<File> fileList = new ArrayList<File>();
				fileList.add(file);
				String titleId = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getEmployeeTitleId",
						"{\"employeeid\":" + uploadDTO.getEmployeeid() + "}");
				List<String> urllist = new ArrayList<String>();
				String result = UploadUtil.uploadByFile(
						systemConfig.getProperty("contract.url") + "upload/fileUpload.do", fileList, urllist, uploadDTO,
						titleId, order.getBrandid(), systemConfig.getProperty("fadada.appSecret"));
				JSONObject getJson = JSONObject.fromObject(result);
				if (String.valueOf(getJson.get("code")).equals("200")) {
					getJson.remove("data");
					FileListDTO fileuuidList = (FileListDTO) JSONObject.toBean(getJson, FileListDTO.class);
					contract.setContractUUID(fileuuidList.getObj()[0].getUuid());
					constractService.updateConstract(contract);
					// 更新定金
					int i = depositService.insertOrUpdateByOrder(orderDTO.getSaporderid(), orderDTO.getEarnest());
					if (i == 0) {
						throw new BusinessException(orderDTO.getSaporderid(), "更新定金失败");
					}
				} else {
					throw new BusinessException(orderDTO.getSaporderid(), "上传合同失败");
				}

			}
			// 发送物流消息
			String logisticinfo = orderDTO.getLogisticinfo();
			CBPLogistics logistics = new CBPLogistics();
			try { // 判断车型是否变化如果变化需要删除以前物流信息
				String firstcartype = order.getCartype();
				String getcartype = orderDTO.getCartype();
				if (!StringUtil.isNullorEmpty(firstcartype) && !StringUtil.isNullorEmpty(getcartype)) {
					if (!firstcartype.equals(getcartype)) {
						logisticsService.deleteLogisticInfoByOrder(order.getOrderid());
					}
				}
				String status = null;
				if (logisticinfo != null) {
					String[] getlogstic = logisticinfo.split("&&");
					String cartype = getlogstic[0];
					status = getlogstic[1];
					logistics.setCartype(cartype);
					logistics.setStatus(status);
					logistics.setOrderid(order.getOrderid());
					logistics.setSenddate(new SimpleDateFormat("yyyyMMdd").format(new Date()));
					if (status.equals("订单已确定")) {
						logistics.setSequence("0000");
					} else if (status.equals("车辆已排产")) {
						logistics.setSequence("0010");
					} else if (status.equals("车辆已发运")) {
						logistics.setSequence("0020");
					} else if (status.equals("车辆已到港")) {
						logistics.setSequence("0030");
					} else if (status.equals("车辆已在途")) {
						logistics.setSequence("0040");
					} else if (status.equals("车辆已到店")) {
						logistics.setSequence("0050");
					}
					logistics.setSendStatus("0");
					logisticsService.insertOrUpdateLogistic(logistics);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			res.setSAP_ID(orderDTO.getSaporderid());
			res.setStatus(CBPConstant.SAPResponseStatusEnum.SUCCESS.getType());
			res.setDescription("success");
			sapResult.setRecords(res);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(orderDTO.getSaporderid(), e.getMessage());
		}
		return sapResult;
	}

	@Override
	public Page getOrderByEmployeeId(CustomerInfoQueryDTO customerInfoQueryDTO) {
		Page<CBPOrder> page = new Page<CBPOrder>();
		// 起始索引
		Integer index = null;
		Integer pageSize = null;
		if (StringUtil.isNullorEmpty(customerInfoQueryDTO.getPageSize() + "")
				|| customerInfoQueryDTO.getPageSize() == 0) {
			pageSize = 10;
		} else {
			pageSize = customerInfoQueryDTO.getPageSize();
		}
		if (StringUtil.isNullorEmpty(customerInfoQueryDTO.getCurrentPage() + "")) {
			index = 0;
		} else {
			index = (customerInfoQueryDTO.getCurrentPage() - 1) * pageSize;
		}
		Map map = new HashMap();
		map.put("employeeId", customerInfoQueryDTO.getEmployeeId());
		map.put("index", index);
		map.put("pageSize", pageSize);
		map.put("employeeIdOwn", customerInfoQueryDTO.getEmployeeIdOwn());
		List<CBPOrder> list = null;
		Integer count = null;
		if ((CBPConstant.CAccountTypeEnum.PRIVATE.getType() + "").equals(customerInfoQueryDTO.getAccountType())) {
			map.put("pageSize", 20);
			list = cbpOrderMapper.getOrderListByEmployeeId(map);
			count = cbpOrderMapper.getOrderListCountByEmployeeId(map);
			System.out.println(list.size());
			System.out.println("=======================总数量为" + count);
		} else {
			list = cbpOrderMapper.getOrderListByEmployeeIds(map);
			count = cbpOrderMapper.getOrderListCountByEmployeeIds(map);
			System.out.println(list.size());
			System.out.println("=======================" + count);
		}
		page.setCurrentPage(customerInfoQueryDTO.getCurrentPage());
		page.setPageSize((int) map.get("pageSize"));
		page.setTotalRecord(count);
		page.setDatas(list);
		return page;
	}

	@Override
	public List<SearchCustomerDTO> searchCustomerPrivate(CustomerInfoQueryDTO customerInfoQueryDTO) {
		return cbpOrderMapper.searchCustomerPrivate(customerInfoQueryDTO);
	}

	@Override
	public List<SearchCustomerDTO> searchCustomerPublic(CustomerInfoQueryDTO customerInfoQueryDTO) {
		return cbpOrderMapper.searchCustomerPublic(customerInfoQueryDTO);
	}

	/**
	 * 显示门店监控
	 * 
	 * @param monitorSearchDTO
	 * @return
	 */
	@Override
	public List<MonitorSearchDTO> searchOrderMonitorBrand(MonitorSearchDTO monitorSearchDTO) {
		return cbpOrderMapper.searchOrderMonitorBrand(monitorSearchDTO);
	}

	/**
	 * 显示门店管理监控
	 * 
	 * @param monitorSearchDTO
	 * @return
	 */
	@Override
	public List<MonitorSearchDTO> searchOrderMonitorManager(MonitorSearchDTO monitorSearchDTO) {

		return cbpOrderMapper.searchOrderMonitorManager(monitorSearchDTO);
	}

	@Override
	public List<CBPOrder> getOrderByEmployeeId(String employeeId) {
		return cbpOrderMapper.getOrderByEmployeeId(Integer.valueOf(employeeId));
	}

	@Override
	public int insertOrUpdateOrder(CBPOrder cbpOrder) {
		int status = 0;
		CBPOrder cbpOrder1 = cbpOrderMapper.selectOrderByCarId(cbpOrder.getCarid());
		if (cbpOrder1 != null) {
			cbpOrder.setOrderid(cbpOrder1.getOrderid());
			status = cbpOrderMapper.updateByPrimaryKeySelective(cbpOrder);
		} else {
			status = cbpOrderMapper.insertSelective(cbpOrder);
		}
		return status;

	}

	@Override
	public List<SignMonitorDTO> showOrderSignMonitor(SignMonitorDTO signMonitorDTO) {
		return cbpOrderMapper.showOrderSignMonitor(signMonitorDTO);
	}

	@Override
	public List<PaperCostsDTO> getPaperCosts(SignMonitorDTO smdto) {
		Object[] brandIdList = smdto.getBrandIdList();
		List<PaperCostsDTO> smdtoList = new ArrayList<>();
		for (Object object : brandIdList) {
			PaperCostsDTO pcdto = new PaperCostsDTO();
			smdto.setBrandId(object.toString());
			try {
				String storeCnShort = HttpUtil.postbody(
						systemConfig.getProperty("url.employee") + "/store/getstoreCnShortByStoreId",
						"{\"brandid\":" + object.toString() + "}");
				String[] split = storeCnShort.split(",");
				pcdto.setBrandId(object.toString());
				pcdto.setStoreCnShort(split[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 获取合同纸张数量及每页价格
			CBPTimeConf ctf = cbpTimeConfMapper.getEveryContractCountAndPrice(smdto);
			// 完成的订单数量（按门店统计）
			Integer count = cbpOrderMapper.getOrderCountByBrandIdAndOrderStatus(smdto);
			if (ctf != null && count != null) {
				Integer contractPaperCount = ctf.getContractCount();
				String paperCost = ctf.getPaperCost();
				// 合同纸张总数
				int contractCount = 0;
				if (contractPaperCount != null) {
					contractCount = count.intValue() * contractPaperCount.intValue();
				}
				// 文件证照数量
				Integer fileCount = cbpOrderFileMapper.getFileCountByBrandIdAndOrderStatus(smdto);
				// 垫付证明
				Integer outpocketCount = cbpOutpocketMapper.getOutpocketCountByBrandIdAndOrderStatus(smdto);
				// 收据
				Integer receiptCount = cbpReceiptMapper.getReceiptCountByBrandIdAndOrderStatus(smdto);
				int total = contractCount;
				if (fileCount != null) {
					total = total + fileCount.intValue();
				}
				if (outpocketCount != null) {
					total = total + outpocketCount.intValue();
				}
				if (receiptCount != null) {
					total = total + receiptCount.intValue();
				}
				Double d = 0.0;
				if (paperCost != null) {
					d = total * Double.valueOf(paperCost);
				}
				pcdto.setPaperTotalCount(total + "");
				pcdto.setPaperTotalPrice(d.toString());
			}
			smdtoList.add(pcdto);
		}
		return smdtoList;
	}

	@Override
	public List<CBPOrder> getOrderListByEmployeeIdAndCustomerId(CBPOrder cbpOrder) {
		return cbpOrderMapper.getOrderListByEmployeeIdAndCustomerId(cbpOrder);
	}

	@Override
	public List<CBPOrder> getOrderListByBrandIdAndCustomerId(CBPOrder cbpOrder) {
		return cbpOrderMapper.getOrderListByBrandIdAndCustomerId(cbpOrder);
	}

	@Override
	public int maintenancePrice(ChangeAllFee caf) {

		if (caf.getCar() != null) {
			CBPCar car = cbpCarMapper.selectByPrimaryKey(caf.getCar().getCarid());

		} else if (caf.getBoutiqueOrder() != null) {

		} else if (caf.getInsuranceOrder() != null) {

		}

		return 1;
	}

	@Override
	public WeixinResult insertChooseCarAndFourCustomer(ChooseCarDTO cshco) {
		WeixinResult res11 = new WeixinResult();
		res11.setCode(CBPConstant.CODE_NULL_PARAM);
		res11.setMessage(CBPConstant.MESSAGE_NULL_PARAM);
		if (!StringUtil.isNullorEmpty(cshco.getOrderid())) {
			CBPOrder cbpOrder = cbpOrderMapper.selectByPrimaryKey(Integer.valueOf(cshco.getOrderid()));
			if (cbpOrder != null) {
				CBPFourCustomer fourCustomer = new CBPFourCustomer();
				fourCustomer.setOrderid(Integer.valueOf(cshco.getOrderid()));
				fourCustomer.setType(CBPConstant.FourCustomerTypeEnum.SOLD.getType());
				CBPFourCustomer cfc = fourCustomerMapper.selectByOrderIdAndType(fourCustomer);
				if (cfc != null) {
					cbpOrder.setIdcardnum(cfc.getIdcard());
					cbpOrder.setPhonenumber(cfc.getPhonenumber());
					cbpOrder.setCustomername(cfc.getName());
				}
				CBPFourCustomer fourCustomer1 = new CBPFourCustomer();
				fourCustomer1.setOrderid(Integer.valueOf(cshco.getOrderid()));
				fourCustomer1.setType(CBPConstant.FourCustomerTypeEnum.DELIVERY.getType());
				CBPFourCustomer cfc1 = fourCustomerMapper.selectByOrderIdAndType(fourCustomer1);
				if (cfc1 != null) {
					cbpOrder.setBuyername(cfc.getName());
					cbpOrder.setBuyeridcardnum(cfc.getIdcard());
				}
				cbpOrder.setCustomerid(Integer.valueOf(cshco.getCustomerid()));
				cbpOrder.setCartype(cshco.getCartype());
				cbpOrder.setOrderstatus(CBPConstant.OrderStatusEnum.WAITINGPAY.getType() + "");
				cbpOrder.setEmployeeid(Integer.valueOf(cshco.getEmployeeid()));
				cbpOrder.setOrderdate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
				cbpOrder.setRealvinno(cshco.getRealvinno());
				// 更新 订单
				int i = cbpOrderMapper.updateByPrimaryKeySelective(cbpOrder);
				// 更新 车辆
				CBPCar cbpCar = cbpCarMapper.selectCarByOrderId(Integer.valueOf(cshco.getOrderid()));
				cbpCar.setCarseries(cshco.getCarseries());
				// cbpCar.setCaryear(cshco.get);
				cbpCar.setCarinterior(cshco.getCarinterior());
				cbpCar.setCarappearance(cshco.getCarappearance());
				// cbpCar.setCarbrand(cshco.get);
				int j = cbpCarMapper.updateByPrimaryKeySelective(cbpCar);
				if (i != 0 && j != 0) {
					res11.setCode(CBPConstant.CODE_SUCCESS);
					res11.setMessage(CBPConstant.MESSAGE_SUCCESS);
					res11.setData(Integer.valueOf(cshco.getOrderid()));
					return res11;
				} else {
					throw new WXException("修改订单失败");
				}
			} else {
				throw new WXException("未查询到该订单");
			}

		} else {

			CBPOrder cbpOrder = new CBPOrder();
			int orderid = 0;
			List<BDLFourCustomer> list;
			// 通过customerid去查四方信息
			try {
				JSONObject jsonObject7 = new JSONObject();
				jsonObject7.put("customerid", cshco.getCustomerid());
				String result = HttpUtil.postbody(
						systemConfig.getProperty("url.customer") + "/selectFourListByCustomerid",
						jsonObject7.toString());
				list = JSONArray.parseArray(result, BDLFourCustomer.class);
				if (list == null || list.isEmpty()) {
					return res11;
				}
			} catch (Exception e) {
				throw new WXException("查询四方信息异常");
			}

			for (BDLFourCustomer cfc : list) {
				if (CBPConstant.FourCustomerTypeEnum.SOLD.getType().equals(cfc.getType())) {
					cbpOrder.setIdcardnum(cfc.getIdcard());
					cbpOrder.setPhonenumber(cfc.getPhonenumber());
					cbpOrder.setCustomername(cfc.getName());
				}
				if (CBPConstant.FourCustomerTypeEnum.DELIVERY.getType().equals(cfc.getType())) {
					cbpOrder.setBuyername(cfc.getName());
					cbpOrder.setBuyeridcardnum(cfc.getIdcard());
				}
			}
			// 生成订单 并 添加车辆信息到car表
			cbpOrder.setCustomerid(Integer.valueOf(cshco.getCustomerid()));
			cbpOrder.setCartype(cshco.getCartype());
			cbpOrder.setOrderstatus(CBPConstant.OrderStatusEnum.WAITINGPAY.getType() + "");
			cbpOrder.setEmployeeid(Integer.valueOf(cshco.getEmployeeid()));
			String brandid = null;
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("employeeid", Integer.valueOf(cshco.getEmployeeid()));
			try {
				brandid = HttpUtil.postbody(systemConfig.getProperty("url.employee") + "/ee/getStoreIdByEmployeeId",
						jsonObject.toString());
			} catch (Exception e) {
				res11.setCode(CBPConstant.CODE_NULL_PARAM);
				res11.setMessage("获取门店信息失败");
				return res11;
			}
			cbpOrder.setBrandid(brandid);
			cbpOrder.setOrderdate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			cbpOrder.setRealvinno(cshco.getRealvinno());
			int insert = cbpOrderMapper.insert(cbpOrder);
			orderid = cbpOrder.getOrderid();
			CBPCar cbpCar = new CBPCar();
			cbpCar.setCarseries(cshco.getCarseries());
			// cbpCar.setCaryear(cshco.get);
			cbpCar.setCarinterior(cshco.getCarinterior());
			cbpCar.setCarappearance(cshco.getCarappearance());
			// cbpCar.setCarbrand(cshco.get);
			cbpCar.setOrderid(orderid);
			int insertSelective = cbpCarMapper.insertSelective(cbpCar);
			if (insert == 0 || insertSelective == 0) {
				throw new WXException(CBPConstant.CODE_NULL_PARAM, "生成订单失败！");
			}
			int status = 0;
			for (BDLFourCustomer cfc : list) {
				CBPFourCustomer fourCustomer = new CBPFourCustomer();
				fourCustomer.setIdcard(cfc.getIdcard());
				fourCustomer.setName(cfc.getName());
				fourCustomer.setOrderid(orderid);
				fourCustomer.setPhonenumber(cfc.getPhonenumber());
				fourCustomer.setType(cfc.getType());
				fourCustomer.setTypename(cfc.getTypename());
				fourCustomer.setAddress(cfc.getAddress());
				fourCustomer.setArea(cfc.getArea());
				int i = fourCustomerMapper.insertSelective(fourCustomer);
				if (i != 0) {
					status++;
				}
			}
			if (status == list.size()) {
				res11.setCode(CBPConstant.CODE_SUCCESS);
				res11.setMessage(CBPConstant.MESSAGE_SUCCESS);
				res11.setData(orderid);
			} else {
				throw new WXException("维护四方信息异常");
			}
			return res11;
		}
	}

	@Override
	public List<CBPOrder> getOrderListBySearchCard(CardDTO cardDTO) {

		return cbpOrderMapper.getOrderListBySearchCard(cardDTO);
	}

	@Override
	public ProcessDTO getOrderProcess(Integer orderid) {
		return cbpOrderMapper.getOrderProcess(orderid);
	}

	@Override
	public List<BDLBrandStore<BrandStore>> getPaperCostsForPad(SignMonitorDTO smdto) {
		List<BDLBrandStore<BrandStore>> BDLBrandStoreList = new ArrayList<>();
		Object[] brandIdList = smdto.getBrandIdList();
		List<PaperCostsDTO> smdtoList = new ArrayList<>();
		for (Object object : brandIdList) {
			BDLBrandStore<BrandStore> BDLBrandStore = new BDLBrandStore<BrandStore>();
			PaperCostsDTO pcdto = new PaperCostsDTO();
			smdto.setBrandId(object.toString());
			try {
				String storeCnShort = HttpUtil.postbody(
						systemConfig.getProperty("url.employee") + "/store/getstoreCnShortByStoreId",
						"{\"brandid\":" + object.toString() + "}");
				String[] split = storeCnShort.split(",");
				pcdto.setBrandId(object.toString());
				pcdto.setStoreCnShort(split[0]);
				pcdto.setBrandName(split[1]);
				pcdto.setBrandType(Integer.valueOf(split[2]));
				BDLBrandStore.setBrandId(object.toString());
				BDLBrandStore.setBrandName(split[1]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 获取合同纸张数量及每页价格
			CBPTimeConf ctf = cbpTimeConfMapper.getEveryContractCountAndPrice(smdto);
			// 完成的订单数量（按门店统计）
			Integer count = cbpOrderMapper.getOrderCountByBrandIdAndOrderStatus(smdto);
			if (ctf != null && count != null) {
				Integer contractPaperCount = ctf.getContractCount();
				String paperCost = ctf.getPaperCost();
				// 合同纸张总数
				int contractCount = count.intValue() * contractPaperCount.intValue();
				// 文件证照数量
				Integer fileCount = cbpOrderFileMapper.getFileCountByBrandIdAndOrderStatus(smdto);
				// 垫付证明
				Integer outpocketCount = cbpOutpocketMapper.getOutpocketCountByBrandIdAndOrderStatus(smdto);
				// 收据
				Integer receiptCount = cbpReceiptMapper.getReceiptCountByBrandIdAndOrderStatus(smdto);
				int total = contractCount + fileCount.intValue() + outpocketCount.intValue() + receiptCount.intValue();
				Double d = total * Double.valueOf(paperCost);
				if (StringUtil.isNullorEmpty(total + "")) {
					pcdto.setPaperTotalCount("0");
				} else {
					pcdto.setPaperTotalCount(total + "");
				}
				if (StringUtil.isNullorEmpty(d + "")) {
					pcdto.setPaperTotalPrice("0");
				} else {
					DecimalFormat df = new DecimalFormat("00.00");
					String format = df.format(d);
					pcdto.setPaperTotalPrice(format);
				}
			} else {
				pcdto.setPaperTotalCount("0");
				pcdto.setPaperTotalPrice("0");
			}
			smdtoList.add(pcdto);
		}
		Collections.sort(smdtoList);
		for (int i = 0; i < smdtoList.size(); i++) {
			Boolean flag = false;
			for (int j = 0; j < BDLBrandStoreList.size(); j++) {
				Integer brandType = BDLBrandStoreList.get(j).getBrandType();
				if (brandType.intValue() == smdtoList.get(i).getBrandType().intValue()) {
					BDLBrandStoreList.get(j).getBrandStoreList().add(smdtoList.get(i));
					flag = true;
					break;
				}
			}
			if (!flag) {
				BDLBrandStore<BrandStore> bdlstore = new BDLBrandStore<BrandStore>();
				bdlstore.setBrandId(smdtoList.get(i).getBrandId());
				bdlstore.setBrandName(smdtoList.get(i).getBrandName());
				bdlstore.setBrandType(smdtoList.get(i).getBrandType());
				List<PaperCostsDTO> brandstoreList = new ArrayList<PaperCostsDTO>();
				brandstoreList.add(smdtoList.get(i));
				bdlstore.setBrandStoreList(brandstoreList);
				BDLBrandStoreList.add(bdlstore);
			}
		}
		return BDLBrandStoreList;
	}

	@Override
	public AllFeeDTO getAllFee(Integer orderid) {
		AllFeeDTO afd = new AllFeeDTO();
		CBPContract maxConstract = constractService.getMaxConstract(orderid);
		afd.setCbpContract(maxConstract);
		List<CBPInsuranceOrder> list = cbpInsuranceOrderMapper.selectInsuranceOrderListByOrderId(orderid);
		afd.setInsuranceOrderList(list);
		List<CBPBoutiqueOrder> list2 = cbpBoutiqueOrderMapper.selectBoutiqueByOrderId(orderid);
		afd.setBoutiqueOrderList(list2);
		CBPCheckCarNumOrder checkCarNum = cbpCheckCarNumOrderMapper.getCheckCarNumByOrderId(orderid);
		afd.setCbpCheckCarNumOrder(checkCarNum);
		return afd;
	}

	@Override
	public List<CBPOrder> getAllOrder() {
		return cbpOrderMapper.getAllOrder();
	}

	@Override
	public int updateOrder(CBPOrder order) {
		return cbpOrderMapper.updateByPrimaryKeySelective(order);
	}

}
