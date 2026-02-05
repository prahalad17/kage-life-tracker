package com.kage.dto.response;

import lombok.Data;

@Data
public class PillarTemplateResponse {

    private Long id;
    private String pillarName;
    private String description;
    private boolean active;
}
