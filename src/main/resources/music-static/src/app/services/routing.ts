import {Routes} from "@angular/router";
import {SigninComponent} from "../outdoor/signin/signin.component";
import {SignupComponent} from "../outdoor/signup/signup.component";
import {BaseComponent} from "../indoor/views/base/base.component";
import {HomeComponent} from "../indoor/views/home/home.component";
import {ProfileComponent} from "../indoor/views/profile/profile.component";
import {UploadComponent} from "../indoor/views/upload/upload.component";
import {SearchComponent} from "../indoor/views/search/search.component";
import {SettingsComponent} from "../indoor/views/settings/settings.component";
import {ActiveComponent} from "../outdoor/active/active.component";
import {Error404Component} from "../outdoor/error404/error404.component";
import {SecureGuardService} from "./SecureGuardService";
import {InSecureGuardService} from "./InSecureGuardService";
import {SongInfoComponent} from "../indoor/views/song-info/song-info.component";
export const routes: Routes = [
  {path: '', component: SigninComponent, canActivate: [InSecureGuardService]},
  {path: 'signin', component: SigninComponent, canActivate: [InSecureGuardService]},
  {path: 'signup', component: SignupComponent, canActivate: [InSecureGuardService]},
  {path: 'active/:id', component: ActiveComponent, canActivate: [InSecureGuardService]},
  {path: '404', component: Error404Component},
  {
    path: '', component: BaseComponent, children: [
    {path: 'home', component: HomeComponent, canActivate: [SecureGuardService]},
    {path: 'search/:data', component: SearchComponent, canActivate: [SecureGuardService]},
    {path: 'song/:id', component: SongInfoComponent, canActivate: [SecureGuardService]},
    {path: 'profile/:id', component: ProfileComponent, canActivate: [SecureGuardService]},
    {path: 'settings', component: SettingsComponent, canActivate: [SecureGuardService]},
    {path: 'upload', component: UploadComponent, canActivate: [SecureGuardService]},
  ]
  },
  {path: "**", redirectTo: "404"}
];
