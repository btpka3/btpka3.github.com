import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
// import {HeroesComponent} from "./heroes.component";
// import {HeroDetailComponent} from "./hero-detail.component";
// import {DashboardComponent} from "./dashboard.component";

// const routes: Routes = [
//     {path: '', redirectTo: '/dashboard', pathMatch: 'full'},
//     {path: 'detail/:id', component: HeroDetailComponent},
//     {path: 'dashboard', component: DashboardComponent},
//     {path: 'heroes', component: HeroesComponent}
// ];
const routes: Routes = [
  {path: '', redirectTo: '/dashboard', pathMatch: 'full'},
  {path: 'detail/:id', loadChildren: './hero-detail/hero-detail.module#HeroDetailModule'},
  {path: 'dashboard', loadChildren: "./dashboard/dashboard.module#DashboardModule"},
  {path: 'heroes', loadChildren: "./heroes/heroes.module#HeroesModule"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: []
})
export class AppRoutingModule {
}

//export const routedComponents = [DashboardComponent, HeroesComponent, HeroDetailComponent];

