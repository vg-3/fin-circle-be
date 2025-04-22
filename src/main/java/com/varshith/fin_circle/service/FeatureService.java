package com.varshith.fin_circle.service;

import com.varshith.fin_circle.dto.FeatureResponseDto;
import com.varshith.fin_circle.entity.Feature;
import com.varshith.fin_circle.repository.FeatureRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureService {

    private final FeatureRepository featureRepository;

    public FeatureService(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    public List<Feature> getFeatures(){
        return featureRepository.findAll();
    }

    public Feature addFeature(FeatureResponseDto featureResponseDto){
        Feature feature = Feature
                .builder()
                .featureName(featureResponseDto.featureName())
                .featureStatus(featureResponseDto.featureStatus())
                .build();
        return  featureRepository.save(feature);
    }
}
