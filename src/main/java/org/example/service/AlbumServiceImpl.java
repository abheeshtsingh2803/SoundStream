package org.example.service;

import org.example.exceptions.ResourceNotFoundException;
import org.example.model.Album;
import org.example.model.Song;
import org.example.repository.AlbumRepository;
import org.example.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AlbumServiceImpl implements AlbumService{

    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository, SongRepository songRepository) {
        this.albumRepository = albumRepository;
        this.songRepository = songRepository;
    }

    @Override
    public Album getAlbumById(Long albumId) {
        return albumRepository.findById(albumId)
                .orElseThrow(() -> new ResourceNotFoundException("Album not found"));
    }

    @Override
    public Album getAlbumByName(String name) {
        return albumRepository.findByAlbumNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("Album not found"));
    }

    @Override
    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    @Override
    public Album createAlbum(String name, String artistName, int releaseYear) {
        return albumRepository.findByAlbumNameIgnoreCase(name)
                .orElseGet(() -> {
                        Album album = new Album();
                        album.setAlbumName(name);
                        album.setArtistName(artistName);
                        album.setReleaseYear(releaseYear);
                        return albumRepository.save(album);
                    });
    }

    @Override
    public Album updateAlbum(Long albumId, Album album) {
        Album oldAlbum = getAlbumById(albumId);
        oldAlbum.setAlbumName(album.getAlbumName());
        oldAlbum.setArtistName(album.getArtistName());
        oldAlbum.setReleaseYear(album.getReleaseYear());
        return albumRepository.save(oldAlbum);
    }

    @Override
    public void deleteAlbumById(Long albumId) {
        if(!albumRepository.existsById(albumId)) {
            throw new ResourceNotFoundException("Album not found");
        }
        albumRepository.deleteById(albumId);
    }

    @Override
    public Set<Song> getSongsByAlbumId(Long albumId) {

        if (!albumRepository.existsById(albumId)) {
            throw new ResourceNotFoundException("Album not found with id " + albumId);
        }

        return songRepository.findSongsByAlbumId(albumId);
    }


}
