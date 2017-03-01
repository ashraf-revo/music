import {Component, OnInit, OnDestroy} from "@angular/core";
import {PlayerService} from "../../../services/player.service";
import {UploadService} from "../../../services/upload.service";
import {SongService} from "../../../services/song.service";
import {Router} from "@angular/router";
import {Subscription} from "rxjs";
@Component({
  selector: 'm-base',
  templateUrl: './base.component.html',
  styleUrls: ['./base.component.css'], providers: [PlayerService, UploadService, SongService]
})
export class BaseComponent implements OnInit,OnDestroy {
  search: string = "";
  subscription: Subscription = null;

  constructor(private _router: Router) {
  }

  ngOnInit() {
    this.subscription = this._router.events.subscribe(it => {
      let values: string[] = it.url.split("/");
      if (values.length > 0) {
        if (values[1] == 'search') {
          if (values.length > 2 && values[2].trim() != "") {
            this.search = values[2].split("-").join(" ");
          } else this.search = ""
        } else this.search = ""
      } else this.search = ""
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

}
