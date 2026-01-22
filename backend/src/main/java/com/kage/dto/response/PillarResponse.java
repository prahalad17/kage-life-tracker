package com.kage.dto.response;

import lombok.Data;

@Data
public class PillarResponse {

    private Long id;
    private String name;
    private String description;
    private boolean active;

    // Optional for UI
    private Long masterPillarId;

}
