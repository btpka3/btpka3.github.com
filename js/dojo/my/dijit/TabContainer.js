require( [ "dojo/ready", "dojo/parser", "dijit/registry",
    "dijit/layout/TabContainer", "dijit/layout/ContentPane","dojo/back" ], function(ready,
    parser, registry, TabContainer, ContentPane,back) {
  ready(function() {

    var tc = new TabContainer( {
      style : "height: 100%; width: 100%;"
    }, "tc1-prog");

    var cp1 = new ContentPane( {
      title : "Food",
      content : "We offer amazing food"
    });
    tc.addChild(cp1);

    var cp2 = new ContentPane( {
      title : "Drinks",
      content : "We are known for our drinks."
    });
    tc.addChild(cp2);

    tc.startup();


    back.addToHistory({
      back: function(){ console.log("back : cp1"); },
      forward: function(){ console.log("forward : cp1"); },
      changeUrl: "cp1"
    });
//    back.addToHistory({
//      back: function(){ console.log("back : cp2"); },
//      forward: function(){ console.log("forward : cp2"); },
//      changeUrl: "cp2"
//    });
    //back.init();
  });
});
