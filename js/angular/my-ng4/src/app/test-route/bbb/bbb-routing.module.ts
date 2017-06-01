import {NgModule} from "@angular/core";
import {BbbComponent} from "./bbb.component";
import {RouterModule} from "@angular/router";


@NgModule({
  imports: [
    RouterModule.forChild([{path: '', component: BbbComponent}]),
  ],
  // exports: [RouterModule]
})
export class BbbRoutingModule {
}
