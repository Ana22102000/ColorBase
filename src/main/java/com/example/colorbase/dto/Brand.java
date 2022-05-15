package com.example.colorbase.dto;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "brand")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Brand {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "country", nullable = false)
    private String country;

    @JsonIgnore
    @OneToMany(mappedBy="brand", fetch = FetchType.LAZY)
    private List<Colour> colours;

    @JsonIgnore
    @OneToMany(mappedBy="brand", fetch = FetchType.LAZY)
    private List<Set> sets;

}
