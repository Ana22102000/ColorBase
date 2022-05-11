package com.example.colorbase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Roles {

        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Integer id;

        @Enumerated(EnumType.STRING)
        @Column(name = "role")
        private RoleName role;

        public enum RoleName {
            OWNER,
            ADMIN,
            CLIENT,
        }
}
