require(["dojo/store/Cache",
         "dojo/store/Observable",
         "dojo/store/Memory",
         "dojo/store/JsonRest",
         "dijit/form/FilteringSelect",
         "dojo/data/ItemFileReadStore",
         "dijit/form/Button",
         "dojo/dom",
         "dojo/aspect",
         "dojo/_base/lang",
         "dojo/domReady!"
], function(
             Cache,
             Observable,
             Memory,
             JsonRest,
             FilteringSelect,
             ItemFileReadStore,
             Button,
             dom,
             aspect,
             lang
){
    var store = new JsonRest({
        target: "rs/user/",
        sortParam : "sortBy"
    });
    //store = new Observable(new Cache(store, new Memory()));

    aspect.around(store, "query", function(orgQueryFunc){
        return function(){
            store.lastQuery = arguments[0];
            store.lastOptions = lang.mixin({}, arguments[1]);
            var results = orgQueryFunc.apply(this, arguments); // call the original
            results.then(function(data){
                console.info("sssss", results);

               var last = {start:0, end:0, total:0};
               if(data && data["data"]){
                   last.total = data["data"].length;
                   last.start = last.total - 1;
               }

               var str = results.ioArgs.xhr.getResponseHeader("Content-Range");
               var regex = /\s*(\d*)\s*-\s*(\d*)\s*\/\s*(\d*)\s*/;
               if(str && regex.test(str)){
                   var matches = regex.exec(str);
                   last.start = parseInt(matches[1]);
                   last.end = parseInt(matches[2]);
                   last.total = parseInt(matches[3]);
               }
               store.lastContentRange = last;
            });
            return results;
        };
    });

    function display(items){
        dom.byId("out").innerHTML = "";
        for(var i = 0; i < items.length; i++){
            dom.byId("out").innerHTML += "id : " + items[i].id + ", "
                + "name : " + items[i].name + ", "
                + "gender : " + (items[i].gender ? "M" : "F" ) + ", "
                + "birthday : " + items[i].birthday + ", "
                + "height : " + items[i].height + ", "
                + "avatarId : " + items[i].avatarId + ", "
                + "<br/>";
        }
    }

    new Button({
        label: "Range 0~9",
        onClick: function(){
            var o = store.query({},{
                start : 0,
                count : 10});
            console.info("check ioArgs ", o);
            o.then(function(data){
                console.info("data = ",data);
                display(data["data"]);
            });
        }}, "range0_9");

    new Button({
        label: "Range 10~19",
        onClick: function(){
            var o = store.query({},{
                start : 10,
                count : 10});
            o.then(function(data){
                display(data["data"]);
            });
        }}, "range10_19");

    new Button({
        label: "query name contains '3_1'",
        onClick: function(){
            var o = store.query({name:"3_1"});
            o.then(function(data){
                display(data["data"]);
            });
        }}, "queryName");

    new Button({
        label: "sort by height asc, gender desc",
        onClick: function(){
            var o = store.query({}, {
                sort:[{attribute:"height", descending: false},
                      {attribute:"gender", descending: true}]
            });
            o.then(function(data){
                display(data["data"]);
            });
        }}, "sortBtn");

    new Button({
        label: "Clear",
        onClick: function(){
            dom.byId("out").innerHTML = "";
        }}, "clear");

    new Button({
        label: "ShowId2",
        onClick: function(){
            var o =store.get(2);
            o.then(function(data){
                display([data]);
            });
        }}, "showId2");

    new Button({
        label: "Next page",
        onClick: function(){

            if(!(store.lastQuery || store.lastQuery)){
                console.error("no query ran before");
                return;
            }

            if(store.lastContentRange && store.lastContentRange["total"] > 0 && store.lastOptions && store.lastOptions["count"]){
                store.lastOptions["start"] =  store.lastContentRange["start"] + store.lastOptions["count"];
            }
            var o = store.query(store.lastQuery, store.lastOptions);
            o.then(function(data){
                display(data["data"]);
            });
        }}, "nextBtn");



});
