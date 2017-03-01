package org.revo.Controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.revo.Domain.*;
import org.revo.Service.*;
import org.revo.Util.ExtractText;
import org.revo.Util.Util;
import org.revo.Util.ViewDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ashraf on 18/01/17.
 */
@RestController
@RequestMapping(Role.Paths.SONG_PATH)
public class SongApi {
    @Autowired
    private SongService songService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private ViewService viewService;
    @Autowired
    private CachedUserService cachedUserService;
    @Autowired
    private CachedSongService cachedSongService;
    @Autowired
    private SearchService searchService;
    @Autowired
    private UserService userService;

    @GetMapping
    @JsonView(ViewDetails.CustomSong.class)
    public ResponseEntity<List<Song>> songs() {
        return ResponseEntity.ok(Util.buildLikes(songService.findAll(), userService.isAuth() ? cachedUserService.likesByCurrentUser() : new ArrayList<>()));
    }

    @GetMapping("{id}")
    @JsonView(ViewDetails.CustomSong.class)
    public ResponseEntity<Song> findSong(@PathVariable("id") Long id) {
        return ResponseEntity.ok(songService.findSong(id));
    }

    @GetMapping("info/{id}")
    @JsonView(ViewDetails.CustomSong.class)
    public ResponseEntity<Map<String, Integer>> findSongInfo(@PathVariable("id") Long id) {
        Map<String, Integer> map = new HashMap<>();
        map.put("likes", cachedSongService.likesCount(id));
        map.put("views", cachedSongService.viewsCount(id));
        return ResponseEntity.ok(map);
    }

    @PostMapping(value = "search")
    @JsonView(ViewDetails.CustomSongSearch.class)
    public ResponseEntity<Search> search(@RequestBody SearchCriteria searchCriteria, @RequestParam(value = "full", required = false, defaultValue = "false") boolean full) {
        String fields = "title";
        List<Song> body = full ? searchService.searchAndGet(searchCriteria, fields) : searchService.search(searchCriteria, fields);
        return ResponseEntity.ok(new Search(searchCriteria, Util.buildLikes(body, userService.isAuth() ? cachedUserService.likesByCurrentUser() : new ArrayList<>())));
    }

    @PostMapping
    @JsonView(ViewDetails.CustomSong.class)
    public ResponseEntity<Song> song(@ModelAttribute Song song) {
        return ResponseEntity.ok(songService.save(song));
    }

    @PostMapping("like")
    @JsonView(ViewDetails.CustomLike.class)
    public ResponseEntity<Like> like(@Validated @RequestBody Like like) {
        return ResponseEntity.ok(likeService.like(like));
    }

    @PostMapping("unlike")
    public ResponseEntity<Void> unlike(@RequestBody Like like) {
        likeService.unLike(like);
        return ResponseEntity.ok().build();
    }

    @PostMapping("view")
    @JsonView(ViewDetails.CustomView.class)
    public ResponseEntity view(@RequestBody @Validated View view) {
        viewService.view(view);
        return ResponseEntity.ok().build();
    }

    @GetMapping("fuck/{artist}")
    public void fuck(@PathVariable("artist") String artist) {
        ExtractText.extract(artist)
                .map(it -> {
                    Song song = new Song();
                    song.setTitle(it.getTitle());
                    song.setDescription(it.getWords());
                    return song;
                })
                .subscribe(it -> songService.save(it));
    }
}