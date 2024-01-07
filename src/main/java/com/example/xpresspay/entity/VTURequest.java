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
public class VTURequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;
    private String uniqueCode;
    @OneToOne
    private Airtime details;
}
