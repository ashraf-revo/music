package org.revo.Service.Impl;

import org.revo.Domain.Song;
import org.revo.Repository.SongRepository;
import org.revo.Service.CloudinaryService;
import org.revo.Service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by ashraf on 17/02/17.
 */
@Service
public class SongServiceImpl implements SongService {
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    @Transactional(readOnly = true)
    @Override
    public List<Song> findAll() {
        return songRepository.findBy();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Song> findAllTo(Long id) {
        return songRepository.findByUser_Id(id);
    }

    @Override
    public Song save(Song song) {
        Assert.isNull(song.getId());
        if (song.getImage() != null && !song.getImage().isEmpty()) {
            song.setImageUrl(cloudinaryService.saveImage(song.getImage()));
        }
        if (song.getFile() != null && !song.getFile().isEmpty()) {
            song.setFileUrl(cloudinaryService.saveFile(song));
        }
        song = songRepository.save(song);
        return song;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Song> findViewsByUser_Id(Long id) {
        return songRepository.findViewsByUser_Id(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Song> findLikesByUser_Id(Long id) {
        return songRepository.findLikesByUser_Id(id);
    }
}