<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>
    <title>登录界面</title>
   <style>
    body,html{
        font-family: "微软雅黑";
        height: 100%;
        width: 100%;
    }
    html {
            width: 100%;
            height: 100%;
            font-size: 100px;
            overflow: hidden;
        }

        h1 {
            margin: 0.1rem auto;
            font-size: 0.1rem;
        }

        input {
            border: 1px solid #000000;
            width: 3rem;
            height: 0.4rem;
            font-size: 0.2rem;
        }

        button {
            margin: 0 auto;
        }
        h1{
            padding: .1rem 0;
            font-size: .14rem;
            display: flex;
            align-items: center;
            border-bottom:.01rem solid #eee;
        }
        input{
            text-indent: .15rem;
            border-radius: .05rem;
            flex:1;
            margin-right:.3rem;
        }
        .btn{
            width: 100%;
        }
        h1 label{
            width: .7rem;
            display: inline-block;
            display: flex;
            font-size: .16rem;
            font-weight: bold;
            white-space: nowrap;
        }
       .btn button{
           width: 80%;
           display: inline-block;
           background:skyblue;
           color:#fff;
           outline: none;
           border:0.01rem solid skyblue;
           border-radius: .04rem;
           font-size: .16rem;
       }
</style>
<body>
<h1 style="color:red;font-size:.18rem;font-weight:bold;">授权成功！</h1>
<h1><label for="">openId:</label>${openId}</h1>
<h1><label for="">昵称:</label>${nickname}</h1>
<h1><label for="">地址:</label>${province}&nbsp;${city}</h1>
<h1><label for="">头像:</label>${headimgurl}</h1>
<h1><label for="phonenumber">手 机 号:</label></label><input type="text" value="111" id="phonenumber"></h1>
<h1><label for="idcardnum">身份证号:</label></label><input type="text" value="222" id="idcardnum"><br></h1>
<div class="btn"><button id="geren" type="button" style="height:0.5rem;display:block">个人资料</button></div>
<div class="btn"><button id="gouche" type="button" style="margin-top: 0.2rem;height:0.5rem;display:block">购车订单</button></div>
</body>
<script type="text/javascript">
    ~function (desW) {
        var winW = document.documentElement.clientWidth;
        document.documentElement.style.fontSize = winW / desW * 100 + "px";
    }(375);
    var storage = window.localStorage;
    var gerenIpt = document.getElementById('phonenumber');//手机号
    var goucheIpt = document.getElementById('idcardnum');//身份证号
    var gerenBtn = document.getElementById('geren');
    var goucheBtn = document.getElementById('gouche');
    var phone = gerenIpt.value || '';
    var gouche = goucheIpt.value || '';
    
    var $phone = storage.getItem("phone") ||'';
    var $goucheIpt = storage.getItem("goucheIpt") || '';
    
    if(storage.getItem("phone")){
        gerenIpt.value = $phone;
    }
    if(storage.getItem("goucheIpt")){
        goucheIpt.value = $goucheIpt;
    }
    gerenBtn.onclick = function () {
        var phonenumber = gerenIpt.getAttribute('value');
        var idcardnum = goucheIpt.getAttribute('value');
        var urlStr = "http://cbp-qas.xbotech.com/cbp/new-personalData.html?phonenumber=" + phonenumber + "&openId=" + ${openId} +"&idcardnum=" + idcardnum + "&nickname=" + ${nickname} +"&wxheadimgurl=" + ${headimgurl} +"&brandId=2028";
        alert(urlStr);
        window.location.href = urlStr;
        
        storage.setItem("phone",phone);
        storage.setItem("goucheIpt",gouche);
    };
    goucheBtn.onclick = function () {
        var phonenumber = gerenIpt.getAttribute('value');
        var idcardnum = goucheIpt.getAttribute('value');
        var urlStr = "http://cbp-qas.xbotech.com/cbp/new-personalData.html?phonenumber=" + phonenumber + "&openId=" + ${openId} +"&idcardnum=" + idcardnum + "&nickname=" + ${nickname} +"&wxheadimgurl=" + ${headimgurl} +"&brandId=2028";
        alert(urlStr);
        window.location.href = urlStr;

        storage.setItem("phone",phone);
        storage.setItem("goucheIpt",gouche);
    }
</script>
</html>