require(["dojo/store/Cache",
         "dojo/store/Observable",
         "dojo/store/Memory",
         "dojo/store/JsonRest",
         "dijit/form/FilteringSelect",
         "dojo/data/ItemFileReadStore",
         "dijit/form/Button",
         "dojo/dom",
         "dojo/domReady!"
], function(
             Cache,
             Observable,
             Memory,
             JsonRest,
             FilteringSelect,
             ItemFileReadStore,
             Button,
             dom
){
    var store = new JsonRest({
        target: "rs/user/",
        sortParam : "sortBy"
    });
    //store = new Observable(new Cache(store, new Memory()));

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
        label: "Range 1~10",
        onClick: function(){
            var o = store.query({},{
                start : 1,
                count : 10});
            o.then(function(data){
                console.info("data = ",data);
                display(data["data"]);
            });
        }}, "range1_10");

    new Button({
        label: "Range 11~20",
        onClick: function(){
            var o = store.query({},{
                start : 11,
                count : 10});
            o.then(function(data){
                console.info("data = ",data);
                display(data["data"]);
            });
        }}, "range11_20");


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
        label: "ShowAll",
        onClick: function(){
            // Memory is synchronous store
            var queryResults = store.query({},{
                start : 5,
                count : 5});
            display(queryResults);
            //queryResults.then(display, function(e){
            //    console.error(e);
            //});
        }}, "showAllBtn");

});
