define([
    "exports",
    "dojo/dom",
    "dojo/_base/declare",
    "dojo/parser",
    "dijit/_WidgetBase",
    "dijit/_TemplatedMixin",
    "dijit/_WidgetsInTemplateMixin",  // 该类允许在HTML模板中使用其他的widget
    "dojo/text!my/dijit/templates/PersonWidget.html",
    "dijit/form/Button"       // 由于在HTML模板中使用了Button，所以必须先在该依赖声明中先声明一下，使相关资源预加载完毕
], function(
    exports,
    dom,
    declare,
    parser,
    _WidgetBase,
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    template,
    Button
) {
    return declare( [
        _WidgetBase,
        _TemplatedMixin,
        _WidgetsInTemplateMixin
      ], {
        baseClass : 'personWidget',
        mouseAnim : null,
        baseBackgroundColor : '#fff',
        mouseBackgroundColor : '#def',

        templateString: template, // 从 _TemplatedMixin 继承而来

        // 以下和PersonWidgetDemo.json.js中每个条目相对应的默认值
        name : "NO NAME",
        city : "Mars",
        avatar : require.toUrl('./images/defaultAvatar.png'),
        _setAvatarAttr: function(imagePath) {
          if (imagePath != "") {
            this._set("avatar", imagePath);
            this.avatarNode.src = imagePath;
        }
        },

        // 注意：由于这些属性与上面的属性在同一个平级，容易混淆，故建议将模型数据都统一细化到一个子属性中
        // model : {name:"", city:"", avata:""}

        // 通过 data-dojo-attach-event 设置Event处理函数
        _onmouseenter : function(){
          console.log("mouse entered name ["+this.name+"]");
        },
        _onmouseleave : function(){
          console.log("mouse left name ["+this.name+"]");
        }
    });
});
