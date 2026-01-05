package org.soundstream.service.album;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.soundstream.dto.request.CreateAlbumRequest;
import org.soundstream.dto.response.AlbumResponseDTO;
import org.soundstream.exception.ResourceNotFoundException;
import org.soundstream.mapper.AlbumMapper;
import org.soundstream.model.Album;
import org.soundstream.model.Artist;
import org.soundstream.model.Song;
import org.soundstream.repository.AlbumRepository;
import org.soundstream.repository.ArtistRepository;
import org.soundstream.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlbumServiceImpl implements AlbumService{

    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;

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
    public AlbumResponseDTO createAlbum(CreateAlbumRequest request) {

        log.info("Creating album: {}", request.getName());

        Artist artist = artistRepository.findById(request.getArtistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
        log.info("Artist ID: {}", artist.getArtistName());

        Album album = new Album();
        album.setAlbumName(request.getName());
        album.setReleaseYear(request.getReleaseYear());
        album.setArtistName(artist.getArtistName());

        Album saved = albumRepository.save(album);

        return AlbumMapper.toDto(saved);
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
