import {NgModule} from "@angular/core";
import {IonicComponent} from "./ionic.component";
import {IonicRoutingModule} from "./ionic-routing.module";
//import {BrowserModule} from '@angular/platform-browser';
import {FlexLayoutModule} from '@angular/flex-layout';

import {MatSidenavModule} from '@angular/material/sidenav';


import {IonicModule} from 'ionic-angular';


import {MyDatetimeComponent} from "./myDatetime/myDatetime.component";
import {MyInputsComponent} from "./myInputs/myInputs.component";
import {MySegmentButtonComponent} from "./mySegmentButton/mySegmentButton.component";
import {MySelectComponent} from "./mySelect/mySelect.component";
import {MyTabsComponent} from "./myTabs/myTabs.component";
import {MyToastComponent} from "./myToast/myToast.component";
import {MyToolbarComponent} from "./myToolbar/myToolbar.component";

@NgModule({
  imports: [
    //  BrowserModule,
    IonicRoutingModule,
    FlexLayoutModule,
    //IonicModule
    IonicModule,

    MatSidenavModule,
  ],
  declarations: [
    IonicComponent,
    MyDatetimeComponent,
    MyInputsComponent,
    MySegmentButtonComponent,
    MySelectComponent,
    MyTabsComponent,
    MyToastComponent,
    MyToolbarComponent,
  ]
})
export class IonicDemoModule {
}
