import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from '@angular/router';

@Component({
  ///selector: '[aaa-component]',
  templateUrl: './x11.component.html',
  styleUrls: ['./x11.component.scss'],
  //host: {class: 'myClass'}
})
export class X11Component implements OnInit {


  q: string;

  // Observed query param
  oq: string;

  // matrix param
  mq1: string;
  mq2: string;

  id: string;

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit(): void {

    // 因为 q 是使用快照版本的，所以URL上参数变化后，不会变更
    this.q = this.route.snapshot.queryParams["q"];

    // 该版本使通过 Observable 监听变化的，能反应URL上的实时变化
    this.route.queryParams.subscribe((params: { q: string }) => {
      this.oq = params.q;
    });

    this.route.params.subscribe((params: { mq1: string, mq2: string }) => {
      this.mq1 = params.mq1;
      this.mq2 = params.mq2;
    });


    this.route.params.subscribe((params: { id: string }) => {
      this.id = params.id
    });
  }

}
