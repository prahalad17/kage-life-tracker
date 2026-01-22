package com.kage.dto.request;

import com.kage.entity.PillarTemplate;
import com.kage.enums.ActivityNature;
import com.kage.enums.TrackingType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * DTO for {@link com.kage.entity.ActivityTemplate}
 */
@AllArgsConstructor
@Getter
public class ActivityTemplateUpdateRequest  {
    private  Long pillarTemplateId;
    private  String name;
    private  ActivityNature nature;
    private  TrackingType defaultTrackingType;
    private  String defaultUnit;
    private  String description;
}