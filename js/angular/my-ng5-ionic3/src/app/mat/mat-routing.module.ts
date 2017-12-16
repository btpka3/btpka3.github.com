import {NgModule} from "@angular/core";
import {MatComponent} from "./mat.component";
import {RouterModule} from "@angular/router";


@NgModule({
  imports: [
    RouterModule.forChild([{path: '', component: MatComponent}]),
  ],
  // exports: [RouterModule]
})
export class MatRoutingModule {
}
