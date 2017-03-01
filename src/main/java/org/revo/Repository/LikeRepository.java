package org.revo.Repository;

import org.revo.Domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Created by ashraf on 18/01/17.
 */
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUser_IdAndSong_Id(Long id1, Long id2);

    Optional<Like> removeById(Long id);

    @Query("select new org.revo.Domain.Like(l.id,l.user.id,l.song.id,l.createdDate) from Like l where l.user.id=?1")
    List<Like> readByUser_Id(Long id);

    @Query("select new org.revo.Domain.Like(l.id,l.user.id,l.song.id,l.createdDate) from Like l where l.song.id=?1")
    List<Like> readBySong_Id(Long id);
}
   