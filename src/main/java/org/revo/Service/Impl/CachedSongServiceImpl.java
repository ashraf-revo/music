package org.revo.Service.Impl;

import org.revo.Domain.Like;
import org.revo.Domain.View;
import org.revo.Service.CachedSongService;
import org.revo.Service.LikeService;
import org.revo.Service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;
import static org.revo.Util.Util.copyLike;
import static org.revo.Util.Util.copyView;

/**
 * Created by ashraf on 17/02/17.
 */
@Service
public class CachedSongServiceImpl implements CachedSongService {
    @Autowired
    private CachedSongService cachedSongService;
    @Autowired
    private ViewService  viewService;
    @Autowired
    private LikeService LikeService;

    @Cacheable(value = "songViews", key = "#id")
    @Override
    public List<View> views(Long id) {
        return viewService.readBySong_Id(id);
    }

    @CachePut(value = "songViews", key = "#view.user.id")
    @Override
    public List<View> add(View view) {
        List<View> views = cachedSongService.views(view.getUser().getId());
        views.add(copyView(view));
        return views;
    }

    @Cacheable(value = "songLikes", key = "#id")
    public List<Like> likes(Long id) {
        return LikeService.readBySong_Id(id);
    }

    @CachePut(value = "songLikes", key = "#like.user.id")
    public List<Like> add(Like like) {
        List<Like> likes = cachedSongService.likes(like.getUser().getId());
        likes.add(copyLike(like));
        return likes;
    }


    @CachePut(value = "songLikes", key = "#like.user.id")
    public List<Like> remove(Like like) {
        return cachedSongService.likes(like.getUser().getId()).stream().filter(it -> !Objects.equals(it.getId(), like.getId())).collect(toList());
    }
}
