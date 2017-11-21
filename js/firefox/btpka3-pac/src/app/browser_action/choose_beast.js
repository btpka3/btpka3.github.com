function hi() {
    console.log("------- hi : " + new Date());

}
console.log("------- a.js loaded : " + new Date());


function beastNameToURL(beastName) {
    switch (beastName) {
        case "Frog":
            return chrome.extension.getURL("icons/icon-16.png");
        case "Snake":
            return chrome.extension.getURL("icons/icon-32.png");
        case "Turtle":
            return chrome.extension.getURL("icons/icon-64.png");
    }
}



document.addEventListener("click", function(e) {
    if (!e.target.classList.contains("beast")) {
        return;
    }

    console.log("------- click : " + new Date());

    var chosenBeast = e.target.textContent;
    var chosenBeastURL = beastNameToURL(chosenBeast);

    chrome.tabs.executeScript(null, {
        file: "/content_scripts/beastify.js"
    });

    chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
        chrome.tabs.sendMessage(tabs[0].id, {beastURL: chosenBeastURL});
    });

});

