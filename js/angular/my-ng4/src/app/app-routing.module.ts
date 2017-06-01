import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
// import {AaaModule} from "./test-route/aaa/aaa.module";
import {AaaComponent} from "./test-route/aaa/aaa.component";

const routes: Routes = [
  {path: '', redirectTo: '/aaa', pathMatch: 'full'},
  {path: 'aaa', component: AaaComponent},
  {path: 'bbb', loadChildren: './test-route/bbb/bbb.module#BbbModule'},
  {path: 'ccc', loadChildren: './test-route/ccc/ccc.module#CccModule'}
];

@NgModule({
  imports: [
    // AaaModule,
    RouterModule.forRoot(routes)
  ],

  exports: [RouterModule],
  providers: []
})
export class AppRoutingModule {
}

//export const routedComponents = [DashboardComponent, HeroesComponent, HeroDetailComponent];

