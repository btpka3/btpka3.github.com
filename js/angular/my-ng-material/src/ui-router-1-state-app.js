angular
    .module('app')
    .run([function () {
        $stateProviderRef.state("app.aaa", {
            url: "/aaa",

            views: {
                "@app": {
                    template: '<div> === <span ui-view=""></span> ===',
                }
            }
        });
        console.log("------------ 'ui-router-1-state-app.js' run.");
    }]);

console.log("------------ 'ui-router-1-state-app.js' loaded.");


// angular
//     .module('app')
//     .run([function () {
//         // 如果是这种方式，该run方法将不会执行
//     }]);