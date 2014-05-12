require([
     "dijit/form/Button",
     "dojox/form/Uploader",
     "dojox/form/uploader/FileList",
     "dojo/domReady!"
], function(
     Button,
     Uploader,
     FileList
){
    var myUploader = new dojox.form.Uploader({
        name:"file"
    }, "uploader");
    myUploader.startup();

    var s1 = new Button({
        label: "Submit",
        type:"submit"
    }, "s1");
    s1.startup();

    var fileList = new FileList({
        uploader:myUploader,
        style:"width:300px;",
        headerFilename:"文件名",
        headerFilesize:"大小",
        headerType:"类型"
    }, "fileList");
    fileList.startup();
});
