import {NgModule} from "@angular/core";
import {RouterModule, Routes,PreloadAllModules} from "@angular/router";
import {AaaComponent} from "./aaa/aaa.component";
import {BpacStatusComponent} from "./bpac/status/status.component";
// import {AaaModule} from "./test-route/aaa/aaa.module";

const routes: Routes = [
  //{path: '', redirectTo: '/index.html', pathMatch: 'full'},
  {path: '', component: AaaComponent}, // 该方式是直接打包到 main.bundle.js 中了
  {path: 'flex', loadChildren: './flex/flex.module#FlexModule'},
  {path: 'mat', loadChildren: './mat/mat.module#MatModule'},
  {path: 'ionic', loadChildren: './ionic/ionic.module#IonicDemoModule'},

  {path: 'bpac/status', component: BpacStatusComponent},

  {path: '**', redirectTo: ''}
];

@NgModule({
  imports: [
    // AaaModule,

    // HashLocationStrategy
    // PathLocationStrategy
    // RouterModule.forRoot(routes,{ useHash: true })
    RouterModule.forRoot(routes,{ preloadingStrategy: PreloadAllModules })
  ],

  exports: [RouterModule],
  providers: []
})
export class AppRoutingModule {
}

//export const routedComponents = [DashboardComponent, HeroesComponent, HeroDetailComponent];

