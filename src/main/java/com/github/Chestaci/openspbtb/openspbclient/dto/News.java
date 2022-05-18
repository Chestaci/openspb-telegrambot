package com.github.Chestaci.openspbtb.openspbclient.dto;

import lombok.Data;
import lombok.ToString;

/**
 * DTO, which represents news information.
 */
@Data
@ToString
public class News {
    private String title;
    private Long newsId;
    private String link;
    private String date;
    private String rubric;
}
