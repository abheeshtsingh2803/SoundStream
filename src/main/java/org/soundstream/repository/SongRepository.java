package org.soundstream.repository;

import org.soundstream.model.Songs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface SongRepository extends JpaRepository<Songs,Long> {
    Optional<Songs> findBySongNameIgnoreCase(String title);

    @Query("SELECT s FROM Song s WHERE s.album.albumId = :albumId")
    Set<Songs> findSongsByAlbumId(@Param("albumId") Long albumId);

}
