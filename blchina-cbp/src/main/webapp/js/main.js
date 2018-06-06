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
        alert("已更改");
        var strUrl = window.location.href;
        var queryUrl = strUrl.queryURLParameter();
        var d = null,idcardnum = '',phonenumber="";
        if(session.getItem("openid")){   
            //alert("走的session");
            //  if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test($(".fg").val())))
            var img = '',name = '';
            img = session.getItem("headimgurl");
            name = decodeURIComponent(session.getItem('nickname'));

            $(".numBBB").show();
            $(".number").val(session.getItem("phonenumber"));
            $(".bdl_img").attr("src",img);
            $(".bdl_titleName").text(name);
        }else{

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
        //个人资料
        $('#geren').click(function () {  
            
            if(session.getItem("openid")){
                if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test($(".number").val()))){
                        alert('请输入正确的手机格式');
                        return false;
                }else{
                    var phonenumber = $(".number").val();
                
                    var openid= '',idcardnum= '',nickname='',headimgurl='';
                        
                        openid = session.getItem("openid")||'';
                        nickname = session.getItem("nickname")||'';
                        headimgurl = session.getItem("headimgurl")||'';
                        session.setItem("phonenumber",phonenumber);
                        storage.setItem(openid,phonenumber);
                    var urlStr = "./new-personalData.html?phonenumber=" + phonenumber + "&openId=" + openid +"&idcardnum=" + '' + "&nickname=" + nickname +"&wxheadimgurl=" + headimgurl +"&brandId="+queryUrl.state+"";
                        alert(urlStr);
                    window.location.href = urlStr; 
                }
                
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
         //购车订单
        $('.bdl_nav').click(function () { 
            if(session.getItem("openid")){
                if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test($(".number").val()))){
                        alert('请输入正确的手机格式');
                        return false;
                }else{
                    var phonenumber = $(".number").val();
                
                    var openid= '',idcardnum= '',nickname='',headimgurl='';
                        
                        openid = session.getItem("openid")||'';
                        nickname = session.getItem("nickname")||'';
                        headimgurl = session.getItem("headimgurl")||'';
                        session.setItem("phonenumber",phonenumber);
                        storage.setItem(openid,phonenumber);
                        
                    var urlStr = "./new-carOrder.html?phonenumber=" + phonenumber + "&openId=" + openid +"&idcardnum=" + '' + "&nickname=" + nickname +"&wxheadimgurl=" + headimgurl +"&brandId="+queryUrl.state+"";
                        alert(urlStr);
                    window.location.href = urlStr; 
                }
            }else{
                var message = $(this).find(".bdl_navRi").attr("message");
                if(!storage.getItem(d.openid)){
                    addBar(message);
                }else{
                    
                session.setItem("phonenumber",storage.getItem(d.openid));
                session.setItem("openid",d.openid);
                session.setItem("headimgurl",d.headimgurl);
                session.setItem("nickname",d.nickname);

                var urlStr = "./new-carOrder.html?phonenumber=" + storage.getItem(d.openid) + "&openId=" + d.openid +"&idcardnum=" + '' + "&nickname=" + d.nickname +"&wxheadimgurl=" + d.headimgurl +"&brandId="+queryUrl.state+"";
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
                    if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test($(".fg").val()))){
                        alert('请输入正确的手机格式');
                    }else{
                        if(session.getItem("openId")){
                            var phonenumber = '',openid= '',idcardnum= '',nickname='',headimgurl='';
                            phonenumber = session.getItem("phonenumber")||'';
                            openid = session.getItem("openid")||'';
                            // idcardnum = session.getItem("idcardnum")||'';
                            nickname = session.getItem("nickname")||'';
                            headimgurl = session.getItem("headimgurl")||'';
                            $(".boxTan").hide();
                            var urlStr = "./new-personalData.html?phonenumber=" + phonenumber + "&openId=" + openid +"&idcardnum=" + ' ' +"&nickname=" + nickname +"&wxheadimgurl=" + headimgurl +"&brandId="+queryUrl.state+"";
                            alert(urlStr);
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
                            alert(urlStr);
                            window.location.href = urlStr;
                        }
                    } 
                }else{
                    if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test($(".fg").val()))){
                        alert('请输入正确的手机格式');
                    }else{
                        if(session.getItem("openId")){
                            var phonenumber = '',openid= '',idcardnum= '',nickname='',headimgurl='';
                            phonenumber = session.getItem("phonenumber")||'';
                            openid = session.getItem("openid")||'';
                            // idcardnum = session.getItem("idcardnum")||'';
                            headimgurl = session.getItem("headimgurl")||'';
                            nickname = session.getItem("nickname")||'';
                            $(".boxTan").hide();
                            var urlStr = "./new-carOrder.html?phonenumber=" + phonenumber + "&openId=" + openid +"&idcardnum=" + idcardnum + "&nickname=" + nickname +"&wxheadimgurl=" + headimgurl +"&brandId="+queryUrl.state+"";
                            alert(urlStr);
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
                            var urlStr = "./new-carOrder.html?phonenumber=" + phonenumber + "&openId=" + d.openid +"&idcardnum=" + idcardnum + "&nickname=" + d.nickname +"&wxheadimgurl=" + d.headimgurl +"&brandId="+queryUrl.state+"";
                            alert(urlStr);
                            window.location.href = urlStr;
                        }
                    }
                }
            })
        }













