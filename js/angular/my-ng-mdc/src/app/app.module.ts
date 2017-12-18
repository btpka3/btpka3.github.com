import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';

import { ServiceWorkerModule } from '@angular/service-worker';
import { AppComponent } from './app.component';

import { environment } from '../environments/environment';


import { MyHomeComponent } from './myHome/myHome.component';

import { MyIconToggleComponent } from './button/myIconToggle/myIconToggle.component';
import { MyFabComponent } from './button/myFab/myFab.component';


import { MyDialogComponent } from './dialog/myDialog/myDialog.component';

import { MyButtonComponent } from './input/myButton/myButton.component';

@NgModule({
  declarations: [
    AppComponent,
    MyHomeComponent,

    MyFabComponent,
    MyIconToggleComponent,

    MyDialogComponent,

    MyButtonComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ServiceWorkerModule.register('/ngsw-worker.js', { enabled: environment.production })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
