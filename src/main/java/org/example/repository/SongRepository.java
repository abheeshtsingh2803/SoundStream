package org.example.repository;

import org.example.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface SongRepository extends JpaRepository<Song,Long> {
    Optional<Song> findBySongNameIgnoreCase(String title);

    Set<Song> findSongsByAlbumId(Long albumId);
}
