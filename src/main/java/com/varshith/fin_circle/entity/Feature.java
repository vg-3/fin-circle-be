package com.varshith.fin_circle.entity;

import com.varshith.fin_circle.enumeration.FeatureStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="feature")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer featureId;

    private String featureName;

    private FeatureStatus featureStatus;
}
