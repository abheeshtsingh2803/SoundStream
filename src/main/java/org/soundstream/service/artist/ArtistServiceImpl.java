package org.soundstream.service.artist;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.soundstream.dto.request.CreateArtistRequest;
import org.soundstream.dto.request.UpdateArtistRequest;
import org.soundstream.dto.response.ArtistResponseDTO;
import org.soundstream.dto.response.SongResponseDTO;
import org.soundstream.exception.ResourceAlreadyExistsException;
import org.soundstream.exception.ResourceNotFoundException;
import org.soundstream.mapper.ArtistMapper;
import org.soundstream.mapper.SongMapper;
import org.soundstream.model.Artist;
import org.soundstream.model.Song;
import org.soundstream.repository.ArtistRepository;
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
public class ArtistServiceImpl implements ArtistService{

    @Autowired
    private final ArtistRepository artistRepository;

    @Override
    @Transactional(readOnly = true)
    public ArtistResponseDTO getArtistById(Long artistId) {

        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Artist not found")
                );

        log.info("getArtistById: artistId={}", artistId);
        return ArtistMapper.toDto(artist);
    }

    @Override
    public ArtistResponseDTO getArtistByName(String name) {
        Artist artist = artistRepository.findByArtistNameIgnoreCase(name)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Artist not found")
                );

        log.info("getArtistByName: artistName={}", artist.getArtistName());
        return ArtistMapper.toDto(artist);
    }

    @Override
    public Page<ArtistResponseDTO> getAllArtists(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("artistName").ascending());

        Page<Artist> artists = artistRepository.findAll(pageable);

        return artists.map(ArtistMapper::toDto);
    }


    @Override
    public ArtistResponseDTO createArtist(CreateArtistRequest request) {

        boolean exists = artistRepository
                .findByArtistNameIgnoreCase(request.getName())
                .isPresent();

        if (exists) {
            log.error("Artist creation failed. Artist already exists with name={}",
                    request.getName());

            throw new ResourceAlreadyExistsException(
                    "Artist already exists with name: " + request.getName()
            );
        }

        Artist artist = new Artist();
        artist.setArtistName(request.getName());
        artist.setGenre(request.getGenre());

        Artist saved = artistRepository.save(artist);

        log.info("Artist created successfully with name={}", saved.getArtistName());

        return ArtistMapper.toDto(saved);
    }

    @Override
    @Transactional
    public ArtistResponseDTO updateArtist(Long artistId, UpdateArtistRequest request) {

        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Artist not found with id: " + artistId)
                );

        if (request.getName() != null) {
            artist.setArtistName(request.getName());
        }

        if (request.getGenre() != null) {
            artist.setGenre(request.getGenre());
        }

        return ArtistMapper.toDto(artistRepository.save(artist));
    }


    @Override
    public void deleteArtistById(Long artistId) {
        if (!artistRepository.existsById(artistId)) {
            throw new ResourceNotFoundException("Artist not found with id " + artistId);
        }

        artistRepository.deleteById(artistId);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<SongResponseDTO> getSongsByArtistId(Long artistId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() ->
                            new ResourceNotFoundException("Artist not found with id: " + artistId)
                        );
        log.info("getSongsByArtistId: artistId={}", artistId);
        return artist.getSongs()
                .stream()
                .map(SongMapper::toDto)
                .collect(Collectors.toSet());
    }
}
