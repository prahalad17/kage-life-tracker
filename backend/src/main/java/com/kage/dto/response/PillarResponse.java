package com.kage.dto.response;

import lombok.Data;

@Data
public class PillarResponse {

    private Long id;
    private String pillarName;
    private String description;
    // Optional for UI
    private Long pillarTemplateId;

}
