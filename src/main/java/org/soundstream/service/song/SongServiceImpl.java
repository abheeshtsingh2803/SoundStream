package org.soundstream.service.song;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.soundstream.dto.request.CreateSongRequest;
import org.soundstream.dto.response.SongResponseDTO;
import org.soundstream.model.Album;
import org.soundstream.model.Artist;
import org.soundstream.model.Song;
import org.soundstream.mapper.SongMapper;
import org.soundstream.repository.AlbumRepository;
import org.soundstream.repository.ArtistRepository;
import org.soundstream.repository.SongRepository;
import org.springframework.stereotype.Service;

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
    public Song getSongById(Long songId) {
        return songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song Not Found"));
    }

    @Override
    public Song getSongByTitle(String title){
        return songRepository.findBySongNameIgnoreCase(title)
                .orElseThrow(() ->  new RuntimeException("Song not found"));
    }

    @Override
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    @Override
    public SongResponseDTO createSong(CreateSongRequest request) {

        log.info("Creating song: {}", request.getTitle());

        Album album = albumRepository.findById(request.getAlbumId())
                .orElseThrow(() -> new RuntimeException("Album Not Found"));

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
    public Song updateSong(Long songId, Song song) {
        return null;
    }

    @Override
    public void deleteSongById(Long songId) {
    songRepository.deleteById(songId);
    }

    @Override
    public Set<Artist> getArtistsBySongId(Long songId) {
        return artistRepository.findArtistsBySongId(songId);
    }

    @Override
    public Album getAlbumBySongId(Long songId) {
        return albumRepository.findAlbumBySongId(songId);
    }
}
