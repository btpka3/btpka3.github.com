import {Component} from '@angular/core';
import {AlertController, NavParams,NavController} from 'ionic-angular';

@Component({
  // selector: 'page-user',
  templateUrl: 'user.page.html'
})
export class UserPage {

  message = 'Welcome!';
  k1: string;
  k2: string[];

  constructor(public alertCtrl: AlertController,
              public navParams: NavParams,
              public navCtrl: NavController) {

    this.k1 = this.navParams.get("k1");
    this.k2 = this.navParams.get("k2");
  }

  showAlert() {
    let alert = this.alertCtrl.create({
      title: 'New Friend!',
      subTitle: 'Your friend, Obi wan Kenobi, just accepted your friend request!',
      buttons: ['OK']
    });
    alert.present();
  }

  back() {
    // push another page onto the history stack
    // causing the nav controller to animate the new page in
    //this.viewCtrl.dismiss();
    this.navCtrl.pop();
  }

}
