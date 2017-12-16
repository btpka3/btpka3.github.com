import {NgModule} from "@angular/core";
import {FlexComponent} from "./flex.component";
import {RouterModule} from "@angular/router";


@NgModule({
  imports: [
    RouterModule.forChild([{path: '', component: FlexComponent}]),
  ],
  // exports: [RouterModule]
})
export class FlexRoutingModule {
}
