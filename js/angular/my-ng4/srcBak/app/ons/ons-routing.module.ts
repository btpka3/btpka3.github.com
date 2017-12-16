import {NgModule} from "@angular/core";
import {OnsComponent} from "./ons.component";
import {RouterModule} from "@angular/router";


@NgModule({
  imports: [
    RouterModule.forChild([{path: '', component: OnsComponent}]),
  ],
  // exports: [RouterModule]
})
export class OnsRoutingModule {
}
