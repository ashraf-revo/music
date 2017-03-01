import { browser, element, by } from 'protractor';

export class MusicStaticPage {
  navigateTo() {
    return browser.get('/');
  }

  getParagraphText() {
    return element(by.css('m-root h1')).getText();
  }
}
