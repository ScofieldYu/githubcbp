/**
 * Created by ThinkPad User on 2018/2/11.
 */
$(function () {
    var insuranceOrderList = JSON.parse(window.sessionStorage.getItem("insuranceOrderList"));

    //整理数据
    var insuranceData = {
        name: insuranceOrderList[0].insurancecompanyname, //保险公司名称
        insurancePrice: 0, //保险总价
        compulsory: [],   //车船税+交强险
        commercial: []   //商业险
    };
    for (var i = 0; i < insuranceOrderList.length; i++) {
        var cur = insuranceOrderList[i];
        insuranceData.insurancePrice += Number(cur.expense);  //保险价格
        if (cur.insurancetype == 1) { //1 为"交强险+车船使用税"    2为"商业险"
            insuranceData.compulsory.push(cur)    //车船税+交强险
        } else {
            insuranceData.commercial.push(cur)   //商业险
        }
    }

    //绑定页面数据
    $("#compulsory").html(returnHtmlStr(insuranceData.compulsory));//车船税+交强险
    $("#commercial").html(returnHtmlStr(insuranceData.commercial));//商业险
    $("#insurancePrice").text(insuranceData.insurancePrice);//保险总价

    //返回保险列表htmlStr
    function returnHtmlStr(data) {
        var htmlStr = '';
        for (var i = 0; i < data.length; i++) {
            //是否有  -> 不计免赔
            var isnodeductible = data[i].isnodeductible == 1 ? "(不计免赔)" : "";
            htmlStr += '<li><span>' + data[i].insurancename + isnodeductible + '</span><span>'+data[i].insurancelimit+'保额</span></li>'
        }
        return htmlStr;
    }


});