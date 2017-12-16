import {Component, OnInit} from "@angular/core";
declare var alert: Function;

@Component({
  ///selector: '[aaa-component]',
  templateUrl: './ons.component.html',
  styleUrls: ['./ons.component.css']
})
export class OnsComponent implements OnInit {
  value: string = '10';
  target: boolean = true;

  constructor() {
  }

  ngOnInit(): void {

  }

  onClick():void {
    //ons.notification.alert('Clicked!');
  }

  rangeValueChange():void{
    console.log(arguments);
  }

}
