// import {headerHtml, HeaderController} from '../header';
// import {footerHtml, FooterController} from '../footer';
// import {sidebarHtml, SidebarController} from '../sidebar';
// import {breadcrumbHtml, BreadcrumbController} from '../breadcrumb';
// import {sidebarSmHtml, SidebarSmController} from '../sidebar-sm';

// appLayoutRun.$inject = ['RouterHelper'];

appLayoutRun.$inject = ['$stateProvider'];
function appLayoutRun($stateProvider) {
    console.log("---------- angular.module('app.layout').run()");
    // RouterHelperProvider.configureStates(getStates());

    $stateProvider.state("app", {
        // abstract: true,
        views: {
            "@": {
                template: "---------bbb--------"
            }
        },
        url: '',
        // template: mainLayoutHtml

    });

    // $stateProvider.state("app.layout", {
    //     abstract: true,
    //     url: ''
    // });
}
//
function getStates() {
    return [
        {
            state: 'app',
            config: {
                abstract: true,
                url: '',
                // template: "---------bbb--------"
                //template: mainLayoutHtml
                views: {
                    "@": {
                        template: "---------bbb--------"
                    }
                },
            }
        },
        // {
        //     state: 'app.layout',
        //     config: {
        //         abstract: true,
        //         url: '',
        //         views: {
        //             // header: {
        //             //     template: headerHtml,
        //             //     controller: `${HeaderController.name} as vm`
        //             // },
        //             // sidebar: {
        //             //     template: sidebarHtml,
        //             //     controller: `${SidebarController.name} as vm`
        //             // },
        //             // breadcrumb: {
        //             //     template: breadcrumbHtml,
        //             //     controller: `${BreadcrumbController.name} as vm`
        //             // },
        //             // footer: {
        //             //     template: footerHtml,
        //             //     controller: `${FooterController.name} as vm`
        //             // },
        //             // 'sidebar-sm': {
        //             //     template: sidebarSmHtml,
        //             //     controller: `${SidebarSmController.name} as vm`
        //             // }
        //         }
        //     }
        // }
    ];
}

export default appLayoutRun;
