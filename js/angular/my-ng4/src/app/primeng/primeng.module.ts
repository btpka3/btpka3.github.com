import {NgModule} from "@angular/core";
import {PrimeNgComponent} from "./primeng.component";
import {PrimeNgRoutingModule} from "./primeng-routing.module";

import {
  AccordionModule,
  InputTextModule, ButtonModule, DataTableModule, DialogModule,
  SplitButtonModule,
  PaginatorModule
} from 'primeng/primeng';

@NgModule({
  imports: [
    //  BrowserModule,
    // AccordionModule,
    ButtonModule,
    PrimeNgRoutingModule,
    SplitButtonModule,
    PaginatorModule
  ],
  declarations: [PrimeNgComponent]
})
export class PrimeNgModule {
}
