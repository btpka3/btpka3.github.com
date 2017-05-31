import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {HeroesComponent} from "./heroes.component";
import {HeroModule} from "../hero/hero.module"

@NgModule({
  imports: [
    RouterModule.forChild([{path: '', component: HeroesComponent}]),
    CommonModule,
    HeroModule
  ],
  declarations: [HeroesComponent],
  exports: [HeroesComponent]
})
export class HeroesModule {
}
