import {Component, OnInit} from "@angular/core";
import {MyService} from "./test-service/my.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'app works!';
  myServiceResult;
  myPipeSrc="myPipeSrc";

  constructor(private myService: MyService) {
  }

  ngOnInit(): void {
    this.myServiceResult = "aaa : " + this.myService.add(1, 2)
  }
}
