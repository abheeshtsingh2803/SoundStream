package org.example.service;

import org.example.enums.Genre;
import org.example.exceptions.ResourceNotFoundException;
import org.example.model.Artist;
import org.example.model.Song;
import org.example.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ArtistServiceImpl implements ArtistService{

    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistServiceImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public Artist getArtistById(Long artistId) {
        return artistRepository.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
    }

    @Override
    public Artist getArtistByName(String name) {
        return artistRepository.findByArtistNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
    }

    @Override
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    @Override
    public Artist createArtist(String name, Genre genre) {
        return artistRepository.findByArtistNameIgnoreCase(name)
                .orElseGet(() -> {
                    Artist newArtist = new Artist();
                    newArtist.setArtistName(name);
                    newArtist.setGenre(genre);
                    return artistRepository.save(newArtist);
                });
    }

    @Override
    public Artist updateArtist(Long artistId, Artist artist) {
        Artist oldArtist = getArtistById(artistId);
        oldArtist.setArtistName(artist.getArtistName());
        oldArtist.setGenre(artist.getGenre());
        return artistRepository.save(oldArtist);
    }

    @Override
    public void deleteArtistById(Long artistId) {
        if (!artistRepository.existsById(artistId)) {
            throw new ResourceNotFoundException("Artist not found with id " + artistId);
        }

        artistRepository.deleteById(artistId);
    }

    @Override
    public Set<Song> getSongsByArtistId(Long artistId) {
        Artist artist = getArtistById(artistId);

        return artist.getSongs();
    }
}
