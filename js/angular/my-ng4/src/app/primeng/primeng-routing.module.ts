import {NgModule} from "@angular/core";
import {PrimeNgComponent} from "./primeng.component";
import {RouterModule} from "@angular/router";


@NgModule({
  imports: [
    RouterModule.forChild([{path: '', component: PrimeNgComponent}]),
  ],
  // exports: [RouterModule]
})
export class PrimeNgRoutingModule {
}
