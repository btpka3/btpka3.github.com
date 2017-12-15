import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from '@angular/router';

@Component({
  // selector: '[bbb-component]',
  templateUrl: './ccc.component.html',
  styleUrls: ['./ccc.component.css']
})
export class CccComponent implements OnInit {
  v = 'c1-c2';
  paramN: number;
  n: number = 0;

  constructor(private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.paramN = this.activatedRoute.snapshot.queryParams["n"];
  }

  changeNum(): void {
    this.n++;
  }

}
