/**
 * Created by ThinkPad User on 2018/3/7.
 */
$(function () {
    var url = window.location.href; //获取当前页url
    var urlQuery = url.queryURLParameter(); //转query

    /*  $.ajax({
     type: "POST",
     url: 'http://192.168.206.188:8889/blchina-cbp/card/selectCardDetails',
     dataType: 'json',
     contentType: "application/json",
     data: JSON.stringify({"cardid": "1"}),
     /!* beforeSend: function (request) {
     request.setRequestHeader("MSESSIONID", token);
     },*!/
     success: function (data) {
     console.log(data)
     },
     error: function (data) {
     console.log(data)
     }
     });*/

    function bindPriceList(data) {


    }

    // changePriceDialog(1000);

    $("#changePriceTest").click(function () {
        changePriceDialog(this)
    });

    //改价弹出框
    function changePriceDialog(ele) {
        //获取价格
        var priceNum = Number($(ele).attr("price"));

        var $changePrice = $("#changePrice");//直接改价
        var $discount = $("#discount");//折扣
        var $discountNum = $("#discountNum");//折扣价格
        //设置初始值
        $changePrice.val(priceNum);
        $discount.val(1);
        $discountNum.text(priceNum);
        //直接改价事件
        $changePrice.unbind().focus(function () {
            $(this).addClass("changeFlag"); //增加操作过的标识
            //直接改价激活时 把折扣价重置并移除修改标识
            $discount.removeClass("changeFlag").val(1);
            $discountNum.text(priceNum);
        });
        //折扣事件
        $discount.unbind().focus(function () {
            $(this).addClass("changeFlag"); //增加操作过的标识
            $changePrice.removeClass("changeFlag").val(priceNum);
            statInputNum($discount, $discountNum);
        });
        //折扣价计算
        function statInputNum(discount, discountNum) {
            discount.on('input propertychange', function () {
                var _value = $(this).val() * priceNum;
                discountNum.text(_value);
            });
        }

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
            var a = $(".changeFlag").val();
            console.log(a)

            // $(".ECSDialog").hide();
        })


    }


    $(".completeBtn a").click(function () {


        $.ajax({
            type: "POST",
            url: srcConfig.insertOrUpdateFourCustomer,
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(fourCustomerData),
            /* beforeSend: function (request) {
             request.setRequestHeader("MSESSIONID", token);
             },*/
            success: function (data) {
                console.log(data)
                if (data.code == 0) {
                    alert(data.message);
                    window.sessionStorage.setItem("customerId", JSON.stringify(data.data));
                    window.location.href = './selectCar.html?employeeid=' + urlQuery.employeeid;
                } else {
                    alert(data.message)
                }
            },
            error: function (data) {
                alert(data.message)
            }
        });
    })

});