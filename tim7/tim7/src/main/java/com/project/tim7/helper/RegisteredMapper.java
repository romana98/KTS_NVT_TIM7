package com.project.tim7.helper;

import com.project.tim7.dto.UserDTO;
import com.project.tim7.model.Administrator;
import com.project.tim7.model.Registered;

public class RegisteredMapper implements MapperInterface<Registered, UserDTO> {


    @Override
    public Registered toEntity(UserDTO dto) {
        return new Registered(dto.getId(), dto.getEmail(), dto.getUsername(), dto.getPassword());
    }

    @Override
    public UserDTO toDto(Registered entity) {
        return new UserDTO(entity.getId(), entity.getUsername(), entity.getEmail());
    }
}
