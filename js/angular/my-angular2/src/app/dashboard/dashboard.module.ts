import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";

import {DashboardComponent} from "./dashboard.component";
import {HeroSearchModule} from "../hero-search/hero-search.module";
import {HeroModule} from "../hero/hero.module"

@NgModule({
  imports: [
    RouterModule.forChild([{path: '', component: DashboardComponent}]),
    CommonModule,
    HeroSearchModule,
    HeroModule
  ],
  declarations: [DashboardComponent]
})
export class DashboardModule {
}
