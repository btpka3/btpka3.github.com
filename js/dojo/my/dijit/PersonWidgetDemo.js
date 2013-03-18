require([
    "dojo/ready",
    "dojo/dom",
    "dojo/request",
    "dojo/json",
    "my/dijit/PersonWidget"
], function(
    ready,
    dom,
    request,
    json,
    PersonWidget
){
  ready(function(){
    var callback = function(data){
      console.log("callback : " + json.stringify(data));
      try{
      for ( var i = 0; i < data.length; i++) {
        console.log("callback : " + i);
        var personWidget = new PersonWidget({
          modelData : data[i]
        });
        personWidget.startup();
        personWidget.placeAt("listContainer");
        console.log("callback : " + json.stringify(personWidget));
      }
      }catch(e){
        console.error("ERROR + "+e);
      }
    };
    var errback = function(err){
      console.log("errback : " + json.stringify(err));
    };
    var progback = function(evt){
      console.log("progback : " + json.stringify(evt));
    };
    var reqPromise = request( "PersonWidgetDemo.json.js", {
        method : "GET",
        handleAs : "json"
    });
    reqPromise.then( callback, errback, progback);
  });

});
