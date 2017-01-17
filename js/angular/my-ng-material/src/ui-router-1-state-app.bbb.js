angular
    .module('app.bbb',[])
    .run([function () {
        $stateProviderRef.state("app.bbb", {
            url: "/bbb",

            views: {
                "@app": {
                    template: 'bbb {{1+2}}'

                }
            }
        });
        console.log("------------ 'ui-router-1-state-bbb.js' loaded.")
    }]);


