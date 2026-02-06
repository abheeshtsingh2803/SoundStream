package org.soundstream.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.soundstream.enum_.Genre;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Artists {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long artistId;

    private String artistName;
    @Enumerated (EnumType .STRING)
    private Genre genre;

    @ManyToMany(mappedBy = "artists", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Songs> songs = new HashSet<>();
}