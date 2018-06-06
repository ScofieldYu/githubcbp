/*
 下拉列表
 依赖：jQuery
 */
/*$.dropdownlist({
    container: $("#select1"),  //承载下拉列表的容器（jquery对象）
    value: undefined,   //当前的值{value:value,text:text} 无当前选中值
    defValue: undefined,   //默认显示的字段
    autoShow: false,    //true 情况下就默认选项是打开的
    data: [{ value: "aa", text: '查看合同' }, { value: "de", text: '查看收据' }],  //data数据大于6个，分两列展示，没有当前选中值
    afterSelect: function (selectedItem) {  //下拉选中后执行的事件，参数：selectedItem选中项
        console.log(selectedItem);
        alert("你选择了" + selectedItem.text);
    },
})*/
;(function ($) {
    $.extend({
        dropdownlist: function (config) {
            config = config == undefined || config == null ? {} : config;
            var dropObj = {
                //初始化控件
                init: function (config) {
                    config = $.extend({
                        container: {},  //承载下拉列表的容器（jquery对象）
                        value: undefined,   //当前的值
                        defValue: undefined,   //默认显示值
                        autoShow: false,    //是否自动显示下拉项
                        data: [], //数据源（数组）,value是下拉项的值，text是下拉项的显示文本
                        afterSelect: function (selectedItem) {}//下拉选中后执行的事件，参数：selectedItem选中项
                    }, config);
                    var thiz = this;
                    thiz.config = config;

                   /* //1.加载dom
                    var baseUrl = $("script[src$='dropdownlist.js']").attr("src").replace("dropdownlist.js", "");
                    //加载样式表
                    var styleUrl = baseUrl + "style.css";
                    if ($("link[href='" + styleUrl + "']").length < 1) {
                        $("head").append("<link href='" + styleUrl + "' rel='stylesheet' type='text/css' />");
                    }*/
                    //加载html内容
                    var html = '<div class="plugin_dropdownlist_div"><label></label><i class="arrow"></i><ul></ul></div>';
                    thiz.config.container.html(html);
                    //绑定数据
                    thiz.bindData();
                    //绑定事件
                    thiz.bindEvents();
                    return thiz;
                }
                //绑定数据源
                , bindData: function () {
                    var thiz = this;
                    /* 设置下拉框高度和行高及ul距父元素的top值 */
                    var ul = thiz.config.container.find("ul");
                    var lis = "";
                    for (var i = 0; i < thiz.config.data.length; i++) {
                        lis += "<li val='" + thiz.config.data[i].value + "' title='" + thiz.config.data[i].text + "'>" + thiz.config.data[i].text + "</li>";
                    }
                    ul.html(lis);
                    //默认显示
                    thiz.config.container.find("label").text(thiz.config.defValue || '请选择').attr("val", "");
                    //显示当前值
                    if (thiz.config.value) {
                        thiz.doSelect(thiz.config.container.find("li[val='" + thiz.config.value.value + "']"));
                        thiz.config.container.find("label").text(thiz.config.value.text);
                    }
                }
                //绑定事件
                , bindEvents: function () {
                    var thiz = this;
                    //下拉事件
                    thiz.config.container.find(".plugin_dropdownlist_div").unbind().click(function () {
                        thiz.doDropDown();
                    }).mouseleave(function () {
                        thiz.doHideDropDown();
                    });
                    //选择事件
                    $('.plugin_dropdownlist_div', thiz.config.container).delegate("li", "click", function () {
                        thiz.doSelect($(this));
                    });
                    if (thiz.config.autoShow) {
                        thiz.config.container.find(".plugin_dropdownlist_div").click();
                    }
                }
                //下拉事件
                , doDropDown: function () {
                    var thiz = this;
                    var ul = thiz.config.container.find("ul");
                    if (ul.is(":visible")) {
                        ul.hide();
                    } else {
                        ul.show();
                    }
                }
                //收起下拉事件
                , doHideDropDown: function () {
                    var thiz = this;
                    var ul = thiz.config.container.find("ul");
                    ul.hide();
                    //ie下会有蓝色的框，失去焦点，去掉蓝框
                    thiz.config.container.find(".plugin_dropdownlist_div").blur();
                }
                //选择下拉项
                , doSelect: function (li) {
                    var thiz = this;
                    li.addClass("active").siblings().removeClass("active");
                    thiz.config.container.find("label").text(li.text()).attr("val", li.attr("val"));
                    if (typeof (thiz.config.afterSelect) == "function") {
                        thiz.config.afterSelect({value: li.attr("val"), text: li.text()});
                    }
                }
            };
            dropObj.init(config);
            return dropObj;
        }
    });
})(jQuery);
