package org.example.repository;

import org.example.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Optional<Artist> findByArtistNameIgnoreCase(String name);

    @Query("SELECT a FROM Artist a JOIN a.songs s WHERE s.id = :songId")
    Set<Artist> findArtistsBySongId(@Param("songId") Long songId);
}
