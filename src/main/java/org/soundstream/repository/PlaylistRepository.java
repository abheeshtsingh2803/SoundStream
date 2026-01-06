package org.soundstream.repository;

import org.soundstream.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Optional<Playlist> findByPlaylistNameIgnoreCase(String playlistName);

    boolean existsByPlaylistNameIgnoreCase(String playlistName);
}
