import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {HeroesComponent} from "./heroes.component";
import {HeroDetailComponent} from "./hero-detail.component";
import {DashboardComponent} from "./dashboard.component";

const routes: Routes = [
    {path: '', redirectTo: '/dashboard', pathMatch: 'full'},
    {path: 'detail/:id', component: HeroDetailComponent},
    {path: 'dashboard', component: DashboardComponent},
    {path: 'heroes', component: HeroesComponent}
];
@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}

export const routedComponents = [DashboardComponent, HeroesComponent, HeroDetailComponent];

