package org.example.service;

import org.example.model.Album;
import org.example.model.Song;

import java.util.List;
import java.util.Set;

public interface AlbumService {

    Album getAlbumById(Long albumId);

    Album getAlbumByName(String name);

    List<Album> getAllAlbums();

    Album createAlbum(String name, String artistName, int releaseYear);

    Album updateAlbum(Long albumId, Album album);

    void deleteAlbumById(Long albumId);

    Set<Song> getSongsByAlbumId(Long albumId);
}
