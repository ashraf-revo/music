package org.revo.Service;

import org.revo.Domain.Like;
import org.revo.Domain.View;

import java.util.List;

/**
 * Created by ashraf on 15/02/17.
 */
public interface CachedUserService {
    List<View> views(Long id);

    List<View> add(View view);

    List<Like> likes(Long id);

    List<Like> add(Like like);

    List<Like> remove(Like like);

    List<Like> likesByCurrentUser();
}
