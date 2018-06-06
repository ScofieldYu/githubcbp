(function (doc, win) {
        var docEl = doc.documentElement,
            resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
            recalc = function () {
                var clientWidth = docEl.clientWidth;

                if (!clientWidth) return;

                docEl.style.fontSize = 100 * (clientWidth / 375) + 'px';
            };
        if (!doc.addEventListener) return;
        win.addEventListener(resizeEvt, recalc, false);
        doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);

    var storage = window.localStorage;
    var session = window.sessionStorage;
    //字符串公共方法
    ~function (pro) {
        //queryURLParameter:Gets the parameters in the URL address bar //截取URL
        pro.queryURLParameter = function queryURLParameter() {
            var reg = /([^?&=]+)=([^?&=]+)/g, obj = {};
            this.replace(reg, function () {
                obj[arguments[1]] = decodeURI(arguments[2]) ;
            });
            return obj;
        };
    }(String.prototype);

        var strUrl = window.location.href;
        var queryUrl = strUrl.queryURLParameter();
        var d = null,idcardnum = '',phonenumber="";
        if(session.getItem("openid")){   
            //alert("走的session");
            var img = '',name = '';
            img = session.getItem("headimgurl");
            name = decodeURIComponent(session.getItem('nickname'));
            console.log(img)
            console.log(name)
            $(".bdl_img").attr("src",img);
            $(".bdl_titleName").text(name);
        }else{
            //alert(queryUrl.state)
            var img = '',name = '';
            $.ajax({
            type: "POST",
            url:srcConfig.setOpenId,
            // url:'http://localhost:8080',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify({
               code:queryUrl.code
            }),
            success: function (data) {
                console.log(data)
                if(data.code == 0){
                    d = data.data;
                    d.headimgurl!=""? img = d.headimgurl:img = "./img/headingurl.png";
                    name= decodeURIComponent(d.nickname) || "";
                    $(".bdl_img").attr("src",img);
                    $(".bdl_titleName").text(name);
                }
            },
            error: function (data) {
            	 alert(data.message);
            }
        });
        }
        $('#geren').click(function () {  //个人资料
            
            if(session.getItem("openid")){
                var phonenumber = '',openid= '',idcardnum= '',nickname='',headimgurl='';
                    phonenumber = session.getItem("phonenumber")||'';
                    openid = session.getItem("openid")||'';
                    nickname = session.getItem("nickname")||'';
                    headimgurl = session.getItem("headimgurl")||'';
                 var urlStr = "./new-personalData.html?phonenumber=" + phonenumber + "&openId=" + openid +"&idcardnum=" + '' + "&nickname=" + nickname +"&wxheadimgurl=" + headimgurl +"&brandId="+queryUrl.state+"";
                    alert(urlStr);
                 window.location.href = urlStr; 
            }else{
                var message = $(this).attr("message");
                if(!storage.getItem(d.openid)){
                     addBar(message);
                }else{
                    session.setItem("phonenumber",storage.getItem(d.openid));
                    session.setItem("openid",d.openid);
                    session.setItem("headimgurl",d.headimgurl);
                    session.setItem("nickname",d.nickname);
                    
                    //增加state
                    var urlStr = "./new-personalData.html?phonenumber=" + storage.getItem(d.openid) + "&openId=" + d.openid +"&idcardnum=" + '' + "&nickname=" + d.nickname +"&wxheadimgurl=" + d.headimgurl +"&brandId="+queryUrl.state+"";
                    alert(urlStr);
                    window.location.href = urlStr; 
                }
            }
        });
        
        $('#bdl_navR').click(function () {  //购车订单
            if(session.getItem("openid")){
                var phonenumber = '',openid= '',idcardnum= '',nickname='',headimgurl='';
                    phonenumber = session.getItem("phonenumber")||'';
                    openid = session.getItem("openid")||'';
                    nickname = session.getItem("nickname")||'';
                    headimgurl = session.getItem("headimgurl")||'';
                 var urlStr = "./paperlessOfficeSales/customerOrderList.html?phonenumber=" + phonenumber + "&openId=" + openid +"&idcardnum=" + '' + "&nickname=" + nickname +"&wxheadimgurl=" + headimgurl +"&brandId="+queryUrl.state+"";
                    alert(urlStr);
                 window.location.href = urlStr; 
            }else{
                var message = $(this).attr("message");
                if(!storage.getItem(d.openid)){
                    addBar(message);
                }else{

                session.setItem("phonenumber",storage.getItem(d.openid));
                session.setItem("openid",d.openid);
                session.setItem("headimgurl",d.headimgurl);
                session.setItem("nickname",d.nickname);

                var urlStr = "./paperlessOfficeSales/customerOrderList.html?phonenumber=" + storage.getItem(d.openid) + "&openId=" + d.openid +"&idcardnum=" + '' + "&nickname=" + d.nickname +"&wxheadimgurl=" + d.headimgurl +"&brandId="+queryUrl.state+"";
                alert(urlStr);
                window.location.href = urlStr;
                }
            }
        })
        function addBar(message){
            $(".boxTan").show();
            $("fg").val("");
            $(".btn").on("click",function(){
                if(message == "geren"){
                    if(session.getItem("openId")){
                        var phonenumber = '',openid= '',idcardnum= '',nickname='',headimgurl='';
                        phonenumber = session.getItem("phonenumber")||'';
                        openid = session.getItem("openid")||'';
                        // idcardnum = session.getItem("idcardnum")||'';
                        nickname = session.getItem("nickname")||'';
                        headimgurl = session.getItem("headimgurl")||'';
                        $(".boxTan").hide();
                        var urlStr = "./new-personalData.html?phonenumber=" + phonenumber + "&openId=" + openid +"&idcardnum=" + ' ' +"&nickname=" + nickname +"&wxheadimgurl=" + headimgurl +"&brandId="+queryUrl.state+"";
                        //alert(urlStr);
                        window.location.href = urlStr;
                    }else{
                        var phonenumber = '',idcardnum='';
                        phonenumber = $(".fg").val();
                        storage.setItem(d.openid,phonenumber);
                        session.setItem("phonenumber",phonenumber);
                        session.setItem("openid",d.openid);
                        session.setItem("headimgurl",d.headimgurl);
                        session.setItem("nickname",d.nickname);
                        $(".boxTan").hide();
                        var urlStr = "./new-personalData.html?phonenumber=" + phonenumber + "&openId=" + d.openid +"&idcardnum=" + ' ' + "&nickname=" + d.nickname +"&wxheadimgurl=" + d.headimgurl +"&brandId="+queryUrl.state+"";
                        //alert(urlStr);
                        window.location.href = urlStr;
                    }
                }else{
                     if(session.getItem("openId")){
                        var phonenumber = '',openid= '',idcardnum= '',nickname='',headimgurl='';
                        phonenumber = session.getItem("phonenumber")||'';
                        openid = session.getItem("openid")||'';
                        // idcardnum = session.getItem("idcardnum")||'';
                        headimgurl = session.getItem("headimgurl")||'';
                         nickname = session.getItem("nickname")||'';
                        $(".boxTan").hide();
                        var urlStr = "./customerOrderList.html?phonenumber=" + phonenumber + "&openId=" + openid +"&idcardnum=" + idcardnum + "&nickname=" + nickname +"&wxheadimgurl=" + headimgurl +"&brandId="+queryUrl.state+"";
                        //alert(urlStr);
                        window.location.href = urlStr;
                    }else{
                        var phonenumber = '',idcardnum='';
                        phonenumber = $(".fg").val();
                        storage.setItem(d.openid,phonenumber);
                        session.setItem("phonenumber",phonenumber);
                        session.setItem("openid",d.openid);
                        session.setItem("headimgurl",d.headimgurl);
                        // session.setItem("idcardnum",d.idcardnum);
                        session.setItem("nickname",d.nickname);
                        $(".boxTan").hide();
                        var urlStr = "./customerOrderList.html?phonenumber=" + phonenumber + "&openId=" + d.openid +"&idcardnum=" + idcardnum + "&nickname=" + d.nickname +"&wxheadimgurl=" + d.headimgurl +"&brandId="+queryUrl.state+"";
                        //alert(urlStr);
                        window.location.href = urlStr;
                    }
                }
            })
        }













