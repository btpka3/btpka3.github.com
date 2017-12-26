import {Component, OnDestroy, OnInit} from "@angular/core";
import {Subscription} from 'rxjs/Subscription';
import {MyDemoService} from "./MyDemo.service";

@Component({
  selector: '[my-sub]',
  templateUrl: './MySub.component.html',
  styleUrls: ['./MySub.component.scss'],
})
export class MySubComponent implements OnInit, OnDestroy {

  sub: Subscription;

  num: number = 0;

  constructor(private myDemoService: MyDemoService) {
    this.sub = this.myDemoService.observable.subscribe(item => this.num = item)
  }

  ngOnInit(): void {

  }

  ngOnDestroy() {
    // prevent memory leak when component is destroyed
    this.sub.unsubscribe();
  }
}
