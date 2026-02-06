package org.soundstream.repository;

import org.soundstream.model.Artists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ArtistRepository extends JpaRepository<Artists, Long> {
    Optional<Artists> findByArtistNameIgnoreCase(String name);

    @Query("SELECT a FROM Artist a JOIN a.songs s WHERE s.id = :songId")
    Set<Artists> findArtistsBySongId(@Param("songId") Long songId);
}
