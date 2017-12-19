import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ServiceWorkerModule} from '@angular/service-worker';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AppComponent} from './app.component';
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


import {environment} from '../environments/environment';


import {MyHomeComponent} from "./myHome/myHome.component";


import {MyButtonComponent} from "./button/myButton/myButton.component";
import {MyChipsComponent} from "./button/myChips/myChips.component";

import {MyAutocompleteComponent} from "./form/myAutocomplete/myAutocomplete.component";
import {MyCheckboxComponent} from "./form/myCheckbox/myCheckbox.component";
import {MyInputComponent} from "./form/myInput/myInput.component";
import {MySelectComponent} from "./form/mySelect/mySelect.component";

import {MyListComponent} from "./layout/myList/myList.component";
import {MyExpansionComponent} from "./layout/myExpansion/myExpansion.component";

import {MyToolbarComponent} from "./navigation/myToolbar/myToolbar.component";
import {MySidenavComponent} from "./navigation/mySidenav/mySidenav.component";

import {MySnackBarComponent} from "./pops/mySnackBar/mySnackBar.component";


import {BpacAboutComponent} from "./bpac/about/BpacAbout.component";
import {BpacCombineProxyListComponent} from "./bpac/proxy/combine/BpacCombineProxyList.component";
import {BpacDynamicProxyDetailComponent} from "./bpac/proxy/dynamic/BpacDynamicProxyDetail.component";
import {BpacDynamicProxyTestComponent} from "./bpac/proxy/dynamic/test/BpacDynamicProxyTest.component";
import {BpacSimpleProxyListComponent} from "./bpac/proxy/simple/BpacSimpleProxyList.component";
import {BpacSimpleProxyDetailComponent} from "./bpac/proxy/simple/detail/BpacSimpleProxyDetail.component";
import {BpacSideNavComponent} from "./bpac/sideNav/BpacSideNav.component";
import {BpacStatusComponent} from "./bpac/status/BpacStatus.component";

@NgModule({
  declarations: [
    AppComponent,
    MyHomeComponent,

    MyButtonComponent,
    MyChipsComponent,

    MyAutocompleteComponent,
    MyCheckboxComponent,
    MyInputComponent,
    MySelectComponent,

    MyListComponent,
    MyExpansionComponent,

    MyToolbarComponent,
    MySidenavComponent,

    MySnackBarComponent,

    BpacAboutComponent,
    BpacCombineProxyListComponent,
    BpacDynamicProxyDetailComponent,
    BpacDynamicProxyTestComponent,
    BpacSimpleProxyListComponent,
    BpacSimpleProxyDetailComponent,
    BpacSideNavComponent,
    BpacStatusComponent,


  ],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    BrowserModule,
    BrowserAnimationsModule,

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

    AppRoutingModule,
    ServiceWorkerModule.register('/ngsw-worker.js', {enabled: environment.production})
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
