import {NgModule} from "@angular/core";
import {BbbComponent} from "./bbb.component";
import {RouterModule} from "@angular/router";


@NgModule({
  imports: [
    RouterModule.forChild([{path: '', component: BbbComponent}]),
  ],
  exports: [RouterModule] // 该行需要的
})
export class BbbRoutingModule {
}
