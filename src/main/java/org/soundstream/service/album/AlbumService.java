package org.soundstream.service.album;

import org.soundstream.dto.request.CreateAlbumRequest;
import org.soundstream.dto.request.UpdateAlbumRequest;
import org.soundstream.dto.response.AlbumResponseDTO;
import org.soundstream.dto.response.SongResponseDTO;
import org.soundstream.model.Album;
import org.soundstream.model.Song;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface AlbumService {

    AlbumResponseDTO getAlbumById(Long albumId);

    AlbumResponseDTO getAlbumByName(String name);

    Page<AlbumResponseDTO> getAllAlbums(int page, int size);

    AlbumResponseDTO createAlbum(CreateAlbumRequest request);

    AlbumResponseDTO updateAlbum(Long albumId, UpdateAlbumRequest request);

    void deleteAlbumById(Long albumId);

    Set<SongResponseDTO> getSongsByAlbumId(Long albumId);
}