import {NgModule} from "@angular/core";
import {SortChipComponent} from "./sort-chip.component";
import {RouterModule} from "@angular/router";

@NgModule({
  imports: [
    RouterModule.forChild([{path: '', component: SortChipComponent}]),
  ],
  // exports: [RouterModule]
})
export class SortChipRoutingModule {
}
