import {NgModule} from "@angular/core";
import {SortChipComponent} from "./sort-chip.component";
import {SortChipRoutingModule} from "./sort-chip-routing.module";
import {HttpClientModule} from '@angular/common/http';
import {MatChipsModule} from '@angular/material/chips';
//import {BrowserModule} from '@angular/platform-browser';
import { FlexLayoutModule } from '@angular/flex-layout';
import { DragulaModule } from 'ng2-dragula';


@NgModule({
  imports: [
    //  BrowserModule,
    MatChipsModule,
    DragulaModule,
    SortChipRoutingModule,
    HttpClientModule,
    FlexLayoutModule
  ],
  declarations: [SortChipComponent]
})
export class SortChipModule {
}
