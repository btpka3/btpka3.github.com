import {Component, OnInit} from "@angular/core";
import {Location} from '@angular/common';
import {Router,ActivatedRoute} from '@angular/router';

import {ObjectID} from "bson";

@Component({
  ///selector: '[aaa-component]',
  templateUrl: './BpacSimpleProxyList.component.html',
  styleUrls: ['./BpacSimpleProxyList.component.scss']
})
export class BpacSimpleProxyListComponent implements OnInit {



  constructor(public location: Location,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {

  }


  add(): void {
    let oid = new ObjectID();
    console.log(oid, oid.toString(), oid.toHexString());
    this.router.navigate(['/bpac/proxy/simple', oid.toHexString()]);
  }

  goBack():void{
    this.location.back();
  }

}
