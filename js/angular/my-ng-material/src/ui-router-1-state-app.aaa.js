angular
    // .module('app') // ERROR : angular.js:13236 State 'app.aaa' is already defined
    .module('app.aaa',[])
    .run([function () {
        $stateProviderRef.state("app.aaa", {
            url: "/aaa",
            views: {
                "@app": {
                    template: 'aaa {{1+2}}'
                }
            }
        });
        console.log("------------ 'ui-router-1-state-app.aaa.js' run.");
    }]);


// Uncaught Error: State 'app.aaa' is already defined
// $stateProviderRef.state("app.aaa", {
//     url: "/aaa",
//     views: {
//         "@app": {
//             template: 'aaa {{1+2}}'
//         }
//     }
// });

console.log("------------ 'ui-router-1-state-app.aaa.js' loaded.");


// angular
//     .module('app')
//     .run([function () {
//         // 如果是这种方式，该run方法将不会执行
//     }]);