import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';

import {FormsModule, ReactiveFormsModule} from "@angular/forms";


import {DragulaModule} from 'ng2-dragula';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HashLocationStrategy, LocationStrategy} from "@angular/common";

import {FlexLayoutModule} from '@angular/flex-layout';


import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatRadioModule} from '@angular/material/radio';
import {MatSelectModule} from '@angular/material/select';
import {MatSliderModule} from '@angular/material/slider';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';


import {MatMenuModule} from '@angular/material/menu';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatToolbarModule} from '@angular/material/toolbar';


import {MatListModule} from '@angular/material/list';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatCardModule} from '@angular/material/card';
import {MatStepperModule} from '@angular/material/stepper';
import {MatTabsModule} from '@angular/material/tabs';
import {MatExpansionModule} from '@angular/material/expansion';


import {MatButtonModule} from '@angular/material/button';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import {MatChipsModule} from '@angular/material/chips';
import {MatIconModule} from '@angular/material/icon';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatProgressBarModule} from '@angular/material/progress-bar';


import {MatDialogModule} from '@angular/material/dialog';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatSnackBarModule} from '@angular/material/snack-bar';

import {MatTableModule} from '@angular/material/table';
import {MatSortModule} from '@angular/material/sort';
import {MatPaginatorModule} from '@angular/material/paginator';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';


import {BpacAboutComponent} from "./bpac/about/BpacAbout.component";
import {BpacCombineProxyListComponent} from "./bpac/proxy/combine/BpacCombineProxyList.component";
import {BpacCombineProxyDetailComponent} from "./bpac/proxy/combine/detail/BpacCombineProxyDetail.component";
import {CandidateListComponent} from "./bpac/proxy/combine/detail/CandidateList/CandidateList.component";
import {BpacDynamicProxyListComponent} from "./bpac/proxy/dynamic/BpacDynamicProxyList.component";
import {BpacDynamicProxyDetailComponent} from "./bpac/proxy/dynamic/detail/BpacDynamicProxyDetail.component";
import {BpacDynamicProxyTestComponent} from "./bpac/proxy/dynamic/detail/test/BpacDynamicProxyTest.component";
import {BpacSimpleProxyListComponent} from "./bpac/proxy/simple/BpacSimpleProxyList.component";
import {BpacSimpleProxyDetailComponent} from "./bpac/proxy/simple/detail/BpacSimpleProxyDetail.component";
import {BpacSideNavComponent} from "./bpac/sideNav/BpacSideNav.component";
import {BpacStatusComponent} from "./bpac/status/BpacStatus.component";

import {environment} from '../environments/environment';

@NgModule({
  declarations: [
    AppComponent,

    BpacAboutComponent,
    BpacCombineProxyListComponent,
    BpacCombineProxyDetailComponent,
    CandidateListComponent,
    BpacDynamicProxyListComponent,
    BpacDynamicProxyDetailComponent,
    BpacDynamicProxyTestComponent,
    BpacSimpleProxyListComponent,
    BpacSimpleProxyDetailComponent,
    BpacSideNavComponent,
    BpacStatusComponent,
  ],
  imports: [
    DragulaModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    BrowserModule,

    FlexLayoutModule,


    MatAutocompleteModule,
    MatCheckboxModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatInputModule,
    MatRadioModule,
    MatSelectModule,
    MatSliderModule,
    MatSlideToggleModule,


    MatMenuModule,
    MatSidenavModule,
    MatToolbarModule,

    MatListModule,
    MatGridListModule,
    MatCardModule,
    MatStepperModule,
    MatTabsModule,
    MatExpansionModule,

    MatButtonModule,
    MatButtonToggleModule,
    MatChipsModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatProgressBarModule,

    MatDialogModule,
    MatTooltipModule,
    MatSnackBarModule,

    MatTableModule,
    MatSortModule,
    MatPaginatorModule,

    AppRoutingModule
  ],
  providers: [
    {provide: LocationStrategy, useClass: HashLocationStrategy},
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

  constructor() {
    console.log(environment)
  }
}
