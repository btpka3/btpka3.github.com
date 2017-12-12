import {NgModule} from "@angular/core";
import {SortSelectComponent} from "./sort-select.component";
import {RouterModule} from "@angular/router";

@NgModule({
  imports: [
    RouterModule.forChild([{path: '', component: SortSelectComponent}]),
  ],
  // exports: [RouterModule]
})
export class SortSelectRoutingModule {
}
