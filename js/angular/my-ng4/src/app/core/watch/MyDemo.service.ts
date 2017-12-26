import {Injectable} from "@angular/core";
import {BehaviorSubject} from 'rxjs/BehaviorSubject';


@Injectable()
export class MyDemoService   {

  // Observable navItem source
  private sub = new BehaviorSubject<number>(0);

  // Observable navItem stream
  observable = this.sub.asObservable();

  // service command
  changeNum(number) {
    this.sub.next(number);
  }

}
