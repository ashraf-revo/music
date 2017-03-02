import {Component, OnInit, OnDestroy} from "@angular/core";
import {ActivatedRoute, Params} from "@angular/router";
import {Subscription} from "rxjs";
import {SongService} from "../../../services/song.service";
import {Song} from "../../../domain/Song";
import {SongInfo} from "../../../domain/SongInfo";

@Component({
  selector: 'm-song-info',
  templateUrl: './song-info.component.html',
  styleUrls: ['./song-info.component.css']
})
export class SongInfoComponent implements OnInit,OnDestroy {
  song: Song;
  songInfo: SongInfo;
  subscription1: Subscription;
  subscription2: Subscription;

  constructor(private _activatedRoute: ActivatedRoute, private _songService: SongService) {
  }

  ngOnInit() {
    let routerFilter = this._activatedRoute.params.map((it: Params) => <string>it['id']).filter((it: string) => it.trim() != "");
    this.subscription1 = routerFilter.flatMap(it => this._songService.song(it)).subscribe(it => this.song = it);
    this.subscription2 = routerFilter.flatMap(it => this._songService.songInfo(it)).subscribe(it => this.songInfo = it);
  }

  ngOnDestroy(): void {
    this.subscription1.unsubscribe();
    this.subscription2.unsubscribe();
  }
}
