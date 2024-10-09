package com.example.mihail.lytov.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "result_entity")
public class ResultEntity {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean inn;

    private Boolean region;

    private Boolean capital;

    private Boolean resident;

    private Boolean result;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "users")
    private User user;
}
