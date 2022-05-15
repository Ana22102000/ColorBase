package com.example.colorbase.dto.users;

import com.example.colorbase.EntityIdResolver;
import com.example.colorbase.dto.Brand;
import com.example.colorbase.dto.Collection;
import com.example.colorbase.dto.Role;
import com.example.colorbase.dto.Set;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user")
@ToString
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "login", unique = true)
    @NotEmpty
    @Length(min = 3, max = 30)
    private String login;

    @Column(name = "password")
    private String password;



    @Column(name = "name", unique = true)
    @NotEmpty
    @Length(min = 3, max = 20)
    private String name;

    @Column(name = "surname", unique = true)
    @NotEmpty
    @Length(min = 3, max = 20)
    private String surname;

//    @Column(name = "role", unique = true)
//    @NotEmpty
//    @Length(min = 3, max = 20)
//    private String role;

    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            resolver = EntityIdResolver.class,
            property = "id",
            scope= Role.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name="role_id", nullable=false)
    private Role role;


//    @ManyToMany
//    @JoinTable(
//            name = "user_to_role",
//            joinColumns = @JoinColumn(
//                    name = "user_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "role_id", referencedColumnName = "id"))
//    private List<Roles> roles;

    @JsonIgnore
    @OneToMany(mappedBy="user", fetch = FetchType.LAZY)
    private List<Collection> collections;
}
