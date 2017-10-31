import {NgModule} from "@angular/core";
import {ClComponent} from "./clarity.component";
import {RouterModule} from "@angular/router";


@NgModule({
  imports: [
    RouterModule.forChild([{path: '', component: ClComponent}]),
  ],
  // exports: [RouterModule]
})
export class ClRoutingModule {
}
