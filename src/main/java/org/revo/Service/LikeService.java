package org.revo.Service;

import org.revo.Domain.Like;

import java.util.List;

/**
 * Created by ashraf on 18/01/17.
 */
public interface LikeService {
    Like like(Like like);

    void unLike(Like like);

    List<Like> readBySong_Id(Long id);

    List<Like> readByUser_Id(Long id);
}
   