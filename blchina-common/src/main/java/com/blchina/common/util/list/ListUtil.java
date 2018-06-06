package com.blchina.common.util.list;


import com.blchina.common.util.string.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**

 */
public class ListUtil {

    public static StringBuilder listToString(List<String> list){
        StringBuilder str = new StringBuilder();
        for ( int i = 0; i < list.size(); i++ ) {
            str.append(list.get(i)).append(",");
        }
        if (str.length() > 0) str.deleteCharAt(str.length() - 1);
        return str;
    }

    public static String stringToAttention(String str){
        if (StringUtil.isNullorEmpty(str)) {
            return "";
        }
        str = str.replace(",","','");
        return "'" + str.trim() + "'";
    }

    public static String removeLastComma(StringBuilder sb){
        String str = sb.toString();
        if (str.endsWith(",")) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public static void main(String[] args) {
        ListUtil util = new ListUtil();
        List<String> list = new ArrayList<String>();
        for (int i = 1; i <= 10; i++) {
            list.add(i + "");
        }
        util.syncStatus(list);
    }

    private void syncStatus(List<String> uuidList) {
        if (uuidList.size() == 0) return;
        int count = (int)Math.ceil(uuidList.size()/50);
        for ( int i = 0; i < count; i++ ) {
            StringBuilder uuids = ListUtil.listToString(uuidList.subList(i * 50, i + 50));
            if ( uuidList.size() < i + 50) {
                uuids = ListUtil.listToString(uuidList.subList(i * 50, uuidList.size()));
            }
            System.out.println(uuids);
        }
    }
}
