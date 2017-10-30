import {NgModule} from "@angular/core";
import {FlexComponent} from "./flex.component";
import {FlexRoutingModule} from "./flex-routing.module";
import {HttpClientModule} from '@angular/common/http';

//import {BrowserModule} from '@angular/platform-browser';
import { FlexLayoutModule } from '@angular/flex-layout';


@NgModule({
  imports: [
    //  BrowserModule,
    FlexRoutingModule,
    HttpClientModule,
    FlexLayoutModule
  ],
  declarations: [FlexComponent]
})
export class FlexModule {
}
