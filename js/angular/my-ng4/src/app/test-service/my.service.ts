import {Injectable} from "@angular/core";


@Injectable()
export class MyService {

  constructor() {
  }

  add(a: number, b: number): number {
    return a + b;
  }

}
