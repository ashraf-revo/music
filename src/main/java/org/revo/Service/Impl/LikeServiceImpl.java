package org.revo.Service.Impl;

import org.revo.Domain.Like;
import org.revo.Repository.LikeRepository;
import org.revo.Service.LikeService;
import org.revo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by ashraf on 17/02/17.
 */
@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private UserService userService;

    @Override
    public Like like(Like like) {
        like.setUser(userService.current());
        Assert.notNull(like.getUser().getId());
        Assert.notNull(like.getSong().getId());
        Assert.isNull(like.getId());
        return likeRepository.findByUser_IdAndSong_Id(like.getUser().getId(), like.getSong().getId()).orElseGet(() -> likeRepository.save(like));
    }

    @Transactional
    @Override
    public void unLike(Like like) {
        likeRepository.delete(like.getId());
    }

    @Override
    public List<Like> readBySong_Id(Long id) {
        return likeRepository.readBySong_Id(id);
    }

    @Override
    public List<Like> readByUser_Id(Long id) {
        return likeRepository.readByUser_Id(id);
    }

    @Override
    public Like findOne(Long id) {
        return likeRepository.findOne(id);
    }
}
