import {NgModule} from "@angular/core";
import {ClComponent} from "./clarity.component";
import {ClRoutingModule} from "./clarity-routing.module";
import {HttpClientModule} from '@angular/common/http';
import { ClarityModule } from "clarity-angular";
//import {BrowserModule} from '@angular/platform-browser';


@NgModule({
  imports: [
    //  BrowserModule,
    ClRoutingModule,
    HttpClientModule,
    ClarityModule
  ],
  declarations: [ClComponent]
})
export class ClModule {
}
