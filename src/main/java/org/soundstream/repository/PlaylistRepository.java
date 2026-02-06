package org.soundstream.repository;

import org.soundstream.model.Playlists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlists, Long> {
    Optional<Playlists> findByPlaylistNameIgnoreCase(String playlistName);

    boolean existsByPlaylistNameIgnoreCase(String playlistName);
}
