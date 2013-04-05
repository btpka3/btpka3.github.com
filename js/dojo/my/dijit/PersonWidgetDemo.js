require([
    "dojo/dom",
    "dojo/request",
    "dojo/json",
    "my/dijit/PersonWidget",
    "dijit/form/Button",
    "dojo/dom-class",
    "dojo/_base/window",
    "dojo/domReady!"
], function(
    dom,
    request,
    json,
    PersonWidget,
    Button,
    domClass,
    win
){
  var callback = function(data) {
    console.log("callback : " + json.stringify(data));
    try {
      for ( var i = 0; i < data.length; i++) {
        console.log("callback : " + i);
        var personWidget = new PersonWidget(data[i]);
        personWidget.startup();
        personWidget.placeAt("listContainer");
      }
    } catch (e) {
      console.error("ERROR : " + e);
    }
  };
  var errback = function(err) {
    console.log("errback : " + json.stringify(err));
  };
  var progback = function(evt) {
    console.log("progback : " + json.stringify(evt));
  };
  var reqPromise = request("PersonWidgetDemo.json.js", {
    "method" : "GET",
    "handleAs" : "json"
  });
  reqPromise.then(callback, errback, progback);

  new Button({
    postCreate: function(){
      this.onClick();
    },
    onClick: function(){
      if(!this.i){
          this.i = 1;
      }
      this.i++;
      var themes = ["myTheme1","myTheme2"] ;
      domClass.remove(win.body(), themes);
      var nextTheme = themes[this.i % themes.length];
      domClass.add(win.body(), nextTheme);
      this.set("label" , "Change Theme ： " + nextTheme);
    }}, "chageThemeBtn");
});
