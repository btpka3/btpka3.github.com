import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

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
import {BpacCombineProxyDetailComponent} from "./bpac/proxy/combine/detail/BpacCombineProxyDetail.component";
import {CandidateListComponent} from "./bpac/proxy/combine/detail/CandidateList/CandidateList.component";
import {BpacDynamicProxyListComponent} from "./bpac/proxy/dynamic/BpacDynamicProxyList.component";
import {BpacDynamicProxyDetailComponent} from "./bpac/proxy/dynamic/detail/BpacDynamicProxyDetail.component";
import {BpacDynamicProxyTestComponent} from "./bpac/proxy/dynamic/detail/test/BpacDynamicProxyTest.component";
import {BpacSimpleProxyListComponent} from "./bpac/proxy/simple/BpacSimpleProxyList.component";
import {BpacSimpleProxyDetailComponent} from "./bpac/proxy/simple/detail/BpacSimpleProxyDetail.component";
import {BpacSideNavComponent} from "./bpac/sideNav/BpacSideNav.component";
import {BpacStatusComponent} from "./bpac/status/BpacStatus.component";



const routes: Routes = [
  {path: '', component: MyHomeComponent},

  {path: 'button/myButton', component: MyButtonComponent},
  {path: 'button/myChips', component: MyChipsComponent},

  {path: 'form/myAutocomplete', component: MyAutocompleteComponent},
  {path: 'form/myCheckbox', component: MyCheckboxComponent},
  {path: 'form/myInput', component: MyInputComponent},
  {path: 'form/mySelect', component: MySelectComponent},

  {path: 'layout/myList', component: MyListComponent},
  {path: 'layout/myExpansion', component: MyExpansionComponent},

  {path: 'navigation/myToolbar', component: MyToolbarComponent},
  {path: 'navigation/mySidenav', component: MySidenavComponent},

  {path: 'pops/mySnackBar', component: MySnackBarComponent},


  {path: 'bpac/about', component: BpacAboutComponent},
  {path: 'bpac/proxy/combine', component: BpacCombineProxyListComponent},
  {path: 'bpac/proxy/combine/:id', component: BpacCombineProxyDetailComponent},
  {path: 'bpac/proxy/combine/:id/candidates', component: CandidateListComponent},
  {path: 'bpac/proxy/dynamic', component: BpacDynamicProxyListComponent},
  {path: 'bpac/proxy/dynamic/:id', component: BpacDynamicProxyDetailComponent},
  {path: 'bpac/proxy/dynamic/:id/test', component: BpacDynamicProxyTestComponent},
  {path: 'bpac/proxy/simple', component: BpacSimpleProxyListComponent},
  {path: 'bpac/proxy/simple/:id', component: BpacSimpleProxyDetailComponent},
  {path: 'bpac/sideNav', component: BpacSideNavComponent},
  {path: 'bpac/status', component: BpacStatusComponent},

  {path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
