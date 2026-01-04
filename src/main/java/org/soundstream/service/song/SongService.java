package org.soundstream.service.song;

import org.soundstream.model.Album;
import org.soundstream.model.Artist;
import org.soundstream.model.Song;
import org.soundstream.enums.Genre;

import java.util.List;
import java.util.Set;

public interface SongService {

    Song getSongById(Long songId);

    Song getSongByTitle(String title);

    List<Song> getAllSongs();

    Song createSong(String title, String artistName, Genre genre, String albumName, int duration);

    Song updateSong(Long songId, Song song);

    void deleteSongById(Long songId);

    Set<Artist> getArtistsBySongId(Long songId);

    Album getAlbumBySongId(Long songId);
}
