
// a modified `node_modules/onsenui/core-src/setup.js`

import 'onsenui/core-src/ons/platform'; // This file must be loaded before Custom Elements polyfills.

//import 'onsenui/core-src/polyfills';
import 'onsenui/core-src/vendor';

import ons from 'onsenui/core-src/ons/ons';

import TemplateElement from 'onsenui/core-src/elements/ons-template';
import IfElement from 'onsenui/core-src/elements/ons-if';
import ActionSheetElement from 'onsenui/core-src/elements/ons-action-sheet';
import ActionSheetButtonElement from 'onsenui/core-src/elements/ons-action-sheet-button';
import AlertDialogElement from 'onsenui/core-src/elements/ons-alert-dialog';
import AlertDialogButtonElement from 'onsenui/core-src/elements/ons-alert-dialog-button';
import BackButtonElement from 'onsenui/core-src/elements/ons-back-button';
import BottomToolbarElement from 'onsenui/core-src/elements/ons-bottom-toolbar';
import ButtonElement from 'onsenui/core-src/elements/ons-button';
import CardElement from 'onsenui/core-src/elements/ons-card';
import CarouselItemElement from 'onsenui/core-src/elements/ons-carousel-item';
import CarouselElement from 'onsenui/core-src/elements/ons-carousel';
import ColElement from 'onsenui/core-src/elements/ons-col';
import DialogElement from 'onsenui/core-src/elements/ons-dialog';
import FabElement from 'onsenui/core-src/elements/ons-fab';
import GestureDetectorElement from 'onsenui/core-src/elements/ons-gesture-detector';
import IconElement from 'onsenui/core-src/elements/ons-icon';
import LazyRepeatElement from 'onsenui/core-src/elements/ons-lazy-repeat';
import ListHeaderElement from 'onsenui/core-src/elements/ons-list-header';
import ListTitleElement from 'onsenui/core-src/elements/ons-list-title';
import ListItemElement from 'onsenui/core-src/elements/ons-list-item';
import ListElement from 'onsenui/core-src/elements/ons-list';
import InputElement from 'onsenui/core-src/elements/ons-input';
import CheckboxElement from 'onsenui/core-src/elements/ons-checkbox';
import RadioElement from 'onsenui/core-src/elements/ons-radio';
import SearchInputElement from 'onsenui/core-src/elements/ons-search-input';
import ModalElement from 'onsenui/core-src/elements/ons-modal';
import NavigatorElement from 'onsenui/core-src/elements/ons-navigator';
import PageElement from 'onsenui/core-src/elements/ons-page';
import PopoverElement from 'onsenui/core-src/elements/ons-popover';
import ProgressBarElement from 'onsenui/core-src/elements/ons-progress-bar';
import ProgressCircularElement from 'onsenui/core-src/elements/ons-progress-circular';
import PullHookElement from 'onsenui/core-src/elements/ons-pull-hook';
import RangeElement from 'onsenui/core-src/elements/ons-range';
import RippleElement from 'onsenui/core-src/elements/ons-ripple';
import RowElement from 'onsenui/core-src/elements/ons-row';
import SegmentElement from 'onsenui/core-src/elements/ons-segment';
import SelectElement from 'onsenui/core-src/elements/ons-select';
import SpeedDialItemElement from 'onsenui/core-src/elements/ons-speed-dial-item';
import SpeedDialElement from 'onsenui/core-src/elements/ons-speed-dial';
import SplitterContentElement from 'onsenui/core-src/elements/ons-splitter-content';
import SplitterMaskElement from 'onsenui/core-src/elements/ons-splitter-mask';
import SplitterSideElement from 'onsenui/core-src/elements/ons-splitter-side';
import SplitterElement from 'onsenui/core-src/elements/ons-splitter';
import SwitchElement from 'onsenui/core-src/elements/ons-switch';
import TabElement from 'onsenui/core-src/elements/ons-tab';
import TabbarElement from 'onsenui/core-src/elements/ons-tabbar';
import ToastElement from 'onsenui/core-src/elements/ons-toast';
import ToolbarButtonElement from 'onsenui/core-src/elements/ons-toolbar-button';
import ToolbarElement from 'onsenui/core-src/elements/ons-toolbar';

if (window.ons) {
  ons._util.warn('Onsen UI is loaded more than once.');
}

ons.TemplateElement = TemplateElement;
ons.IfElement = IfElement;
ons.ActionSheetElement = ActionSheetElement;
ons.ActionSheetButtonElement = ActionSheetButtonElement;
ons.AlertDialogElement = AlertDialogElement;
ons.AlertDialogButtonElement = AlertDialogButtonElement;
ons.BackButtonElement = BackButtonElement;
ons.BottomToolbarElement = BottomToolbarElement;
ons.ButtonElement = ButtonElement;
ons.CardElement = CardElement;
ons.CarouselItemElement = CarouselItemElement;
ons.CarouselElement = CarouselElement;
ons.ColElement = ColElement;
ons.DialogElement = DialogElement;
ons.FabElement = FabElement;
ons.GestureDetectorElement = GestureDetectorElement;
ons.IconElement = IconElement;
ons.LazyRepeatElement = LazyRepeatElement;
ons.ListHeaderElement = ListHeaderElement;
ons.ListTitleElement = ListTitleElement;
ons.ListItemElement = ListItemElement;
ons.ListElement = ListElement;
ons.InputElement = InputElement;
ons.CheckboxElement = CheckboxElement;
ons.RadioElement = RadioElement;
ons.SearchInputElement = SearchInputElement;
ons.ModalElement = ModalElement;
ons.NavigatorElement = NavigatorElement;
ons.PageElement = PageElement;
ons.PopoverElement = PopoverElement;
ons.ProgressBarElement = ProgressBarElement;
ons.ProgressCircularElement = ProgressCircularElement;
ons.PullHookElement = PullHookElement;
ons.RangeElement = RangeElement;
ons.RippleElement = RippleElement;
ons.RowElement = RowElement;
ons.SegmentElement = SegmentElement;
ons.SelectElement = SelectElement;
ons.SpeedDialItemElement = SpeedDialItemElement;
ons.SpeedDialElement = SpeedDialElement;
ons.SplitterContentElement = SplitterContentElement;
ons.SplitterMaskElement = SplitterMaskElement;
ons.SplitterSideElement = SplitterSideElement;
ons.SplitterElement = SplitterElement;
ons.SwitchElement = SwitchElement;
ons.TabElement = TabElement;
ons.TabbarElement = TabbarElement;
ons.ToastElement = ToastElement;
ons.ToolbarButtonElement = ToolbarButtonElement;
ons.ToolbarElement = ToolbarElement;
ons.CardElement = CardElement;

// fastclick
window.addEventListener('load', () => {
  ons.fastClick = FastClick.attach(document.body);

  const supportTouchAction = 'touch-action' in document.body.style;

  ons.platform._runOnActualPlatform(() => {
    if (ons.platform.isAndroid()) {
      // In Android4.4+, correct viewport settings can remove click delay.
      // So disable FastClick on Android.
      ons.fastClick.destroy();
    } else if (ons.platform.isIOS()) {
      if (supportTouchAction && (ons.platform.isIOSSafari() || ons.platform.isWKWebView())) {
        // If 'touch-action' supported in iOS Safari or WKWebView, disable FastClick.
        ons.fastClick.destroy();
      } else {
        // Do nothing. 'touch-action: manipulation' has no effect on UIWebView.
      }
    }
  });
}, false);

ons.ready(function() {
  // ons._defaultDeviceBackButtonHandler
  ons._deviceBackButtonDispatcher.enable();
  ons._defaultDeviceBackButtonHandler = ons._deviceBackButtonDispatcher.createHandler(window.document.body, () => {
    if (Object.hasOwnProperty.call(navigator, 'app')) {
      navigator.app.exitApp();
    } else {
      console.warn('Could not close the app. Is \'cordova.js\' included?\nError: \'window.navigator.app\' is undefined.');
    }
  });
  document.body._gestureDetector = new ons.GestureDetector(document.body);

  // Simulate Device Back Button on ESC press
  if (!ons.platform.isWebView()) {
    document.body.addEventListener('keydown', function(event) {
      if (event.keyCode === 27) {
        ons._deviceBackButtonDispatcher.fireDeviceBackButtonEvent();
      }
    })
  }

  // setup loading placeholder
  ons._setupLoadingPlaceHolders();
});

// viewport.js
Viewport.setup();

export default ons;
