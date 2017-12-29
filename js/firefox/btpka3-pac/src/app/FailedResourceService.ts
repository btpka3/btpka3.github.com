import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/map';
import {BeTabsService} from "./BeTabs.service";

// get failed http(s) resource for current browser tab

// https://developer.mozilla.org/en-US/Add-ons/WebExtensions/API/webRequest
// https://github.com/kelseasy/web-ext-types/blob/master/global/index.d.ts
@Injectable()
export class FailedResourceService {

  constructor(private beTabsService: BeTabsService) {

  }

  getFailedResource(): Observable<{ domain: string, url: string }[]> {

    let a :any[];
    let o;

    this.beTabsService.actives.subscribe((activeInfo)=>{
      a = [];


    })
    return null;
  }
}
