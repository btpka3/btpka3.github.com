import {FailedResourceService} from "./FailedResourceService";
import {Observable} from "rxjs/Observable";

export class MockStorageArea implements FailedResourceService {

  getFailedResource(): Observable<string> {
    return undefined;
  }


}
