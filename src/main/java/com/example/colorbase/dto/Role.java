package com.example.colorbase.dto;

import com.example.colorbase.dto.users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Integer id;

//        @Enumerated(EnumType.STRING)
        @Column(name = "role")
        private String role;

    @JsonIgnore
    @OneToMany(mappedBy="role", fetch = FetchType.LAZY)
    private List<User> users;


    public enum RoleName {
            OWNER,
            ADMIN,
            CLIENT,
        }
}
