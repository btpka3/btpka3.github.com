import {Component, OnInit} from "@angular/core";
import { ActivatedRoute } from '@angular/router';
@Component({
  ///selector: '[aaa-component]',
  templateUrl: './x22.component.html',
  styleUrls: ['./x22.component.scss'],
  //host: {class: 'myClass'}
})
export class X22Component implements OnInit {


  q:string;

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.q = this.route.snapshot.queryParams["q"];
  }
}
