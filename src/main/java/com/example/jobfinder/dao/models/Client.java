package com.example.jobfinder.dao.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;

import java.util.UUID;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Table
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column
    @Length(max = 100)
    private String name;

    @Column
    @Email
    private String email;

    @Column
    @Type(type = "uuid-char")
    private UUID apiKey;

    @PrePersist
    public void buildApiKey(){
        this.apiKey = UUID.randomUUID();
    }
}
