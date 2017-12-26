import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
// import {AaaModule} from "./test-route/aaa/aaa.module";

import {MyWatchComponent} from "./core/watch/MyWatch.component";

import {AaaComponent} from "./test-route/aaa/aaa.component";
import {XxxComponent} from "./test-route/xxx/xxx.component";
import {X11Component} from "./test-route/xxx/x11/x11.component";
import {X12Component} from "./test-route/xxx/x12/x12.component";
import {X21Component} from "./test-route/xxx/x21/x21.component";
import {X22Component} from "./test-route/xxx/x22/x22.component";

import {YyyComponent} from "./test-route/xxx/yyy/yyy.component";
import {Y11Component} from "./test-route/xxx/yyy/y11/y11.component";
import {Y12Component} from "./test-route/xxx/yyy/y12/y12.component";
import {Y13Component} from "./test-route/xxx/yyy/y13/y13.component";

const routes: Routes = [
  {path: '', redirectTo: '/index.html', pathMatch: 'full'},
  //{path: 'index.html', component: AaaComponent}, // 该方式是直接打包到 main.bundle.js 中了
  {path: 'watch', component: MyWatchComponent},
  {path: 'index.html', component: AaaComponent}, // 该方式是直接打包到 main.bundle.js 中了
  {path: 'bbb', loadChildren: './test-route/bbb/bbb.module#BbbModule'},
  {path: 'ccc', loadChildren: './test-route/ccc/ccc.module#CccModule'},
  {
    path: 'xxx',
    component: XxxComponent,
    // http://localhost:4200/xxx/(header:11//footer:12)
    children: [

      // 路径组合方式总共有 2*3=6 种
      // http://localhost:4200/#/xxx/(header:11//footer:11)
      // http://localhost:4200/#/xxx/(header:11//footer:12)
      // http://localhost:4200/#/xxx/(header:11//footer:22)
      // http://localhost:4200/#/xxx/(header:21//footer:11)
      // http://localhost:4200/#/xxx/(header:21//footer:12)
      // http://localhost:4200/#/xxx/(header:21//footer:22)

      // header 部分可以使用的 路径/组件
      { outlet: 'header', path: '1/:id', component: X11Component},
      { outlet: 'header', path: '11', component: X11Component},
      { outlet: 'header', path: '21', component: X21Component},

      // footer 部分可以使用的 路径/组件
      { outlet: 'footer', path: '11', component: X11Component},
      { outlet: 'footer', path: '12', component: X12Component},
      { outlet: 'footer', path: '22', component: X22Component},

      { outlet: 'footer', path: 'yyy', component: YyyComponent, children: [
        { outlet: 'header2', path: '11', component: Y11Component},
        { outlet: 'header2', path: '12', component: Y12Component},
        { outlet: 'header2', path: '13', component: Y13Component},

        { outlet: 'footer2', path: '11', component: Y11Component},
        { outlet: 'footer2', path: '12', component: Y12Component},
        { outlet: 'footer2', path: '13', component: Y13Component},
      ]},
    ]
  },
  {path: 'http', loadChildren: './http/http.module#HttpModule'},
  {path: 'flex', loadChildren: './flex/flex.module#FlexModule'},
  {path: 'mat', loadChildren: './mat/mat.module#MatModule'},
  {path: 'primeng', loadChildren: './primeng/primeng.module#PrimeNgModule'},
  {path: 'ionic', loadChildren: './ionic/ionic.module#IonicDemoModule'},
  {path: 'clarity', loadChildren: './clarity/clarity.module#ClModule'},
  // {path: 'ons', loadChildren: './ons/ons.module#OnsModule'
  //
  //   /* canLoad: [AuthGuard] */
  // },
  {path: 'sort-chip', loadChildren: './sort-chip/sort-chip.module#SortChipModule'},
  {path: 'sort-select', loadChildren: './sort-select/sort-select.module#SortSelectModule'},
  {path: '**', redirectTo: ''},
];

@NgModule({
  imports: [
    // AaaModule,

    // HashLocationStrategy
    // PathLocationStrategy
    // RouterModule.forRoot(routes,{ useHash: true })
    RouterModule.forRoot(routes)
  ],

  exports: [RouterModule],
  providers: []
})
export class AppRoutingModule {
}

//export const routedComponents = [DashboardComponent, HeroesComponent, HeroDetailComponent];

