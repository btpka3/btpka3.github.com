import {NgModule} from "@angular/core";
import {IonicComponent} from "./ionic.component";
import {IonicRoutingModule} from "./ionic-routing.module";
//import {BrowserModule} from '@angular/platform-browser';
import {FlexLayoutModule} from '@angular/flex-layout';
//import {Button, App,IonicModule} from 'ionic-angular';

@NgModule({
  imports: [
    //  BrowserModule,
    IonicRoutingModule,
    FlexLayoutModule,
    //IonicModule
  ],
  declarations: [IonicComponent]
})
export class IonicDemoModule {
}
