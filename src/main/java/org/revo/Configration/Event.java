package org.revo.Configration;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.revo.Domain.Like;
import org.revo.Domain.Song;
import org.revo.Domain.User;
import org.revo.Domain.View;
import org.revo.Service.CachedSongService;
import org.revo.Service.CachedUserService;
import org.revo.Service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ashraf on 02/03/17.
 */
@Component
@Aspect
@Slf4j
public class Event {
    @Autowired
    private CachedSongService cachedSongService;
    @Autowired
    private CachedUserService cachedUserService;
    @Autowired
    private LikeService likeService;

    @AfterReturning(value = "execution(public !void org.revo.Repository.UserRepository.save(..))", returning = "obj")
    public void saveUser(User obj) {
        log.info("saveUser             " + obj.toString());
    }

    @AfterReturning(value = "execution(public !void org.revo.Repository.SongRepository.save(..))", returning = "obj")
    public void saveSong(Song obj) {
        log.info("saveSong             " + obj.toString());
    }

    @AfterReturning(value = "execution(public !void org.revo.Repository.LikeRepository.save(..))", returning = "obj")
    public void saveLike(Like obj) {
        cachedSongService.add(obj);
        cachedUserService.add(obj);
    }

    @Before(value = "execution(public void org.revo.Repository.LikeRepository.delete(*)) and args(obj)", argNames = "obj")
    public void deleteLike(Long obj) {
        Like like = likeService.findOne(obj);
        cachedSongService.remove(like);
        cachedUserService.remove(like);
    }

    @AfterReturning(value = "execution(public !void org.revo.Repository.ViewRepository.save(..))", returning = "obj")
    public void saveView(View obj) {
        cachedSongService.add(obj);
        cachedUserService.add(obj);
    }
}
