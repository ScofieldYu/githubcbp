/**
 * Created by ThinkPad User on 2018/2/6.
 */
$(function () {
    var url = window.location.href; //获取当前页url
    var urlQuery = url.queryURLParameter(); //转query

    var carLine = ''; //车系
    var placeofOrigin = ''; //进口或出口
    var carModels = ''; //车型
    var models = ''; //出产日期
    var appearance = ''; //外观
    var interiorTrim = ''; //内饰

    //选择车系按钮
    $(".selectCarLine").click(function () {
        $(".carLine").show()
            .find("#carLine")
            .removeClass('fadeOutRight')
            .addClass('fadeInRight animated')  //车系列表淡入效果
            .find('li').unbind('click')  //防止事件重复绑定
            .click(function () { //选择车系之后
                var $this = $(this);
                carLine = $this.text();  //把所选车系赋值暂存
                placeofOrigin = $this.parent().attr('carLine'); //把所选车系所属产地赋值暂存
                $(".selectCarLine").find(".selectContent").text(carLine);//把所选车系绑定到页面
                $("#carLine").removeClass('fadeInRight').addClass('fadeOutRight');  //车系列表隐藏
                //调用选择车型
                selectCarModels()
            })
    });
    //选择车型按钮
    $(".selectCarModels").click(function () {
        if (!carLine) {
            alert('请先选择车系');
            return
        }
        //调用选择车型
        selectCarModels()
    });

    function selectCarModels() {//车型列表显示
        $.ajax({ //获取车型列表
            async: false,
            type: "POST",
            url: '../js/paperlessOfficeSales/a.txt',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify({
                车系: carLine,
                进出口: placeofOrigin
            }),
            success: function (data) {
                if (data.code == 0) {//车型列表成功
                    var trueCarModels = data.data;
                    var carModelsStr = '';
                    for (var i = 0; i < trueCarModels.length; i++) {
                        var modelList = trueCarModels[i];
                        carModelsStr += '<p>' + modelList.model + '</p><ul models="' + modelList.model + '">';
                        for (var j = 0; j < modelList.carList.length; j++) {
                            carModelsStr += '<li>' + modelList.carList[j] + '</li>'
                        }
                        carModelsStr += '</ul>'
                    }
                    $('#carModels').html(carModelsStr);
                } else {
                    alert(data.message);
                }
            },
            error: function (data) {
                alert(data.message);
            }
        });
        $('.carModels').show()
            .removeClass('fadeOutRight')
            .addClass('fadeInRight animated')   //淡入效果
            .find('li').unbind('click')  //防止事件重复绑定
            .click(function () { //绑定选择车型事件
                var $this = $(this);
                carModels = $this.text();//把所选车型赋值暂存
                models = $this.parent().attr('models');//把所选车型出产日期赋值暂存
                $(".selectCarModels").find(".selectContent").text(placeofOrigin + ' ' + models + ' ' + carModels);//把所选车型绑定到页面
                $('.carModels').removeClass('fadeInRight').addClass('fadeOutRight');
                $('.carLine').hide();  //(如果有)车系选项背景隐藏
                window.setTimeout(function () { //隐藏页面
                    $('.carModels').hide();
                },800);
                Listening(); //执行选车条件监听
            });
    }

    //选择外观按钮
    $(".appearance").click(function () {
        if (!carLine || !carModels) {
            alert('请先选择车系和车型');
            return
        }
        $('.carAppearance').show()
            .removeClass('fadeOutRight')
            .addClass('fadeInRight animated')   //淡入效果
            .find('li').unbind('click')  //防止事件重复绑定
            .click(function () { //绑定选择外观事件
                var $this = $(this);
                appearance = $this.text();//把所选外观赋值暂存
                $(".appearance").find(".selectContent").text(appearance);//把外观绑定到页面
                $('.carAppearance').removeClass('fadeInRight').addClass('fadeOutRight');
                window.setTimeout(function () { //隐藏页面
                    $('.carAppearance').hide();
                },800);
                Listening(); //执行选车条件监听
            });
    });
    //选择内饰按钮
    $(".interiorTrim").click(function () {
        if (!carLine || !carModels) {
            alert('请先选择车系和车型');
            return
        }
        $('.carInteriorTrim').show()
            .removeClass('fadeOutRight')
            .addClass('fadeInRight animated')   //淡入效果
            .find('li').unbind('click')  //防止事件重复绑定
            .click(function () { //绑定选择外观事件
                var $this = $(this);
                interiorTrim = $this.text();//把所选外观赋值暂存
                $(".interiorTrim").find(".selectContent").text(interiorTrim);//把外观绑定到页面
                $('.carInteriorTrim').removeClass('fadeInRight').addClass('fadeOutRight');
                window.setTimeout(function () { //隐藏页面
                    $('.carInteriorTrim').hide();
                },800);
                Listening(); //执行选车条件监听
            });
    });

    function Listening() {
        //监听当所有的条件都选择完毕的时候 才会显示车辆的详细信息
        if (carLine && placeofOrigin && carModels && models && appearance && interiorTrim) {
            $(".carImgAndPrice").show();
            //车架号选择
            $(".VIN").unbind().click(function () {
                /*
                 * 剩余字数统计
                 * 注意 最大字数只需要在放数字的节点哪里直接写好即可 如：<var class="word">200</var>
                 */
                function statInputNum(textArea, numItem) {
                    var max = numItem.text(),
                        curLength;
                    textArea.attr("maxlength", max);
                    curLength = textArea.val().length;
                    numItem.text(max - curLength);
                    textArea.on('input propertychange', function () {
                        var _value = $(this).val().replace(/\n/gi, "");
                        numItem.text(max - _value.length);
                    });
                }
                statInputNum($("#selectVINReason"), $(".word"));

                //出现弹出框
                $(".ECSDialog").show();
                var dialogHeight = $(".ECSDialogContent").height();
                $(".ECSDialogContent").css({  //渲染到正确位置
                    "marginTop": -(dialogHeight / 2),
                    "opacity": 1
                });

                //点击背景 隐藏弹出框
                $(".ECSShadow").unbind("click").click(function () {
                    $(".ECSDialog").hide();
                });

                //弹出框取消按钮
                $(".ECSDialogContent-btn-cancel").unbind("click").click(function () {
                    $(".ECSDialog").hide();
                });

                //弹出框确定按钮
                $(".ECSDialogContent-btn-sure").unbind("click").click(function () {
                    var VIN = $("#selectVINInput").val();
                    var VINReason = $("#selectVINReason").val();
                    if (!VIN) {
                        alert("车架号必须输入");
                        return;
                    } else if (!(/^[a-zA-Z0-9]{17}$/.test(VIN))) {
                        alert("车架号输入不合法");
                        return;
                    }
                    if (!VINReason) {
                        alert("选择原因必须输入");
                        return;
                    }

                    $("#realvinno").text(VIN);
                    $(".ECSDialog").hide();
                })

            });
            $(".completeBtn a").addClass('on').unbind().click(function () {
                //从页面上获取车辆信息
                var selectCarInfo = {
                    employeeid: urlQuery.employeeid, //员工id
                    carseries: $("#carseries").text(), //车辆车系
                    cartype: $("#cartype").text(), //车辆车型
                    carappearance: $("#carappearance").text(), //车辆外观
                    carinterior: $("#carinterior").text(), //车辆内饰
                    realvinno: $("#realvinno").text(), //车架号
                    customerid: JSON.parse(window.sessionStorage.getItem("customerId")) //客户id
                };

                if (urlQuery.orderid) { //证明是从订单页面跳转过来的
                    selectCarInfo.orderid = urlQuery.orderid;
                }

                $.ajax({
                    type: "POST",
                    url: srcConfig.chooseCar,
                    dataType: 'json',
                    contentType: "application/json",
                    data: JSON.stringify(selectCarInfo),
                    /* beforeSend: function (request) {
                     request.setRequestHeader("MSESSIONID", token);
                     },*/
                    success: function (data) {
                        if (data.code == 0) {
                            alert(data.message);
                            window.location.href = './orderDetails.html?employeeid=' + urlQuery.employeeid + '&orderid=' + data.data;
                        } else {
                            alert(data.message)
                        }
                    },
                    error: function (data) {
                        alert(data.message)
                    }
                });
            })
        }
    }


});