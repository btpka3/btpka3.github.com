import {NgModule} from "@angular/core";
import {HttpComponent} from "./http.component";
import {RouterModule} from "@angular/router";


@NgModule({
  imports: [
    RouterModule.forChild([{path: '', component: HttpComponent}]),
  ],
  // exports: [RouterModule]
})
export class HttpRoutingModule {
}
