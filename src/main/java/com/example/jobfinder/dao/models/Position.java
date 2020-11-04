package com.example.jobfinder.dao.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Builder
@Entity
@Table(name = "JOB_POSITION")
@NoArgsConstructor
@AllArgsConstructor
public class Position {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column
    @Length(max = 50)
    private String name;

    @Column
    @Length(max = 50)
    private String local;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "client_id", nullable = false, referencedColumnName = "id")
    @JsonIgnore
    private Client client;

    @Transient
    private String url;
}
