import {Component, OnInit} from "@angular/core";
import { DragulaService } from 'ng2-dragula';
import {FormControl} from '@angular/forms';
@Component({
  // selector: 'mat-test',
  templateUrl: './sort-select.component.html',
  styleUrls: ['./sort-select.component.css']
})
export class SortSelectComponent implements OnInit {
  toppings = new FormControl();
  toppingList = ['Extra cheese', 'Mushroom', 'Onion', 'Pepperoni', 'Sausage', 'Tomato'];

  constructor(private dragulaService: DragulaService) {
    dragulaService.setOptions('bag-mat-select', {
      //removeOnSpill: false,
      //direction: 'horizontal',
      isContainer(el) {
        return el.classList.contains('mat-select-content');
      }
    });
  }

  ngOnInit(): void {

  }

}
