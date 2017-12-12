import {NgModule} from "@angular/core";
import {SortSelectComponent} from "./sort-select.component";
import {SortSelectRoutingModule} from "./sort-select-routing.module";
import {HttpClientModule} from '@angular/common/http';
//import {BrowserModule} from '@angular/platform-browser';
import {FlexLayoutModule} from '@angular/flex-layout';
import {DragulaModule} from 'ng2-dragula';
import {MatSelectModule} from '@angular/material/select';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {MatChipsModule} from '@angular/material/chips';
@NgModule({
  imports: [
    //  BrowserModule,
    FormsModule,
    MatChipsModule,
    MatSelectModule,
    DragulaModule,
    SortSelectRoutingModule,
    HttpClientModule,
    FlexLayoutModule,
    CommonModule
  ],
  declarations: [SortSelectComponent]
})
export class SortSelectModule {
}
