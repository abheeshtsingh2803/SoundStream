package org.soundstream.service.song;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.soundstream.dto.request.CreateSongRequest;
import org.soundstream.dto.request.UpdateSongRequest;
import org.soundstream.dto.response.AlbumResponseDTO;
import org.soundstream.dto.response.ArtistResponseDTO;
import org.soundstream.dto.response.SongResponseDTO;
import org.soundstream.exception.ResourceAlreadyExistsException;
import org.soundstream.exception.ResourceNotFoundException;
import org.soundstream.mapper.AlbumMapper;
import org.soundstream.mapper.ArtistMapper;
import org.soundstream.model.Album;
import org.soundstream.model.Artist;
import org.soundstream.model.Song;
import org.soundstream.mapper.SongMapper;
import org.soundstream.repository.AlbumRepository;
import org.soundstream.repository.ArtistRepository;
import org.soundstream.repository.SongRepository;
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
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    @Override
    @Transactional(readOnly = true)
    public SongResponseDTO getSongById(Long songId) {

        Song song = songRepository.findById(songId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Song not found with id: " + songId)
                );

        log.info("Fetched song with id={}", songId);

        return SongMapper.toDto(song);
    }


    @Override
    @Transactional(readOnly = true)
    public SongResponseDTO getSongByTitle(String title) {

        Song song = songRepository.findBySongNameIgnoreCase(title)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Song not found with title: " + title)
                );

        log.info("Fetched song with title='{}'", title);

        return SongMapper.toDto(song);
    }


    @Override
    public Page<SongResponseDTO> getAllSongs(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("songName").ascending());

        Page<Song> songs = songRepository.findAll(pageable);

        return songs.map(SongMapper::toDto);
    }


    @Override
    public SongResponseDTO createSong(CreateSongRequest request) {

        boolean exists = songRepository
                .findBySongNameIgnoreCase(request.getTitle())
                .isPresent();

        if (exists) {
            log.error("Song creation failed. Song already exists with name={}",
                    request.getTitle());

            throw new ResourceAlreadyExistsException(
                    "Song already exists with name: " + request.getTitle()
            );
        }

        log.info("Creating song: {}", request.getTitle());

        Album album = albumRepository.findById(request.getAlbumId())
                .orElseThrow(() ->
                        new RuntimeException("Album Not Found")
                );

        Set<Artist> artists = request.getArtistIds()
                .stream()
                .map(artistRepository::getReferenceById)
                .collect(Collectors.toSet());

        Song song = new Song();
        song.setSongName(request.getTitle());
        song.setDuration(request.getDuration());
        song.setAlbum(album);
        song.getArtists().addAll(artists);

        Song saved = songRepository.save(song);

        return SongMapper.toDto(saved);
    }


    @Override
    public SongResponseDTO updateSong(Long songId, UpdateSongRequest request) {

        Song song = songRepository.findById(songId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Song not found with id: " + songId)
                );

        if (request.getTitle() != null) {
            song.setSongName(request.getTitle());
        }

        if (request.getDuration() != null) {
            song.setDuration(request.getDuration());
        }

        if (request.getAlbumId() != null) {
            Album album = albumRepository.getReferenceById(request.getAlbumId());
            song.setAlbum(album);
        }

        if (request.getArtistIds() != null) {
            Set<Artist> artists = request.getArtistIds()
                    .stream()
                    .map(artistRepository::getReferenceById)
                    .collect(Collectors.toSet());
            song.setArtists(artists);
        }

        return SongMapper.toDto(songRepository.save(song));
    }


    @Override
    public void deleteSongById(Long songId) {

        if (!songRepository.existsById(songId)) {
            throw new ResourceNotFoundException("Song not found with id: " + songId);
        }

        songRepository.deleteById(songId);

        log.info("Song deleted successfully with id={}", songId);
    }


    @Override
    @Transactional(readOnly = true)
    public Set<ArtistResponseDTO> getArtistsBySongId(Long songId) {

        Song song = songRepository.findById(songId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Song not found with id: " + songId)
                );

        log.info("Fetched artists for songId={}", songId);

        return song.getArtists()
                .stream()
                .map(ArtistMapper::toDto)
                .collect(Collectors.toSet());
    }



    @Override
    @Transactional(readOnly = true)
    public AlbumResponseDTO getAlbumBySongId(Long songId) {

        Song song = songRepository.findById(songId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Song not found with id: " + songId)
                );

        log.info("Fetched album for songId={}", songId);

        return AlbumMapper.toDto(song.getAlbum());
    }

}
