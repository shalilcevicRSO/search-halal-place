package com.selma.halal.food.project.models.converters;

import com.selma.halal.food.project.lib.SearchHalalPlaceMetadata;
import com.selma.halal.food.project.models.entities.SearchPlaceMetadataEntity;

public class SearchPlaceMetadataConverter {

    public static SearchHalalPlaceMetadata toDto(SearchPlaceMetadataEntity entity) {

        SearchHalalPlaceMetadata dto = new SearchHalalPlaceMetadata();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCity(entity.getCity());
        dto.setCountry(entity.getCountry());

        return dto;
    }

    public static SearchPlaceMetadataEntity toEntity(SearchHalalPlaceMetadata dto) {

        SearchPlaceMetadataEntity entity = new SearchPlaceMetadataEntity();
        entity.setName(dto.getName());
        entity.setCity(dto.getCity());
        entity.setCountry(dto.getCountry());

        return entity;
    }
}
