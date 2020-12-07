package com.project.tim7.helper;

import com.project.tim7.dto.UserDTO;
import com.project.tim7.model.Administrator;

public class AdministratorMapper implements MapperInterface<Administrator, UserDTO> {

    @Override
    public Administrator toEntity(UserDTO dto) {
        return new Administrator(dto.getId(), dto.getEmail(), dto.getUsername(), dto.getPassword());
    }

    @Override
    public UserDTO toDto(Administrator entity) {
        return new UserDTO(entity.getId(), entity.getUsername(), entity.getEmail());
    }
}
