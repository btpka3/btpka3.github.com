import {Component, EventEmitter, OnInit, Output} from "@angular/core";
import {Subscription} from 'rxjs/Subscription';
import {MyDemoService} from "./MyDemo.service";

@Component({
  selector: '[my-pub]',
  templateUrl: './MyPub.component.html',
  styleUrls: ['./MyPub.component.scss'],
})
export class MyPubComponent implements OnInit {

  subscription:Subscription;

  @Output() numChange: EventEmitter<number> = new EventEmitter();

  n: number = 0;

  constructor(private myDemoService:MyDemoService) {}

  ngOnInit(): void {

  }

  changeNum(): void {
    this.n++;
    this.numChange.emit(this.n);

    this.myDemoService.changeNum(this.n);
  }
}
