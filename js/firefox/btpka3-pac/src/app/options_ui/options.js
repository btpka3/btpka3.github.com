document.getElementById("btn").addEventListener('click', function () {
    var msg = {msg: '9999 aaa' + new Date()};
    console.log(msg);

    browser.tabs.query({
        currentWindow: true
    }).then(function (tabs) {
        sendMessageToTabs(tabs[0], msg);
    });


});

function sendMessageToTabs(tab, msg) {
    browser.tabs.sendMessage(
        tab.id,
        msg
    ).then(response => {
        console.log("Message from the content script:");
        console.log(response);
    }).catch(function (err) {
        console.log("=====err : ", err);
    });
}
