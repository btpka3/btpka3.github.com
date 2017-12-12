import {Component, OnInit} from "@angular/core";
import { DragulaService } from 'ng2-dragula';

@Component({
  // selector: 'mat-test',
  templateUrl: './sort-chip.component.html',
  styleUrls: ['./sort-chip.component.css']
})
export class SortChipComponent implements OnInit {



  constructor(private dragulaService: DragulaService) {
    dragulaService.setOptions('bag-mat-chip', {
      //removeOnSpill: false,
      //direction: 'horizontal',
      isContainer(el) {
        return el.classList.contains('mat-chip-list-wrapper');
      }
    });
  }

  ngOnInit(): void {

  }

}
