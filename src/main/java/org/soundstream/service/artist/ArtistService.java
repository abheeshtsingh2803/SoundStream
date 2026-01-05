package org.soundstream.service.artist;

import org.soundstream.dto.request.CreateArtistRequest;
import org.soundstream.dto.response.ArtistResponseDTO;
import org.soundstream.model.Artist;
import org.soundstream.model.Song;

import java.util.List;
import java.util.Set;

public interface ArtistService {

    Artist getArtistById(Long artistId);

    Artist getArtistByName(String name);

    List<Artist> getAllArtists();

    ArtistResponseDTO createArtist(CreateArtistRequest request);

    Artist updateArtist(Long artistId, Artist artist);

    void deleteArtistById(Long artistId);

    Set<Song> getSongsByArtistId(Long artistId);
}
