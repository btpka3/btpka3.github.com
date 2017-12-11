import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { IonicApp, IonicModule,NavController } from 'ionic-angular';

import { AppComponent } from './app.component';
import { HomePage } from '../pages/home.page';
@NgModule({
  declarations: [
    AppComponent,
    HomePage
  ],
  entryComponents: [ HomePage ],
  imports: [
    BrowserModule,
    IonicModule.forRoot(AppComponent)
  ],
  providers: [],
  // bootstrap: [AppComponent]
  bootstrap: [IonicApp]
})
export class AppModule { }
