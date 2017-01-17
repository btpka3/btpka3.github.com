angular
    .module('app.ccc',[])
    .run([function () {
        $stateProviderRef.state("app.ccc", {
            url: "/ccc",

            views: {
                "@app": {
                    template: 'ccc {{1+2}}'

                }
            }
        });
        console.log("------------ 'ui-router-1-state-ccc.js' loaded.")
    }]);



