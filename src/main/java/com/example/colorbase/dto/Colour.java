package com.example.colorbase.dto;


import com.example.colorbase.EntityIdResolver;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "colour")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Colour {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotEmpty
    private String name;

    @Column(name = "type")
    @NotEmpty
    private String type;

    @Column(name = "pigments")
    private String pigments;

    @Column(name = "lightfast")
    private String lightfast;

    @Column(name = "staining")
    private String staining;

    @Column(name = "granulation")
    private Boolean granulation;

    @Column(name = "grade")
    @NotEmpty
    private String grade;

    @Column(name = "confirmedASTM")
    @NotEmpty
    private Boolean confirmedASTM;

    @Column(name = "opacity")
    private String opacity;

    @Column(name = "binder")
    private String binder;

    @Column(name = "additives")
    private String additives;


    @Column(name = "image")
    @NotEmpty
    private String image;

    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            resolver = EntityIdResolver.class,
            property = "id",
            scope= Brand.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name="brand_id", nullable=false)
    private Brand brand;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "set_to_colour",
            joinColumns = @JoinColumn(name = "colour_id"),
            inverseJoinColumns = @JoinColumn(name = "set_id")
    )
    private List<Set> sets;


    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "collection_to_colour",
            joinColumns = @JoinColumn(name = "colour_id"),
            inverseJoinColumns = @JoinColumn(name = "collection_id")
    )
    private List<Collection> collections;

}
