package com.varshith.fin_circle.dto;

import com.varshith.fin_circle.enumeration.FeatureStatus;

public record FeatureResponseDto(String featureName, FeatureStatus featureStatus) {
}
