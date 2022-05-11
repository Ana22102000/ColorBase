package com.example.colorbase.dto.users;

import com.example.colorbase.dto.Roles;
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
@Table(name = "users")
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

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private List<Roles> roles;
}
