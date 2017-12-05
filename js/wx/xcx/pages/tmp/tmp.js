var data = {
    data: {
        h1: "handler1",
        h2: "handler2"
    },
    handler1: function () {
        console.log("handler1");
    },
    handler2: function () {
        console.log("handler2");
    },
    obj: {
        nestedHandler: function () {
            console.log("nestedHandler");
        },
    },

};

data["a.b"] = data.obj.nestedHandler;
Page(data);
