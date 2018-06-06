/*   
 * @(#)Test.java       2018年2月12日  
 *   
 * 百得利集团拥有完全的版权   
 * 使用者必须经过许可   
 */  
package com.blchina.employee.controller;

import java.util.HashMap;

import com.blchina.employee.datamodel.CBPConstant.StoreBrandEnum;

/** 
 * 此类功能描述    
 *
 * @author Admin 
 * @since JDK 1.7 
 */
public class Test {
	public static void main(String[] args) {
		HashMap<String,Object> hashMap = new HashMap<String, Object>();
		   Class<StoreBrandEnum> clz = StoreBrandEnum.class;
		   for (Object obj : clz.getEnumConstants()) {
			   hashMap.put(((StoreBrandEnum) obj).getType(), obj );
		   }
		   Object object = hashMap.get("1");
		   String string = object.toString();
		   System.out.println(string);
	}
}
