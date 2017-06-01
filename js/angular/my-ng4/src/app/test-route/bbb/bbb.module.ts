import {NgModule} from "@angular/core";
import {BbbComponent} from "./bbb.component";
import {RouterModule} from "@angular/router";
import {BbbRoutingModule} from "./bbb-routing.module";

@NgModule({
  imports: [
    BbbRoutingModule,
  ],
  declarations: [BbbComponent]
})
export class BbbModule {
}
