package com.project.tim7.helper;

import com.project.tim7.dto.AdministratorDTO;
import com.project.tim7.model.Administrator;

public class AdministratorMapper implements MapperInterface<Administrator, AdministratorDTO> {

    @Override
    public Administrator toEntity(AdministratorDTO dto) {
        return new Administrator(dto.getEmail(), dto.getUsername(), dto.getPassword());
    }

    @Override
    public AdministratorDTO toDto(Administrator entity) {
        return new AdministratorDTO(entity.getId(), entity.getUsername(), entity.getEmail(), entity.getPassword());
    }
}
