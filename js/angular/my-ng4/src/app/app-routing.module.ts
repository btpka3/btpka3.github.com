import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
// import {AaaModule} from "./test-route/aaa/aaa.module";
import {AaaComponent} from "./test-route/aaa/aaa.component";

const routes: Routes = [
  {path: '', redirectTo: '/index.html', pathMatch: 'full'},
  {path: 'index.html', component: AaaComponent}, // 该方式是直接打包到 main.bundle.js 中了
  {path: 'bbb', loadChildren: './test-route/bbb/bbb.module#BbbModule'},
  {path: 'ccc', loadChildren: './test-route/ccc/ccc.module#CccModule'},
  {path: 'http', loadChildren: './http/http.module#HttpModule'},
  {path: 'flex', loadChildren: './flex/flex.module#FlexModule'},
  {path: 'mat', loadChildren: './mat/mat.module#MatModule'},
  {path: 'primeng', loadChildren: './primeng/primeng.module#PrimeNgModule'},
  {path: 'ionic', loadChildren: './ionic/ionic.module#IonicDemoModule'},
  {path: 'clarity', loadChildren: './clarity/clarity.module#ClModule'},
  {path: 'ons', loadChildren: './ons/ons.module#OnsModule'

    /* canLoad: [AuthGuard] */
  },
  {path: 'sort-chip', loadChildren: './sort-chip/sort-chip.module#SortChipModule'},
  {path: 'sort-select', loadChildren: './sort-select/sort-select.module#SortSelectModule'},
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

