package org.soundstream.service.album;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.soundstream.dto.request.CreateAlbumRequest;
import org.soundstream.dto.request.UpdateAlbumRequest;
import org.soundstream.dto.response.AlbumResponseDTO;
import org.soundstream.dto.response.SongResponseDTO;
import org.soundstream.exception.ResourceAlreadyExistsException;
import org.soundstream.exception.ResourceNotFoundException;
import org.soundstream.mapper.AlbumMapper;
import org.soundstream.mapper.SongMapper;
import org.soundstream.model.Albums;
import org.soundstream.model.Artists;
import org.soundstream.repository.AlbumRepository;
import org.soundstream.repository.ArtistRepository;
import org.soundstream.repository.SongRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Albums albums = albumRepository.findById(albumId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Album not found")
                );

        log.info("Album found with id={}", albumId);
        return AlbumMapper.toDto(albums);
    }

    @Override
    @Transactional (readOnly = true)
    public AlbumResponseDTO getAlbumByName(String name) {
        Albums albums = albumRepository.findByAlbumNameIgnoreCase(name)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Album not found")
                );
        log.info("Album found with name={}", name);
        return AlbumMapper.toDto(albums);
    }

    @Override
    public Page<AlbumResponseDTO> getAllAlbums(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("albumName").descending());

        Page<Albums> albums = albumRepository.findAll(pageable);

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

        Artists artists = artistRepository.findById(request.getArtistId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Artist not found")
                );
        log.info("Artist ID: {}", artists.getArtistName());

        Albums albums = new Albums();
        albums.setAlbumName(request.getName());
        albums.setReleaseYear(request.getReleaseYear());
        albums.setArtistName(artists.getArtistName());

        Albums saved = albumRepository.save(albums);

        return AlbumMapper.toDto(saved);
    }

    @Override
    public AlbumResponseDTO updateAlbum(Long albumId, UpdateAlbumRequest request) {

        Albums albums = albumRepository.findById(albumId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Album not found with id " + albumId)
                );

        if(request.getName() != null && !request.getName().isEmpty()) {
            albums.setAlbumName(request.getName());
        }

        if(request.getReleaseYear() != null) {
            albums.setReleaseYear(request.getReleaseYear());
        }

        if(request.getArtistId() != null) {
            albums.setAlbumId(request.getArtistId());
        }

        Albums updated = albumRepository.save(albums);
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

        Albums albums = albumRepository.findById(albumId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Album not found")
                );

        log.info("Fetched Songs by Album with id={}", albumId);
        return albums.getSongs()
                .stream()
                .map(SongMapper::toDto)
                .collect(Collectors.toSet());
    }

}
