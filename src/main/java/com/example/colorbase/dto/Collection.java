package com.example.colorbase.dto;

import com.example.colorbase.EntityIdResolver;
import com.example.colorbase.dto.users.User;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "collection")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Collection {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotEmpty
    private String name;

    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            resolver = EntityIdResolver.class,
            property = "id",
            scope= User.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "collection_to_colour",
            joinColumns = @JoinColumn(name = "collection_id"),
            inverseJoinColumns = @JoinColumn(name = "colour_id")
    )
    private List<Colour> colours;

}
