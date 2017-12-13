import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import { FormsModule } from '@angular/forms';


import {FlexLayoutModule} from '@angular/flex-layout';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HashLocationStrategy, LocationStrategy} from "@angular/common";


import {WeUiModule} from 'ngx-weui';
import { PickerModule } from 'ngx-weui/picker';


import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {MyNavbarComponent} from './myNavbar/myNavbar.component';
import {MyPickerComponent} from './myPicker/myPicker.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    MyNavbarComponent,
    MyPickerComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    FlexLayoutModule,

    WeUiModule.forRoot(),
    //WeUiModule,
    //PickerModule,

    AppRoutingModule
  ],
  providers: [
    {provide: LocationStrategy, useClass: HashLocationStrategy}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
