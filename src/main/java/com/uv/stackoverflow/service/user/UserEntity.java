package com.uv.stackoverflow.service.user;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by Yuvaraj S on 15/04/22 1:02 PM.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "SO_USER")
public class UserEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private String firstName;
    private String lastName;
    @Column(unique=true)
    private String email;
    private String password;
}

