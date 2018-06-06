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
 *   beforeOk: 确定前的回调(默认无,可不传)
 *   onOk: 点确定的回调(默认无,可不传)
 *   onCancel: 点取消或关闭的回调(默认无,可不传)
 * })
 * */

(function($){
    var dialogObj = {
        //初始化参数
        init: function (options) {
            options = $.extend({
                dialogType: 'alert',
                width: 'auto',
                height:　'auto',
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
                "<div class='content plugin_dialog_bd'></div>" +
                "<div class='standard Btn'>" +
                "<button class='cancelBtn send2 close' type='button'>取 消</button>" +
                "<button class='send' type='submit'>确 定</button>" +
                "</div></div></div></div>");
            switch (config.dialogType) {
                case "alert":
                    break;
                case "message":
                    //信息框 隐藏标题和按钮
                    container.find(".Btn").hide();
                    //自动关闭
                    setTimeout(function () {
                        container.remove();
                        config.onOk && config.onOk();
                    }, 3000);
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
            if(config.beforeOk){
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
            },100);
            that.container = container;
        },
        //初始化事件
        initEvents: function () {
            var that = this;
            //确定
            that.container.find(".send, .okBtn").click(function () {
                that.container.remove();
                that.config.onOk && that.config.onOk($(that.container).find('.content'));
            });
            //点击取消或者屏幕其他地方
            $(document).bind("click",function(e){//点击空白处，设置的弹框消失
                var $target = $(e.target);
                if($target.hasClass('cssStyle') || $target.hasClass('close')){
                    that.container.remove();
                    that.config.onCancel && that.config.config.onCancel()
                }
            });
        }
    };
    $.extend({
        dialog: function(options) {
            return $.extend({}, dialogObj).init(options);
        }
    });
})(jQuery);