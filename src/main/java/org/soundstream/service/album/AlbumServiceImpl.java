package org.soundstream.service.album;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.soundstream.dto.request.CreateAlbumRequest;
import org.soundstream.dto.request.UpdateAlbumRequest;
import org.soundstream.dto.request.UpdateSongRequest;
import org.soundstream.dto.response.AlbumResponseDTO;
import org.soundstream.dto.response.SongResponseDTO;
import org.soundstream.exception.ResourceAlreadyExistsException;
import org.soundstream.exception.ResourceNotFoundException;
import org.soundstream.mapper.AlbumMapper;
import org.soundstream.mapper.SongMapper;
import org.soundstream.model.Album;
import org.soundstream.model.Artist;
import org.soundstream.model.Song;
import org.soundstream.repository.AlbumRepository;
import org.soundstream.repository.ArtistRepository;
import org.soundstream.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlbumServiceImpl implements AlbumService{

    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;

    @Override
    @Transactional (readOnly = true)
    public AlbumResponseDTO getAlbumById(Long albumId) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Album not found")
                );

        log.info("Album found with id={}", albumId);
        return AlbumMapper.toDto(album);
    }

    @Override
    @Transactional (readOnly = true)
    public AlbumResponseDTO getAlbumByName(String name) {
        Album album = albumRepository.findByAlbumNameIgnoreCase(name)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Album not found")
                );
        log.info("Album found with name={}", name);
        return AlbumMapper.toDto(album);
    }

    @Override
    public Page<AlbumResponseDTO> getAllAlbums(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("albumName").descending());

        Page<Album> albums = albumRepository.findAll(pageable);

        return albums.map(AlbumMapper::toDto);
    }

    @Override
    public AlbumResponseDTO createAlbum(CreateAlbumRequest request) {

        boolean exists = albumRepository
                .findByAlbumNameIgnoreCase(request.getName())
                .isPresent();

        if (exists) {
            log.error("Album creation failed. Album already exists with name={}",
                    request.getName());

            throw new ResourceAlreadyExistsException(
                    "Album already exists with name: " + request.getName()
            );
        }

        log.info("Creating album: {}", request.getName());

        Artist artist = artistRepository.findById(request.getArtistId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Artist not found")
                );
        log.info("Artist ID: {}", artist.getArtistName());

        Album album = new Album();
        album.setAlbumName(request.getName());
        album.setReleaseYear(request.getReleaseYear());
        album.setArtistName(artist.getArtistName());

        Album saved = albumRepository.save(album);

        return AlbumMapper.toDto(saved);
    }

    @Override
    public AlbumResponseDTO updateAlbum(Long albumId, UpdateAlbumRequest request) {

        Album album = albumRepository.findById(albumId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Album not found with id " + albumId)
                );

        if(request.getName() != null && !request.getName().isEmpty()) {
            album.setAlbumName(request.getName());
        }

        if(request.getReleaseYear() != null) {
            album.setReleaseYear(request.getReleaseYear());
        }

        if(request.getArtistId() != null) {
            album.setAlbumId(request.getArtistId());
        }

        Album updated = albumRepository.save(album);
        return AlbumMapper.toDto(updated);
    }

    @Override
    public void deleteAlbumById(Long albumId) {

        if(!albumRepository.existsById(albumId)) {
            throw new ResourceNotFoundException("Album not found" + albumId);
        }

        albumRepository.deleteById(albumId);

        log.info("Album deleted with id={}", albumId);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<SongResponseDTO> getSongsByAlbumId(Long albumId) {

        Album album = albumRepository.findById(albumId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Album not found")
                );

        log.info("Fetched Songs by Album with id={}", albumId);
        return album.getSongs()
                .stream()
                .map(SongMapper::toDto)
                .collect(Collectors.toSet());
    }

}
