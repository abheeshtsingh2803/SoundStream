package org.example.repository;

import org.example.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface SongRepository extends JpaRepository<Song,Long> {
    Optional<Song> findBySongNameIgnoreCase(String title);

    @Query("SELECT s FROM Song s WHERE s.album.albumId = :albumId")
    Set<Song> findSongsByAlbumId(@Param("albumId") Long albumId);

}
