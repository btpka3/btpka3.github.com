import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {ServiceWorkerModule} from '@angular/service-worker';
import {HashLocationStrategy, LocationStrategy} from "@angular/common";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FlexLayoutModule} from '@angular/flex-layout';


import {
  AccordionModule,
  ButtonModule,
  CodeHighlighterModule,
  GrowlModule,
  MenuModule,
  SplitButtonModule,
  TabViewModule,
  SliderModule,
  DropdownModule,
} from 'primeng/primeng';

import {environment} from '../environments/environment';
import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {MyHomeComponent} from "./myHome/myHome.component";

import {MyMenuComponent} from "./menu/myMenu/myMenu.component";

import {MyDropdownComponent} from "./input/myDropdown/myDropdown.component"
import {MySliderComponent} from "./input/mySlider/mySlider.component";


import {MyButtonComponent} from "./button/myButton/myButton.component";
import {MySplitButtonComponent} from "./button/mySplitButton/mySplitButton.component";


@NgModule({
  declarations: [

    MyDropdownComponent,
    MySliderComponent,

    MyButtonComponent,
    MySplitButtonComponent,

    MyMenuComponent,

    MyHomeComponent,
    AppComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    FlexLayoutModule,
    FormsModule,


    AccordionModule,
    ButtonModule,
    SplitButtonModule,
    GrowlModule,
    TabViewModule,
    CodeHighlighterModule,
    MenuModule,
    SliderModule,
    DropdownModule,

    ServiceWorkerModule.register('/ngsw-worker.js', {enabled: environment.production})
  ],
  providers: [
    {provide: LocationStrategy, useClass: HashLocationStrategy}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
