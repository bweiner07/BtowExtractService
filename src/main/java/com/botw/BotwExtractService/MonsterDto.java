package com.botw.BotwExtractService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonsterDto {

    private String category;

    private ArrayList<String> commonLocations;

    private String description;

    private ArrayList<String> drops;

    private Integer id;

    private String image;

    private String name;

}
