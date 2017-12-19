import {Component, HostBinding, OnInit} from "@angular/core";
import { ActivatedRoute } from '@angular/router';
@Component({
  ///selector: '[aaa-component]',
  templateUrl: './xxx.component.html',
  styleUrls: ['./xxx.component.scss'],
  //host: {class: 'myClass'}
})
export class XxxComponent implements OnInit {

  @HostBinding('attr.role') role = 'admin';



  q:string;

  // matrix param
  mq1: string;
  mq2: string;



  constructor(private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.q = this.route.snapshot.queryParams["q"];

    this.route.params.subscribe((params: { mq1: string, mq2: string }) => {
      this.mq1 = params.mq1;
      this.mq2 = params.mq2;
    });
  }

  go(h:string,f:string):void{

  }

}
