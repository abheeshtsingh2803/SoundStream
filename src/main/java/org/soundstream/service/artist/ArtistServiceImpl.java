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
import org.soundstream.model.Artists;
import org.soundstream.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ArtistServiceImpl implements ArtistService{

    @Autowired
    private final ArtistRepository artistRepository;

    @Override
    @Transactional(readOnly = true)
    public ArtistResponseDTO getArtistById(Long artistId) {

        Artists artists = artistRepository.findById(artistId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Artist not found")
                );

        log.info("getArtistById: artistId={}", artistId);
        return ArtistMapper.toDto(artists);
    }

    @Override
    public ArtistResponseDTO getArtistByName(String name) {
        Artists artists = artistRepository.findByArtistNameIgnoreCase(name)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Artist not found")
                );

        log.info("getArtistByName: artistName={}", artists.getArtistName());
        return ArtistMapper.toDto(artists);
    }

    @Override
    public Page<ArtistResponseDTO> getAllArtists(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("artistName").ascending());

        Page<Artists> artists = artistRepository.findAll(pageable);

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

        Artists artists = new Artists();
        artists.setArtistName(request.getName());
        artists.setGenre(request.getGenre());

        Artists saved = artistRepository.save(artists);

        log.info("Artist created successfully with name={}", saved.getArtistName());

        return ArtistMapper.toDto(saved);
    }

    @Override
    @Transactional
    public ArtistResponseDTO updateArtist(Long artistId, UpdateArtistRequest request) {

        Artists artists = artistRepository.findById(artistId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Artist not found with id: " + artistId)
                );

        if (request.getName() != null) {
            artists.setArtistName(request.getName());
        }

        if (request.getGenre() != null) {
            artists.setGenre(request.getGenre());
        }

        return ArtistMapper.toDto(artistRepository.save(artists));
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
        Artists artists = artistRepository.findById(artistId)
                .orElseThrow(() ->
                            new ResourceNotFoundException("Artist not found with id: " + artistId)
                        );
        log.info("getSongsByArtistId: artistId={}", artistId);
        return artists.getSongs()
                .stream()
                .map(SongMapper::toDto)
                .collect(Collectors.toSet());
    }
}
