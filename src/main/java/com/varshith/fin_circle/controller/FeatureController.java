package com.varshith.fin_circle.controller;

import com.varshith.fin_circle.dto.FeatureResponseDto;
import com.varshith.fin_circle.entity.Feature;
import com.varshith.fin_circle.service.FeatureService;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/feature")
public class FeatureController {

    private final FeatureService featureService;

    public FeatureController(FeatureService featureService) {
        this.featureService = featureService;
    }

    @GetMapping()
    public List<Feature>  getFeatures(){
        return  featureService.getFeatures();
    }

    @PostMapping()
    public Feature addFeature(@RequestBody FeatureResponseDto featureResponseDto){
        return  featureService.addFeature(featureResponseDto);
    }
}


//      {
//        "featureName": "Micro Finance",
//        "featureStatus": "UNAVAILABLE"
//        }

//        {
//        "featureName": "Individual Finance",
//        "featureStatus": "UNAVAILABLE"
//        }
//
//
//        {
//        "featureName": "Chit Funds",
//        "featureStatus": "AVAILABLE"
//        }
//
//        {
//        "featureName": "Expense Tracker",
//        "featureStatus": "UNAVAILABLE"
//        }