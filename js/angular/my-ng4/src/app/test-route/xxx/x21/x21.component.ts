import {Component, OnInit} from "@angular/core";
import { ActivatedRoute } from '@angular/router';
@Component({
  ///selector: '[aaa-component]',
  templateUrl: './x21.component.html',
  styleUrls: ['./x21.component.scss'],
  //host: {class: 'myClass'}
})
export class X21Component implements OnInit {


  q:string;

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.q = this.route.snapshot.queryParams["q"];
  }

}
