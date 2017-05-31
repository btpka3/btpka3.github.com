import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {HeroSearchComponent} from "./hero-search.component";

@NgModule({
  imports: [
    RouterModule.forChild([{path: '', component: HeroSearchComponent}]),
    CommonModule
  ],
  declarations: [HeroSearchComponent],
  exports: [HeroSearchComponent]
})
export class HeroSearchModule {
}
