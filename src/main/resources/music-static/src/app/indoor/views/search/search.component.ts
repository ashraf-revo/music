import {Component, OnInit, OnDestroy} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {SongService} from "../../../services/song.service";
import {Song} from "../../../domain/Song";
import {SearchCriteria} from "../../../domain/SearchCriteria";
import {Page} from "../../../domain/Page";
import {Subscription} from "rxjs";

@Component({
  selector: 'm-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit,OnDestroy {
  search: string = '';
  songs: Song[] = [];
  current: number = 0;
  size: number = 12;
  load: boolean = true;
  private subscription: Subscription;

  constructor(private _activatedRoute: ActivatedRoute, private _songService: SongService) {
  }

  ngOnInit() {
    this._activatedRoute.params.subscribe(v => {
      this.search = v['data'];
      this.current = 0;
      this.songs = [];
      this.loadMore()
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  loadMore() {
    this.subscription = this._songService.songsSearchAndGet(this.build()).subscribe(search => {
      if (search.searchCriteria.search.trim().split(" ").join("-") != this.search) {
        this.songs = [];
        this.current = 0;
      }
      for (let song of search.songs)        this.songs.push(song);
      if (search.songs.length < search.searchCriteria.page.size) this.load = false;
    })
  }

  build(): SearchCriteria {
    let pageRequest: Page = new Page();
    let searchCriteria: SearchCriteria = new SearchCriteria();
    searchCriteria.search = this.search.split("-").join(" ");
    pageRequest.number = this.current;
    pageRequest.size = this.size;
    searchCriteria.page = pageRequest;
    return searchCriteria;
  }
}
