/**
 * try write my first jQuery plugin.
 */

"use strict";

// 使用比包，防止 $ 不是jQuery
(function ($) {

    function FirstJqPlugin(ele, options) {

        this.$element = $(ele);

        // 初始化参数
        this.opt = $.extend({}, $.fn.firstJqPlugin.defaults);
        if (options && typeof actionOrInitOptions == 'object') {
            $.extend(this.opt, options);
        }
        this.$element.on("click", "childElementSelector", this.eventHanlder1);
    }

    FirstJqPlugin.prototype.action1 = function (paraments) {


    };

    FirstJqPlugin.prototype.action2 = function (paraments) {

    };

    FirstJqPlugin.prototype.eventHanlder1 = function (e) {
        if (e) e.preventDefault();
        this.$element.trigger($.Event('youPreEvent', {"your": "eventData"}));
        // do something
        this.$element.trigger($.Event('youPostEvent', {"your": "eventData"}));
    };


    // 第一个参数如果是 object，就是构造用的参数
    // 第一个参数如果是 string，就是action/command
    function Plugin(actionOrInitOptions, actionParams) {

        // 始终使用each，因为jQuery可能查询到多个元素
        return this.each(function () {
            var $this = $(this);

            var data = $this.data('me.firstJqPlugin');
            if (!data) $this.data('bs.alert', (data = new FirstJqPlugin(this, actionOrInitOptions)));
            if (typeof actionOrInitOptions == 'string') data[actionOrInitOptions].call($this, actionParams)
        })
    }


    var old = $.fn.firstJqPlugin;

    $.fn.firstJqPlugin = Plugin


    $.fn.firstJqPlugin.noConflict = function () {
        $.fn.firstJqPlugin = old;
        return this
    };


    // 通过插件函数下面定义默认值的方式，允许用户修改默认值
    $.fn.firstJqPlugin.defaults = {
        foreground: "red",
        background: "yellow"
    };
}(jQuery));
