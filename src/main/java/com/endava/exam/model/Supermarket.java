package com.endava.exam.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Supermarket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank
    private String name;

    @NotBlank
    private String city;

    @NotBlank
    private String street;

    @NotBlank
    private String streetNumber;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String workHours;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    private List<Item> items = new ArrayList<>();

    @OneToOne(mappedBy = "supermarket")
    private Purchase purchase;
}