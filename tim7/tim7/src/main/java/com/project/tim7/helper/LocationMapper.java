package com.project.tim7.helper;

import com.project.tim7.dto.LocationDTO;
import com.project.tim7.model.Location;

public class LocationMapper implements MapperInterface<Location, LocationDTO> {
    @Override
    public Location toEntity(LocationDTO dto) {
        return new Location(dto.getId(), dto.getLongitude(), dto.getLatitude(),dto.getName());
    }

    @Override
    public LocationDTO toDto(Location entity) {

        LocationDTO dto = new LocationDTO();
        dto.setName(entity.getName());
        dto.setLongitude(entity.getLongitude());
        dto.setLatitude(entity.getLatitude());
        dto.setId(entity.getId());
        return dto;
    }
}
