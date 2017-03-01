package org.revo.Service;

import org.revo.Domain.Song;

import java.util.List;

/**
 * Created by ashraf on 18/01/17.
 */
public interface SongService {
    List<Song> findAll();

    List<Song> findAllTo(Long id);

    Song save(Song song);

    List<Song> findViewsByUser_Id(Long id);

    List<Song> findLikesByUser_Id(Long id);
}