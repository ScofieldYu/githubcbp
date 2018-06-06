/**
 * Created by ThinkPad User on 2018/1/11.
 */
$(function () {//获取页面数据
    var url = window.location.href; //获取当前页url
    var queryUrl = url.queryURLParameter(); //转query
    //监控根据code获取员工ID和token和账号类型
    var getEmployeeTimeBaseData = {
        code: queryUrl.code //用户code
        // code: '随便写' //用户code
    };
    var employeeid = null;  //当前用户ID
    var accouttype = null;  //当前用户角色
    var token = null;  //token

    var employeeId = window.sessionStorage.getItem("employeeId");
    var accountType = window.sessionStorage.getItem("accountType");
    var tokenStorage = window.sessionStorage.getItem("tokenStorage");

    if(employeeId && accountType && tokenStorage){
        employeeid = JSON.parse(employeeId);
        accouttype = JSON.parse(accountType);
        token = JSON.parse(tokenStorage);
        if(accouttype != 1){ //销售经理及以上"2" 销售顾问"1" ->为1时 隐藏顾问姓名搜索条件
            $("#accouttype").show()
        }
        initCondition();
        bindEvent()
    }else {
        $.ajax({
            type: "POST",
            url: srcConfig.getEmployeeidByCodeBase,
            // url: './js/data.txt',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(getEmployeeTimeBaseData),
            /* beforeSend: function (request) {
             request.setRequestHeader("MSESSIONID", token);
             },*/
            success: function (data) {
                if (data.code == 0) {
                    employeeid = data.data.employeeid;
                    accouttype = data.data.accouttype;
                    token = data.data.token;
                    window.sessionStorage.setItem("employeeId",JSON.stringify(employeeid));
                    window.sessionStorage.setItem("accountType",JSON.stringify(accouttype));
                    window.sessionStorage.setItem("tokenStorage",JSON.stringify(token));
                    if(accouttype != 1){ //销售经理及以上"2" 销售顾问"1" ->为1时 隐藏顾问姓名搜索条件
                        $("#accouttype").show()
                    }
                    initCondition();
                    bindEvent()
                } else {
                    alert(data.message)
                }
            },
            error: function (data) {
                alert(data)
            }
        });
    }
    //初始化筛选条件
    function initCondition() {
        // 初始化 Framework7
        var today = new Date();
        var myApp = new Framework7();
        var toolbarTemplate = '<div class="toolbar"><div class="toolbar-inner"><div class="left"><a href="#" class="link close-picker cancel">取消</a></div><div class="right"><a href="#" class="link close-picker">完成</a></div></div></div>';

        // 开始时间 / Framework7 picker
        myApp.picker({
            input: '#orderdateStart',
            rotateEffect: true,
            toolbarTemplate: toolbarTemplate,
            value: [today.getFullYear(), today.getMonth() + 1, today.getDate()],
            formatValue: function (p, values, displayValues) {
                return values[0] + '-' + values[1] + '-' + values[2];
            },
            cols: [
                // Years
                {
                    values: (function () {
                        var arr = [];
                        for (var i = 1950; i <= 2030; i++) {
                            arr.push(i);
                        }
                        return arr;
                    })(),
                    // content:'1991',
                    textAlign: 'center',
                    width: '33%'
                },
                // Months
                {
                    displayValues: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                    values: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12],
                    textAlign: 'center',
                    width: '33%'
                },
                // day
                {
                    values: (function () {
                        var arr = [];
                        for (var i = 1; i <= 31; i++) {
                            arr.push(i);
                        }
                        return arr;
                    })(),
                    textAlign: 'center',
                    width: '33%'
                }
            ],
            onOpen: function (picker) {
                picker.container.find('.cancel').on('click', function () {
                    picker.setValue(['不限'])
                });
            }
        });
        // 结束时间 / Framework7 picker
        myApp.picker({
            input: '#orderdateEnd',
            rotateEffect: true,
            toolbarTemplate: toolbarTemplate,
            value: [today.getFullYear(), today.getMonth() + 1, today.getDate()],
            formatValue: function (p, values, displayValues) {
                return values[0] + '-' + values[1] + '-' + values[2];
            },
            cols: [
                // Years
                {
                    values: (function () {
                        var arr = [];
                        for (var i = 1950; i <= 2030; i++) {
                            arr.push(i);
                        }
                        return arr;
                    })(),
                    textAlign: 'center',
                    width: '50%'
                },
                // Months
                {
                    displayValues: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                    values: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12],
                    textAlign: 'center',
                    width: '50%'
                },
                // day
                {
                    values: (function () {
                        var arr = [];
                        for (var i = 1; i <= 31; i++) {
                            arr.push(i);
                        }
                        return arr;
                    })(),
                    textAlign: 'center',
                    width: '33%'
                }
            ],
            onOpen: function (picker) {
                picker.container.find('.cancel').on('click', function () {
                    picker.setValue(['不限', '不限', '不限']);
                    console.log(picker.setValue)
                });
            }
        });
    }

    //绑定页面上的事件
    function bindEvent() {
        //开关按钮效果
        $("#issign").click(function () {
            $(this).toggleClass('on');
        });

        //保存按钮
        $('.searchBtn a').click(function () {
            var searchData = {
                employeename: $("#employeename").val(), //销售顾问姓名
                brandid: $("#brandid").val(), //门店id
                orderdateStart: $("#orderdateStart").val(), //订单开始日期
                orderdateEnd: $("#orderdateEnd").val(), //订单结束日期
                issign: (function () {
                    if ($("#issign").hasClass('on')) {
                        return 'Y'
                    } else {
                        return 'N'
                    }
                })(), //是否电子签
                loginemployeeid: employeeid, //登录人员工id
                accounttype: accouttype //销售经理及以上"2" 销售顾问"1"
            };
            //更新个人资料
            $.ajax({
                //async: false, //同步请求,阻止js继续执行
                type: "POST",
                url: srcConfig.showMonitorDetail,
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify(searchData),
                beforeSend: function (request) {
                    request.setRequestHeader("MSESSIONID", token);
                },
                success: function (data) {
                    if (data.code == 0) {
                        if (data.data.length == 0) {
                            alert('没有可查看的订单数据,请更换条件后重新查询')
                        } else {
                            window.sessionStorage.setItem('OrderList', JSON.stringify(data.data));
                            window.location.href = './orderMonitoringList.html';
                        }
                    } else {
                        alert(data.message)
                    }
                },
                error: function (data) {
                    alert(data.message)
                }
            })
        })
    }


});