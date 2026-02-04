package com.kage.dto.request.activity;

import com.kage.enums.ActivityNature;
import com.kage.enums.TrackingType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO for {@link com.kage.entity.ActivityTemplate}
 */
@AllArgsConstructor
@Getter
public class ActivityTemplateCreateRequest {

    private  Long pillarTemplateId;
    private  String name;
    private  ActivityNature nature;
    private  TrackingType defaultTrackingType;
    private  String defaultUnit;
    private  String description;
}