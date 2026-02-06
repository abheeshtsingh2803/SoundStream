package org.soundstream.service.artist;

import org.soundstream.dto.request.CreateArtistRequest;
import org.soundstream.dto.request.UpdateArtistRequest;
import org.soundstream.dto.response.ArtistResponseDTO;
import org.soundstream.dto.response.SongResponseDTO;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface ArtistService {

    ArtistResponseDTO getArtistById(Long artistId);

    ArtistResponseDTO getArtistByName(String name);

    Page<ArtistResponseDTO> getAllArtists(int page, int size);

    ArtistResponseDTO createArtist(CreateArtistRequest request);

    ArtistResponseDTO updateArtist(Long artistId, UpdateArtistRequest request);

    void deleteArtistById(Long artistId);

    Set<SongResponseDTO> getSongsByArtistId(Long artistId);
}
