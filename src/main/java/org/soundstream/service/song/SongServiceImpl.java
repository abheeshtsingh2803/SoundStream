package org.soundstream.service.song;

import org.soundstream.model.Album;
import org.soundstream.model.Artist;
import org.soundstream.model.Song;
import org.soundstream.repository.AlbumRepository;
import org.soundstream.repository.ArtistRepository;
import org.soundstream.repository.SongRepository;
import org.soundstream.enums.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.List;
import java.util.Set;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    @Autowired
    public SongServiceImpl(SongRepository songRepository, ArtistRepository artistRepository, AlbumRepository albumRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
    }

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
    @Transactional
    public Song createSong(String title, String artistName, Genre genre, String albumName, int duration) {
        Artist artist = artistRepository.findByArtistNameIgnoreCase(artistName)
                .orElseGet(() -> {
                    Artist newArtist = new Artist();
                    newArtist.setArtistName(artistName);
                    newArtist.setGenre(genre);
                    return artistRepository.save(newArtist);
                });

        Album album = albumRepository.findByAlbumNameIgnoreCase(albumName)
                .orElseGet(() -> {
                    Album newAlbum = new Album();
                    newAlbum.setAlbumName(albumName);
                    newAlbum.setReleaseYear(Year.now().getValue());
                    newAlbum.setArtistName(artistName);
                    return albumRepository.save(newAlbum);
                });

        Song song = new Song();
        song.setSongName(title);
        song.setDuration(duration);
        song.setAlbum(album);
        song.getArtists().add(artist);

        artist.getSongs().add(song);
        album.getSongs().add(song);

        return songRepository.save(song);
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
