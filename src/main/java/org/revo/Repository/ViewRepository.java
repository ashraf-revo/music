package org.revo.Repository;

import org.revo.Domain.View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by ashraf on 22/01/17.
 */
public interface ViewRepository extends JpaRepository<View, Long> {
    @Query("select new View(v.id,v.user.id,v.song.id,v.createdDate) from View v where v.user.id=?1")
    List<View> readByUser_Id(Long id);

    @Query("select new View(v.id,v.user.id,v.song.id,v.createdDate) from View v where v.song.id=?1")
    List<View> readBySong_Id(Long id);
}