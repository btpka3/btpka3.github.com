import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {HeroDetailComponent} from "./hero-detail.component";
import {HeroModule} from "../hero/hero.module"
import {FormsModule} from "@angular/forms";

@NgModule({
  imports: [
    RouterModule.forChild([{path: '', component: HeroDetailComponent}]),
    CommonModule,
    FormsModule,
    HeroModule
  ],
  declarations: [HeroDetailComponent]
})
export class HeroDetailModule {

}
