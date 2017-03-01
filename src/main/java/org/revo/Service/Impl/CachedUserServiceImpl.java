package org.revo.Service.Impl;

import org.revo.Domain.Like;
import org.revo.Domain.View;
import org.revo.Service.CachedUserService;
import org.revo.Service.LikeService;
import org.revo.Service.UserService;
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
public class CachedUserServiceImpl implements CachedUserService {
    @Autowired
    private CachedUserService cachedUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private ViewService viewService;
    @Autowired
    private LikeService likeService;

    @Cacheable(value = "userViews", key = "#id")
    @Override
    public List<View> views(Long id) {
        return viewService.readByUser_Id(id);
    }

    @CachePut(value = "userViews", key = "#view.user.id")
    @Override
    public List<View> add(View view) {
        List<View> views = cachedUserService.views(view.getUser().getId());
        views.add(copyView(view));
        return views;
    }

    @Cacheable(value = "userLikes", key = "#id")
    public List<Like> likes(Long id) {
        return likeService.readByUser_Id(id);
    }

    @CachePut(value = "userLikes", key = "#like.user.id")
    public List<Like> add(Like like) {
        List<Like> likes = cachedUserService.likes(like.getUser().getId());
        likes.add(copyLike(like));
        return likes;
    }


    @CachePut(value = "userLikes", key = "#like.user.id")
    public List<Like> remove(Like like) {
        return cachedUserService.likes(like.getUser().getId()).stream().filter(it -> !Objects.equals(it.getId(), like.getId())).collect(toList());
    }

    @Override
    public List<Like> likesByCurrentUser() {
        return cachedUserService.likes(userService.current().getId());
    }
}
