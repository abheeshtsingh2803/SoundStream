package org.soundstream.service.artist;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.soundstream.dto.request.CreateArtistRequest;
import org.soundstream.dto.response.ArtistResponseDTO;
import org.soundstream.exception.ResourceAlreadyExistsException;
import org.soundstream.exception.ResourceNotFoundException;
import org.soundstream.mapper.ArtistMapper;
import org.soundstream.model.Artist;
import org.soundstream.model.Song;
import org.soundstream.repository.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArtistServiceImpl implements ArtistService{

    private final ArtistRepository artistRepository;

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
    public ArtistResponseDTO createArtist(CreateArtistRequest request) {

        boolean exists = artistRepository
                .findByArtistNameIgnoreCase(request.getName())
                .isPresent();

        if (exists) {
            throw new ResourceAlreadyExistsException(
                    "Artist already exists with name: " + request.getName()
            );
        }

        Artist artist = new Artist();
        artist.setArtistName(request.getName());
        artist.setGenre(request.getGenre());

        Artist saved = artistRepository.save(artist);

        return ArtistMapper.toDto(saved);
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
