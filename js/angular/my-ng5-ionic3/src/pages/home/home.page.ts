import {Component} from '@angular/core';
import {AlertController, NavController, ViewController} from 'ionic-angular';

import {UserPage} from "../user/user.page";

@Component({
  //selector: 'page-home',
  templateUrl: 'home.page.html'
})
export class HomePage {

  message = 'Welcome!';

  constructor(public alertCtrl: AlertController,
              public viewCtrl: ViewController,
              public navCtrl: NavController) {
  }

  showAlert() {
    let alert = this.alertCtrl.create({
      title: 'New Friend!',
      subTitle: 'Your friend, Obi wan Kenobi, just accepted your friend request!',
      buttons: ['OK']
    });
    alert.present();
  }

  goToOtherPage() {
    // push another page onto the history stack
    // causing the nav controller to animate the new page in
    //this.viewCtrl.dismiss();
    this.navCtrl.push(UserPage, {
      k1:"v1",
      k2:["v21","v22"]
    });
  }

}
