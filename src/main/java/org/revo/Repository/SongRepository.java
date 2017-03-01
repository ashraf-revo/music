package org.revo.Repository;

import org.revo.Domain.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by ashraf on 18/01/17.
 */
public interface SongRepository extends JpaRepository<Song, Long> {

    List<Song> findByUser_Id(Long id);

    @Query("select new Song(s.id,s.title,s.imageUrl,s.fileUrl,s.description,s.user.id, s.user.name, s.user.imageUrl, s.user.phone, s.user.info, s.user.email, s.user.createdDate,s.createdDate) from Song s")
    List<Song> findBy();

    @Query("select new Song(s.id,s.title,s.imageUrl,s.fileUrl,s.description,s.user.id, s.user.name, s.user.imageUrl, s.user.phone, s.user.info, s.user.email, s.user.createdDate,s.createdDate) from Song s  join s.likes l where l.user.id=?1")
    List<Song> findLikesByUser_Id(Long id);

    @Query("select new Song(s.id,s.title,s.imageUrl,s.fileUrl,s.description,s.user.id, s.user.name, s.user.imageUrl, s.user.phone, s.user.info, s.user.email, s.user.createdDate,s.createdDate) from Song s  join s.views v where v.user.id=?1")
    List<Song> findViewsByUser_Id(Long id);
}