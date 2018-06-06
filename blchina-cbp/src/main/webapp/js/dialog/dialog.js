/**
 * Created by ThinkPad User on 2017/11/22.
 */
/*
 * 依赖于jquery
 *
 *调用方法
 * $.dialog({
 *   dialogType: 'alert' ,//alert message(过三秒后消失) justOk(仅有确定按钮) 有四种快捷弹窗格式可选(默认alert)
 *   content: 提示信息内容，可以html对象，也可以是html文件(调用时需要ajaxGET请求文件名) 必须传；
 *   width: 0.3rem, //弹出框的宽度,以rem为单位的字符串值
 *   height: auto, //弹出框的高度,以rem为单位的字符串值
 *   title: title, //弹出框标题,必传
 *   beforeOk: 确定前的回调(默认无,可不传)
 *   onOk: 点确定的回调(默认无,可不传)
 *   onCancel: 点取消或关闭的回调(默认无,可不传)
 *   leftBtn: 左侧按钮名称
 *   rightBtn: 右侧按钮名称
 * })
 * */

(function ($) {
    var dialogObj = {
        //初始化参数
        init: function (options) {
            options = $.extend({
                dialogType: 'alert',
                width: 'auto',
                height: 'auto',
                title: 'title',
                leftBtn: '取消',
                rightBtn: '确认',
                beforeOk: null,
                onOk: null,
                onCancel: null
            }, options);
            this.config = options;
            var that = this;
            //加载样式
            var href = $("script[src$='dialog/dialog.js']").attr("src").replace("dialog.js", "dialog.css");
            if ($("link[href='" + href + "']").length == 0) {
                $("head").append("<link href='" + href + "' rel='stylesheet'/>");
            }
            that.initDom();
            that.initEvents();
            return that;
        },
        //加载DOM结构
        initDom: function () {
            var that = this;
            var config = this.config;
            //创建文档碎片
            var container = $("" +
                "<div class='plugin_dialog'>" +
                "<div class='plugin_dialog_bg'></div>" +
                "<div class='cssStyle'>" +
                "<div class='plugin_dialog_content scale'>" +
                "<div class='plugin_dialog_tit'>" + config.title + "</div>" +
                "<div class='content plugin_dialog_bd'></div>" +
                "<div class='standard Btn'>" +
                "<button class='plugin_dialog_btn close' type='button'>"+config.leftBtn+"</button>" +
                "<button class='plugin_dialog_btn send' type='submit'>"+config.rightBtn+"</button>" +
                "</div></div></div></div>");
            switch (config.dialogType) {
                case "alert":
                    break;
                case "message":
                    //信息框 隐藏标题和按钮
                    container.find(".Btn").hide();
                    container.find(".plugin_dialog_content ").css('background', 'linear-gradient(to bottom, #FFFFFF, #FAFAFA 75%, #ececec 85%, #c8c8c8');
                    //自动关闭
                    setTimeout(function () {
                        container.remove();
                        config.onOk && config.onOk();
                    }, 1500);
                    break;
                case "message1":
                    //信息框 隐藏标题和按钮
                    container.find(".Btn").hide();
                    container.find(".plugin_dialog_content ").css('background', 'linear-gradient(to bottom, #FFFFFF, #FAFAFA 90%, #ececec 95%, #c8c8c8');
                    break;
                case "justOk":
                    //信息框 隐藏标题和按钮
                    container.find(".Btn").html('<button class="okBtn">确  定</button>');
                    break;
            }
            container.find(".content").append(config.content);
            $('body').append(container);
            //先执行确定或取消前的回调
            // var beforeData;
            if (config.beforeOk) {
                config.beforeOk();
            }
            // config.beforeOk && config.beforeOk();
            //设置宽高和默认上下左右居中
            var $doc = $('.plugin_dialog_content'); //获取元素暂存
            $doc.css('width', config.width);//宽度赋值
            $doc.css('height', config.height);//高度赋值
            //必须先设置宽高后才能确定弹窗层位置
            $doc.css({
                left: '50%',
                top: '50%',
                marginLeft: -($doc.width() / 2),
                marginTop: -($doc.height() / 2)
            });
            window.setTimeout(function () {
                $('.plugin_dialog').addClass('show')
            }, 100);
            that.container = container;
        },
        //初始化事件
        initEvents: function () {
            var that = this;
            //确定
            that.container.find(".send, .okBtn, .dialogCloseBtn").click(function () {
                that.container.remove();
                that.config.onOk && that.config.onOk($(that.container).find('.content'));
            });
            //点击取消或者屏幕其他地方
            $(document).unbind('click').bind("click", function (e) {//点击空白处，设置的弹框消失
                var $target = $(e.target);
                if ($target.hasClass('cssStyle') || $target.hasClass('close')) {
                    that.container.remove();
                    that.config.onCancel && that.config.onCancel();
                }
            });
        }
    };
    $.extend({
        dialog: function (options) {
            return $.extend({}, dialogObj).init(options);
        }
    });
})(jQuery);