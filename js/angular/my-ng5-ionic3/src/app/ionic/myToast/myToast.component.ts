import {Component, OnInit} from "@angular/core";
import { ToastController } from 'ionic-angular';
@Component({
  ///selector: '[aaa-component]',
  templateUrl: './myToast.component.html',
  styleUrls: ['./myToast.component.scss']
})
export class MyToastComponent implements OnInit {


  constructor(private toastCtrl: ToastController) {
  }

  showToast(position: string) {
    const toast = this.toastCtrl.create({
      message: 'User was created successfully',
      position: position,
      duration: 3000
    });

    toast.onDidDismiss(this.dismissHandler);
    toast.present();
  }

  ngOnInit(): void {

  }

  private dismissHandler() {
    console.info('Toast onDidDismiss()');
  }
}
