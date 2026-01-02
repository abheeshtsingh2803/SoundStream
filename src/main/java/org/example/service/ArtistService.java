package org.example.service;

import org.example.model.Artist;
import org.example.model.Song;
import org.example.enums.Genre;

import java.util.List;
import java.util.Set;

public interface ArtistService {

    Artist getArtistById(Long artistId);

    Artist getArtistByName(String name);

    List<Artist> getAllArtists();

    Artist createArtist(String name, Genre genre);

    Artist updateArtist(Long artistId, Artist artist);

    void deleteArtistById(Long artistId);

    Set<Song> getSongsByArtistId(Long artistId);
}
