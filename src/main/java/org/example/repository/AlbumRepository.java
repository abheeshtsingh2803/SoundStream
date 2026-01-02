package org.example.repository;

import org.example.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    Optional<Album> findByAlbumNameIgnoreCase(String name);

    @Query("SELECT a FROM Album a JOIN a.songs s WHERE s.id = :songId")
    Album findAlbumBySongId(@Param("songId") Long songId);
}
