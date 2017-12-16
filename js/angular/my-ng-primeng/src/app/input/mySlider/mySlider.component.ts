import {Component, OnInit} from "@angular/core";

@Component({
  templateUrl: './mySlider.component.html',
  styleUrls: ['./mySlider.component.scss']
})
export class MySliderComponent implements OnInit {
  val:number;
  rangeValues: number[]=[20,40];

  constructor() {
  }

  ngOnInit(): void {

  }

}
