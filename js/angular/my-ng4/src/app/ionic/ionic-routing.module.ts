import {NgModule} from "@angular/core";
import {IonicComponent} from "./ionic.component";
import {RouterModule} from "@angular/router";


@NgModule({
  imports: [
    RouterModule.forChild([{path: '', component: IonicComponent}]),
  ],
  // exports: [RouterModule]
})
export class IonicRoutingModule {
}
