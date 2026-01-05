package org.soundstream.service.album;

import org.soundstream.dto.request.CreateAlbumRequest;
import org.soundstream.dto.response.AlbumResponseDTO;
import org.soundstream.model.Album;
import org.soundstream.model.Song;

import java.util.List;
import java.util.Set;

public interface AlbumService {

    Album getAlbumById(Long albumId);

    Album getAlbumByName(String name);

    List<Album> getAllAlbums();

    AlbumResponseDTO createAlbum(CreateAlbumRequest request);

    Album updateAlbum(Long albumId, Album album);

    void deleteAlbumById(Long albumId);

    Set<Song> getSongsByAlbumId(Long albumId);
}