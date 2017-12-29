/// <reference path="../../../../node_modules/web-ext-types/global/index.d.ts" />

import StorageArea  = browser.storage.StorageArea;
import StorageObject = browser.storage.StorageObject;

// import {browser} from "web-ext-types/global";

// https://developer.mozilla.org/en-US/Add-ons/WebExtensions/API/storage/StorageArea/set
// https://github.com/kelseasy/web-ext-types/blob/master/global/index.d.ts
export class MockStorageArea implements StorageArea {


  private o: string;

  get(keys?: string | string[] | null): Promise<StorageObject> {

    let oldObj = JSON.parse(this.o);

    let newObj = null;
    if (keys == null) {
      newObj = oldObj;
    } else if (typeof(keys) == 'string') {
      let key = keys;
      newObj[key] = oldObj[key];
      return Promise.resolve(oldObj);
    } else if (Array.isArray(keys)) {
      keys.forEach(key => newObj[key] = oldObj[key]);
    }

    return Promise.resolve(oldObj);
  }

  set(keys: StorageObject): Promise<void> {
    let oldObj = JSON.parse(this.o);
    oldObj = Object.assign(oldObj, keys);
    this.o = JSON.stringify(oldObj);
    return Promise.resolve();
  };

  remove(keys: string | string[]): Promise<void> {
    let oldObj = JSON.parse(this.o);

    if (typeof(keys) == 'string') {
      let key: string = keys;
      delete oldObj[keys]
    } else {
      keys.forEach(key => delete oldObj[key])
    }
    return Promise.resolve();
  };

  clear(): Promise<void> {
    this.o = "{}";
    return Promise.resolve();
  };

  constructor() {
  }

}
