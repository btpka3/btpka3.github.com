// 与网页进行交互（注意：这与JavaScript在页面中的 <script> 元素不一样）

//document.body.style.border = "5px solid red";

console.log("------------------zll : " + new Date());


browser.runtime.onMessage.addListener(function(msg){
    console.log("Message from the background script:");
    console.log(msg);
    return Promise.resolve({response: "Hi from content script"});
});



