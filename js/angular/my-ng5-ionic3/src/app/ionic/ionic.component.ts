import {Component, OnInit, ViewChild} from "@angular/core";
import {ActionSheetController, AlertController, Content, MenuController} from 'ionic-angular';

@Component({
  ///selector: '[aaa-component]',
  templateUrl: './ionic.component.html',
  styleUrls: ['./ionic.component.scss']
})
export class IonicComponent implements OnInit {

  @ViewChild(Content) content1: Content;
  items = [];

  constructor(public alertCtrl: AlertController,
              public menuCtrl: MenuController,
              public actionSheetCtrl: ActionSheetController) {

    for (var i = 0; i < 30; i++) {
      this.items.push(this.items.length);
    }
  }

  ngOnInit(): void {
    //this.content1.resize();
  }

  doInfinite(): Promise<any> {
    console.log('Begin async operation');

    return new Promise((resolve) => {
      setTimeout(() => {
        for (var i = 0; i < 30; i++) {
          this.items.push(this.items.length);
        }

        console.log('Async operation has ended');
        resolve();
      }, 500);
    })
  }

  showAlert() {
    let alert = this.alertCtrl.create({
      title: 'New Friend!',
      subTitle: 'Your friend, Obi wan Kenobi, just accepted your friend request!',
      buttons: ['OK']
    });
    alert.present();
  }

  openMenu() {
    this.menuCtrl.open();
  }

  closeMenu() {
    this.menuCtrl.close();
  }

  toggleMenu() {
    this.menuCtrl.toggle();
  }

  presentActionSheet() {
    let actionSheet = this.actionSheetCtrl.create({
      title: 'Modify your album',
      buttons: [
        {
          text: 'Destructive',
          role: 'destructive',
          handler: () => {
            console.log('Destructive clicked');
          }
        }, {
          text: 'Archive',
          handler: () => {
            console.log('Archive clicked');
          }
        }, {
          text: 'Cancel',
          role: 'cancel',
          handler: () => {
            console.log('Cancel clicked');
          }
        }
      ]
    });
    actionSheet.present();
  }


}
