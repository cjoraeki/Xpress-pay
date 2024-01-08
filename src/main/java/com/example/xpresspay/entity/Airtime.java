package com.example.xpresspay.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table
@Entity
@Builder
@ToString
public class Airtime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phoneNumber; //Testing phone number = 08033333333
    private int amount;
}
