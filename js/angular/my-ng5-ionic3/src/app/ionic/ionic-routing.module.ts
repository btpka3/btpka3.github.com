import {NgModule} from "@angular/core";
import {IonicComponent} from "./ionic.component";
import {RouterModule} from "@angular/router";

import {MyDatetimeComponent} from "./myDatetime/myDatetime.component";
import {MyInputsComponent} from "./myInputs/myInputs.component";
import {MySegmentButtonComponent} from "./mySegmentButton/mySegmentButton.component";
import {MySelectComponent} from "./mySelect/mySelect.component";
import {MyTabsComponent} from "./myTabs/myTabs.component";
import {MyToastComponent} from "./myToast/myToast.component";
import {MyToolbarComponent} from "./myToolbar/myToolbar.component";

@NgModule({
  imports: [
    RouterModule.forChild([
      {path: '', component: IonicComponent},
      {path: 'myDatetime', component: MyDatetimeComponent},
      {path: 'myInputs', component: MyInputsComponent},
      {path: 'mySegmentButton', component: MySegmentButtonComponent},
      {path: 'mySelect', component: MySelectComponent},
      {path: 'myTabs', component: MyTabsComponent},
      {path: 'myToast', component: MyToastComponent},
      {path: 'myToolbar', component: MyToolbarComponent},
    ]),
  ],
  // exports: [RouterModule]
})
export class IonicRoutingModule {
}
