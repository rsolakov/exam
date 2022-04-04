package com.endava.exam.model;

import com.endava.exam.model.enums.PaymentType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Supermarket supermarket;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private Double cashAmount;

}