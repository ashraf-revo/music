import { MusicStaticPage } from './app.po';

describe('music-static App', () => {
  let page: MusicStaticPage;

  beforeEach(() => {
    page = new MusicStaticPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('m works!');
  });
});
