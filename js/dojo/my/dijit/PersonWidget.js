define([
    "exports",
    "dojo/dom",
    "dojo/_base/declare",
    "dojo/parser",
    "dijit/_WidgetBase",
    "dijit/_TemplatedMixin",
    "dijit/_WidgetsInTemplateMixin",
    "dojo/text!my/dijit/templates/PersonWidget.html"
], function(
    exports,
    dom,
    declare,
    parser,
    _WidgetBase,
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    template
) {
    declare("my.dijit.PersonWidget", [
        _WidgetBase,
        _TemplatedMixin,
        _WidgetsInTemplateMixin
      ], {
        baseClass : 'personWidget',
        avatar : require.toUrl('dijit/images/defaultAvatar.png'),
        mouseAnim : null,
        templateString: template,
        bio : '',
        baseBackgroundColor : '#fff',
        mouseBackgroundColor : '#def',
        model:{name:'lilili'},
        name:'zhang3'
    });
});
