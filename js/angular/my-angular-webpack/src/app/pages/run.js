import h from "!html-loader?minimize=true!./index.root.html";

// "!ngtemplate-loader?module=main&relativeTo=src/app!html-loader?minimize=true!./index.root.html";
console.log("---00000----00000------",h);

function reg($templateCache){
    console.log("---1111----1111------put template to cache",h);
    $templateCache.put("/pages/index.root.html",h);
}

reg.$inject = ['$templateCache'];
export default reg;
