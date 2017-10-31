import {NgModule} from "@angular/core";
import {OnsComponent} from "./ons.component";
import {OnsRoutingModule} from "./ons-routing.module";
//import { OnsenModule } from 'ngx-onsenui';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import {OnsExModule} from "../ons-ex/ons-ex.module";
import {
  Component,
  OnsRange,
} from 'ngx-onsenui';
@NgModule({
  imports: [
    //OnsenModule,
    OnsExModule,
    OnsRoutingModule
  ],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA,
  ],
  declarations: [OnsComponent]
})
export class OnsModule {
}
