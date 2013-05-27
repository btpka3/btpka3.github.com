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
         "dijit/Dialog",
         "dijit/registry",
         "dojo/on",
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
             lang,
             Dialog,
             registry,
             on
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
               store.data = data;
            });
            return results;
        };
    });

    function display(items){
        dom.byId("out").innerHTML = "";
        for(var i = 0; i < items.length; i++){
            dom.byId("out").innerHTML += "id : " + items[i].id + ", "
                + "name : " + items[i].name + ", "
                + "gender : " + items[i].gender + ", "
                + "birthday : " + items[i].birthday + ", "
                + "height : " + items[i].height + ", "
                + "avatarId : " + items[i].avatarId + ", "
                + "<br/>";
        }
    }


    /////////////////////////////////////////////////////////////////////
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
            var o = store.query({name:"3_1"}, { count : 10});
            o.then(function(data){
                display(data["data"]);
            });
        }}, "queryName");

    new Button({
        label: "sort by height asc, gender desc",
        onClick: function(){
            var o = store.query({}, {
                count : 10,
                sort:[{attribute:"height", descending: false},
                      {attribute:"gender", descending: true}]
            });
            o.then(function(data){
                display(data["data"]);
            });
        }}, "sortBtn");

    /////////////////////////////////////////////////////////////////////

    new Button({
        label: "Clear",
        onClick: function(){
            dom.byId("out").innerHTML = "";
        }}, "clear");



    /////////////////////////////////////////////////////////////////////
    new Button({
        label: "add",
        onClick: function(){
            var newUser = {"name":"zhang3_10999","gender":true,"birthday":486489600000,"height":182,"avatarId":1};
            var o = store.add(newUser);
            o.then(function(data){
                if (o.ioArgs.xhr.status == 201) { // Created
                    new Dialog({
                        title: "Info",
                        content: "Success! access url is "+ o.ioArgs.xhr.getResponseHeader("Location"),
                        style: "width: 600px"
                    }).show();
                } else {
                    new Dialog({
                        title: "Error",
                        content: "ERROR : response status = "+o.ioArgs.xhr.status,
                        style: "width: 600px"
                    }).show();
                }
            });
        }}, "add");

    new Button({
        label: "get2",
        onClick: function(){
            var o =store.get(2);
            o.then(function(data){
                display([data]);
            });
        }
    }, "get2");

    new Button({
        label: "update2",
        onClick: function(){
            var o = store.get(2);
            o.then(function(data){
                data.height ++;
                var o1 = store.put(data, {id: data.id});
                o1.then(function(){
                    if (o1.ioArgs.xhr.status == 204) { // No Content
                        new Dialog({
                            title: "Info",
                            content: "Update Success! ",
                            style: "width: 600px"
                        }).show();
                        console.info("xxxx",registry.byId("get2"));
                        on.emit(registry.byId("get2"), "Click",{
                            bubbles: true,
                            cancelable: true
                        });
                    } else {
                        new Dialog({
                            title: "Error",
                            content: "ERROR : response status = "+o.ioArgs.xhr.status,
                            style: "width: 600px"
                        }).show();
                    }
                });
            });
        }
    }, "update2");

    new Button({
        label: "delete2",
        onClick: function(){
            var o = store.remove(2);
            o.then(function(data){
                if (o.ioArgs.xhr.status == 204) { // No Content
                    new Dialog({
                        title: "Info",
                        content: "Update Success! ",
                        style: "width: 600px"
                    }).show();
                } else {
                    new Dialog({
                        title: "Error",
                        content: "ERROR : response status = "+o.ioArgs.xhr.status,
                        style: "width: 600px"
                    }).show();
                }
            });
        }
    }, "delete2");

    /////////////////////////////////////////////////////////////////////

    new Button({
        label: "Pre page",
        onClick: function(){

            if(!(store.lastQuery || store.lastQuery)){
                new Dialog({
                    title: "Error",
                    content: "no query ran before",
                    style: "width: 600px"
                }).show();
                return;
            }

            if(!store.lastOptions || !("count" in store.lastOptions)){
                new Dialog({
                    title: "Error",
                    content: "last query is runing without paging",
                    style: "width: 600px"
                }).show();
                return;
            }
            if(!store.lastOptions["start"]){
                store.lastOptions["start"] = 0;
            }

            if(store.lastOptions["start"] == 0 ){
                new Dialog({
                    title: "Error",
                    content: "First reached, could not forward any more.",
                    style: "width: 600px"
                }).show();
                return;
            }
            store.lastOptions["start"] = store.lastOptions["start"] - store.lastOptions["count"];
            if(store.lastOptions["start"] < 0){
                store.lastOptions["start"] = 0;
            }
            var o = store.query(store.lastQuery, store.lastOptions);
            o.then(function(data){
                display(data["data"]);
            });

        }}, "preBtn");

    new Button({
        label: "Next page",
        onClick: function(){

            if(!(store.lastQuery || store.lastQuery)){
                new Dialog({
                    title: "Error",
                    content: "no query ran before",
                    style: "width: 600px"
                }).show();
                return;
            }

            if(!store.lastOptions || !("count" in store.lastOptions)){
                new Dialog({
                    title: "Error",
                    content: "last query is runing without paging",
                    style: "width: 600px"
                }).show();
                return;
            }
            if(!store.lastOptions["start"]){
                store.lastOptions["start"] = 0;
            }

            if(store.lastOptions["start"] + store.lastOptions["count"] >= store.lastContentRange["total"]){
                new Dialog({
                    title: "Error",
                    content: "End reached, could not forward any more.",
                    style: "width: 600px"
                }).show();
                return;
            }
            store.lastOptions["start"] =  store.lastContentRange["start"] + store.lastOptions["count"];

            var o = store.query(store.lastQuery, store.lastOptions);
            o.then(function(data){
                display(data["data"]);
            });
        }}, "nextBtn");



});
