import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/map';

// get failed http(s) resource for current browser tab

// https://developer.mozilla.org/en-US/Add-ons/WebExtensions/API/webRequest
// https://github.com/kelseasy/web-ext-types/blob/master/global/index.d.ts
export interface FailedResourceService {
  getFailedResource(): Observable<string>
}
