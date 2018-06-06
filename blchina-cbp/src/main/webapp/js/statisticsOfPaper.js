/**
 * Created by ThinkPad User on 2018/2/28.
 */
$(function () {//获取页面数据
    var url = window.location.href; //获取当前页url
    var queryUrl = url.queryURLParameter(); //转query
    //监控根据code获取员工ID和token和账号类型
    var getEmployeeTimeBaseData = {
        code: queryUrl.code //用户code
    };
    var employeeid = null;  //当前用户ID
    // var employeeid = 20488;  //当前用户ID
    var accouttype = null;  //当前用户角色
    var token = null;  //token
    $.ajax({
        type: "POST",
        async: false,
        url: srcConfig.getEmployeeidByCodeBase,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(getEmployeeTimeBaseData),
        /* beforeSend: function (request) {
         request.setRequestHeader("MSESSIONID", token);
         },*/
        success: function (data) {
            if (data.code == 0) {
                employeeid = data.data.employeeid;
                // accouttype = data.data.accouttype;
                // token = data.data.token;
            } else {
                alert(data.message)
            }
        },
        error: function (data) {
            alert(data)
        }
    });
    $.ajax({
        type: "POST",
        url: srcConfig.getBrandStoreByEmployeeId,
        // url: './js/electronicContractStatistics.txt',
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify({
            employeeId: employeeid
        }),
        success: function (data) {
            if (data.code == 0) {
                /*     if (accouttype != 1) { //销售经理及以上"2" 销售顾问"1" ->为1时 隐藏顾问姓名搜索条件
                 $("#accouttype").show()
                 }*/
                bindScreenCondition(data.data);
            } else {
                alert(data.message)
            }
        },
        error: function (data) {
            alert(data)
        }
    });

    //初始化筛选条件
    function bindScreenCondition(data) {

        //初始化搜索条件

        var searchBrandList, //    品牌列表
            searchStoreList; //门店列表

        // 初始化 Framework7
        var today = new Date();
        var myApp = new Framework7();
        var toolbarTemplate = '<div class="toolbar"><div class="toolbar-inner"><div class="left"><a href="#" class="link close-picker cancel">今天</a></div><div class="right"><a href="#" class="link close-picker">完成</a></div></div></div>';
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
                    picker.setValue([today.getFullYear(), today.getMonth() + 1, today.getDate()]);
                });
            }
        });

        //累计按钮点击事件
        $(".Cumulative").click(function () {
            var $this = $(this);
            if ($this.hasClass('on')) {
                $(this).removeClass('on').siblings().find('input').removeAttr('disabled')
            } else {
                $(this).addClass('on').siblings().find('input').attr('disabled', 'disabled');
                $("#orderdateStart").val("1980-1-1");
                $("#orderdateEnd").val([today.getFullYear(), today.getMonth() + 1, today.getDate()].join('-'))
            }
        });

        //品牌选择选项和门店选择选项数据绑定
        var brandList = []; //所有品牌ID集合
        var storeList = []; //所有门店ID集合
        var BrandHtml = '<a id="brandAll" class="on"><span>全部</span></a>';
        var StoreHtml = '<a id="storeAll" class="on"><span>全部</span></a>';
        for (var i = 0; i < data.length; i++) {
            var curBrandData = data[i];
            brandList.push(curBrandData.brandType);//所有品牌ID集合
            BrandHtml += '<p brandType="' + curBrandData.brandType + '"><span>' + curBrandData.brandName + '</span></p>';
            for (var j = 0; j < curBrandData.brandStore.length; j++) {
                var curStoreData = curBrandData.brandStore[j];
                storeList.push(curStoreData.brandId);//所有门店ID集合
                StoreHtml += '<p brandType="' + curBrandData.brandType + '" brandId="' + curStoreData.brandId + '"><span>' + curStoreData.storeCnShort + '</span></p>'
            }
        }
        //赋值要搜索的条件 -> 没有进行点击操作时默认为全选
        searchBrandList = brandList; //要搜索的品牌ID集合
        searchStoreList = storeList; //要搜索的有门店ID集合
        //获取DOM元素
        var $brand = $("#ECSBrand");
        var $Store = $("#ECSStore");
        //写入DOM
        $brand.html(BrandHtml); //品牌选项写入DOM
        $Store.html(StoreHtml); //门店选项写入DOM
        //品牌 -> 事件绑定和全选
        $brand.find("a").click(function () { //全选按钮点击事件
            var $this = $(this);
            $this.addClass("on").siblings().removeClass("on");
            searchBrandList = brandList; //赋值门店搜索条件
            $Store.html(StoreHtml); //门店选项写入DOM
            storeSelectEvent(); //门店选项事件绑定
        });
        $brand.find("p").click(function () { //普通按钮点击事件
            var $this = $(this);
            $this.toggleClass("on");
            $brand.find("a").removeClass("on");

            //通关选择项生成门店选项列表
            var allBrand = $brand.find("p");
            var allSelect = [];
            var newStoreHtml = '';
            for (var i = 0; i < allBrand.length; i++) { //遍历被选中的元素并取出品牌id数据
                if ($(allBrand[i]).hasClass("on")) {
                    allSelect.push($(allBrand[i]).attr("brandtype"));
                    newStoreHtml = bulidStoreHtml(allSelect);
                    searchBrandList = allSelect; //赋值品牌搜索条件
                }
            }
            //当被选中的元素等于全部元素 或者被选中的元素为0时  将选项置为全选
            if (allBrand.length == allSelect.length || allSelect.length == 0) {
                $brand.find("a").addClass("on").siblings().removeClass("on");
                newStoreHtml = StoreHtml; //选中的元素为0时 要把所有门店数据再次赋值
                searchBrandList = brandList; //赋值品牌搜索条件
            }
            $Store.html(newStoreHtml); //把所有门店数据写入门店
            storeSelectEvent();//写入后重新绑定click事件

            //通过被被选中的元素生成门店DOM数据
            function bulidStoreHtml(list) {
                var str = '<a id="storeAll" class="on"><span>全部</span></a>';
                for (var i = 0; i < list.length; i++) {
                    for (var j = 0; j < data.length; j++) {
                        if (list[i] == data[j].brandType) {
                            for (var x = 0; x < data[j].brandStore.length; x++) {
                                var curStoreData = data[j].brandStore[x];
                                str += '<p brandType="' + list[i] + '" brandId="' + curStoreData.brandId + '"><span>' + curStoreData.storeCnShort + '</span></p>'
                            }
                        }
                    }
                }
                return str
            }

        });
        // 门店 -> 事件绑定和全选
        storeSelectEvent(); //先默认绑定一遍
        function storeSelectEvent() {
            //初始化当前所有门店id数据
            var curStoreList = [];
            for (var i = 0; i < $Store.find("p").length; i++) {
                curStoreList.push($($Store.find("p")[i]).attr("brandid"))
            }
            searchStoreList = curStoreList; //赋值门店搜索条件

            $Store.find("a").click(function () {//门店全选按钮
                var $this = $(this);
                $this.addClass("on").siblings().removeClass("on");
                searchStoreList = curStoreList; //赋值门店搜索条件
            });
            $Store.find("p").click(function () {
                var $this = $(this);
                $this.toggleClass("on");
                $Store.find("a").removeClass("on");

                //当选择项全部选中的时候
                var allBrand = $Store.find("p");
                var allSelect = [];
                for (var i = 0; i < allBrand.length; i++) {
                    if ($(allBrand[i]).hasClass("on")) {
                        allSelect.push($(allBrand[i]).attr("brandid"));
                        searchStoreList = allSelect; //赋值门店搜索条件
                    }
                }
                if (allBrand.length == allSelect.length || allSelect.length == 0) {
                    $Store.find("a").addClass("on").siblings().removeClass("on");
                    searchStoreList = storeList; //赋值门店搜索条件
                }
            });
        }

        //点击搜索按钮
        $("#ECSSearch").unbind().click(function () {
            //页面切换效果
            $(".ECSFilter").toggle("fast");
            $(".ECSResult").toggle("fast");
            $(".ECSBtn").toggle("fast");

            //获取搜索条件数据
            var orderdateStart = $("#orderdateStart").val();
            var orderdateEnd = $("#orderdateEnd").val();
            var searchCriteria = {
                dateStart: orderdateStart, //开始时间
                dateEnd: orderdateEnd, //结束时间
                brandIdList: searchStoreList //门店id
            };

            $.ajax({
                type: "POST",
                url: srcConfig.getPaperCosts,
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify(searchCriteria),
                success: function (data) {
                    if (data.code == 0) {
                        bindSearchResult(data.data);
                        bindEvent()
                    } else {
                        alert(data.message)
                    }
                },
                error: function (data) {
                    alert(data)
                }
            });
        })
    }

    //把搜索结果绑定到页面上
    function bindSearchResult(data) {
        var brandName = []; //品牌
        $("#ECSBrand").find(".on").each(function (index, element) {
            var name = $(element).find("span").text();
            brandName.push(name);
        });
        var StoreName = []; //门店
        $("#ECSStore").find(".on").each(function (index, element) {
            var name = $(element).find("span").text();
            StoreName.push(name);
        });

        var searchDate = $("#orderdateStart").val().split("-").join(".") + "-" + $("#orderdateEnd").val().split("-").join(".");

        //渲染搜索条件
        $("#searchCriteria").text(searchDate + "  " + brandName.join("、") + "品牌 " + StoreName.join("、") + "门店 ");

        //表格内的时间标题
        $("#searchDate").text(searchDate);
        //表格内数据
        var storeAndEmployeeNameStr = ''; //左侧门店名称和员工名称
        var listSearchResultStr = '';  //右侧相应数据
        for (var i = 0; i < data.length; i++) {
            var curData = data[i];
            storeAndEmployeeNameStr += '<li class="' + (curData.brandId ? "TB-store" : "") + '">' + curData.storeCnShort + '</li>';

            listSearchResultStr += '<ul class="' + (curData.brandId ? "TB-store" : "") + '">';
            listSearchResultStr += '<li>' + curData.paperTotalCount + '</li>'; //纸张数量
            listSearchResultStr += '<li>' + curData.paperTotalPrice + '</li>'; //纸张成本
            listSearchResultStr += '</ul>'
        }
        $("#storeAndEmployeeName").html(storeAndEmployeeNameStr);//渲染左侧门店名称和员工名称
        $("#listSearchResult").html(listSearchResultStr)//渲染右侧数据
    }

    //绑定页面上的事件
    function bindEvent() {
        //筛选条件收起展开按钮效果
        $("#filterBtn").unbind().click(function () {
            $(".ECSFilter").toggle("fast");
            $(".ECSResult").toggle("fast");
            $(".ECSBtn").toggle("fast");
        });


        //数据展示切换左右按钮
        var skip = 0;
        $(".TB-inner").css({"marginLeft": 0});
        $("#ECSBtnLeft").unbind().click(function () {
            skip--;
            if (skip <= 0) {
                skip = 0;
                $(this).css("opacity", 0);
                $(".TB-inner").animate({"marginLeft": 0})
            } else {
                $("#ECSBtnRight").css("opacity", 1);
                $(".TB-inner").animate({"marginLeft": -(skip * 2.85) + "rem"})
            }
        });
        $("#ECSBtnRight").unbind().click(function () {
            skip++;
            if (skip >= 2) {
                skip = 2;
                $(this).css("opacity", 0);
                $(".TB-inner").animate({"marginLeft": "-5.7rem"})
            } else {
                $("#ECSBtnLeft").css("opacity", 1);
                $(".TB-inner").animate({"marginLeft": -(skip * 2.85) + "rem"})
            }
        });

    }


});