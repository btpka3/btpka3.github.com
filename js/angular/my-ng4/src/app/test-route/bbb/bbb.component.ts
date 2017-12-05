import {Component, OnInit} from "@angular/core";
import {Router} from '@angular/router';

@Component({
  // selector: '[bbb-component]',
  templateUrl: './bbb.component.html',
  styleUrls: ['./bbb.component.css']
})
export class BbbComponent implements OnInit {
  v = 'b1-b2';
  n: number = 0;

  constructor(private router: Router) {
  }

  ngOnInit(): void {

  }

  changeNum(): void {
    this.n++;
  }

  back(): void {
    history.back()
  }

  goCcc(): void {
    //this.router.navigateByUrl('/ccc',{ skipLocationChange: true });
    //this.router.navigateByUrl('/ccc?n=987', {skipLocationChange: true});
    this.router.navigate(['/ccc'], { queryParams: { n: 999 },  queryParamsHandling: "merge" });
  }
}
