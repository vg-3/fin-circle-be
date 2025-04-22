package com.varshith.fin_circle.repository;

import com.varshith.fin_circle.entity.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Integer> {
}
