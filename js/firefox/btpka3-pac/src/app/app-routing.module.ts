import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';


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
  {path: '', component: BpacStatusComponent},


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

  {path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
