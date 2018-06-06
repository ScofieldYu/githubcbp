/*   
 * @(#)BDLUserRoleDTO.java       2018年3月7日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.employee.dto;

/** 
 * 此类功能描述    
 *
 * @author Zhangtong 
 * @since JDK 1.7 
 */
public class BDLUserRoleDTO {
	 private Integer userroleid;

	    private Integer userid;

	    private Integer roleid;

	    private String companycode;

	    private String area;
	    
	    private String rolekey;
	    

	    public String getRolekey() {
			return rolekey;
		}

		public void setRolekey(String rolekey) {
			this.rolekey = rolekey;
		}

		public Integer getUserroleid() {
	        return userroleid;
	    }

	    public void setUserroleid(Integer userroleid) {
	        this.userroleid = userroleid;
	    }

	    public Integer getUserid() {
	        return userid;
	    }

	    public void setUserid(Integer userid) {
	        this.userid = userid;
	    }

	    public Integer getRoleid() {
	        return roleid;
	    }

	    public void setRoleid(Integer roleid) {
	        this.roleid = roleid;
	    }

	    public String getCompanycode() {
	        return companycode;
	    }

	    public void setCompanycode(String companycode) {
	        this.companycode = companycode == null ? null : companycode.trim();
	    }

	    public String getArea() {
	        return area;
	    }

	    public void setArea(String area) {
	        this.area = area == null ? null : area.trim();
	    }
}
