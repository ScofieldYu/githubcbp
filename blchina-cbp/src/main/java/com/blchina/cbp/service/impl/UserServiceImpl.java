/*
 * @(#)UserService.java       Nov 2, 2017
 *
 * 百得利集团拥有完全的版权
 * 使用者必须经过许可
 */
package com.blchina.cbp.service.impl;

import com.blchina.cbp.datamodel.CBPConstant;
import com.blchina.cbp.service.interfaces.UserService;
import com.blchina.common.util.http.HttpUtil;
import com.blchina.common.util.string.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

/**
 *客户操作service
 * @author zhuchenglong
 * @since JDK 1.8
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    private Logger log = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    protected LdapTemplate ldapTemplate;
    @Autowired
    private Properties systemConfig;

    /**
     * 通过AD域验证用户
     * @param name
     * @param passwd
     * @return
     * 0 ： 密码正确，登录成功
     * 1 ： 用户不存在
     * 2 ：密码错误
     */
    @Override
    public int userLogin(String name, String passwd, String deviceId) {
        // 查找用户信息
        String dn = getDnForUser(name);

        if (StringUtil.isNullorEmpty(dn)) return 1;
        log.info("用户信息cn," + dn);

        // 验证用户名和密码
        boolean bool = authUserByADDomain(dn, passwd);
        if (bool) {
            // 同步用户信息
            syncDomainUser(name);
        }
        return bool ? 0 : 2;
    }

    // 暂时不用
    private void syncDomainUser(String name) {
//        try {
//            //同步用户信息到数据库
//            EdsUser edsUser = new EdsUser();
//            edsUser.setUsername(name);
//            EdsUser user = findUser(edsUser);
//            if(user == null){
//                userSyncService.addUserToDataBase(name);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private String getDnForUser(String userName) {
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "person")).and(new EqualsFilter("sAMAccountName", userName));
        log.info("通过AD域获取用户信息,userName=" + filter.toString());

        String ouList = systemConfig.getProperty("connection.ldap.ou");
        if (StringUtil.isNullorEmpty(ouList)) return null;

        String[] ouArray = ouList.split(";");
        List result = null;
        for (String ou : ouArray) {
            result = ldapTemplate.search("OU=" + ou, filter.toString(), new AbstractContextMapper() {
                protected Object doMapFromContext(DirContextOperations ctx) {
                    return ctx.getNameInNamespace();
                }
            });
            if (result != null && result.size() == 1) break;
        }

        log.info("通过AD域获取用户信息,记录条数size=" + result.size());
        if (result == null || result.size() != 1) return null;
        return (String) result.get(0);
    }

    private boolean authUserByADDomain(String userDn, String passwd) {
        DirContext ctx = null;
        try {
            ctx = ldapTemplate.getContextSource().getContext(userDn, passwd);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            LdapUtils.closeContext(ctx);
        }
    }
    
    
    
    
    public String getTitleIdByEmployeeId(Integer employeeId){
       try {
		 String response = HttpUtil.postbody(
             systemConfig.getProperty("url.employee") + "/userRole/getRoleKeyByEmployeeId",
             "{\"userid\":" + employeeId + "}");
		 if(StringUtil.isNullorEmpty(response)){
			return null;
		 }
		 return response;
	  }
	  catch (Exception e) {
		 return null;
	  }
    }
}
