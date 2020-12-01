package com.project.tim7.api;

import com.project.tim7.dto.UserDTO;
import com.project.tim7.helper.RegisteredMapper;
import com.project.tim7.model.Registered;
import com.project.tim7.service.RegisteredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/registered-users")
public class RegisteredController {

    @Autowired
    RegisteredService regService;

    private RegisteredMapper regMapper;

    public RegisteredController() {
        this.regMapper = new RegisteredMapper();
    }

    @RequestMapping(method= RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateRegistered(@RequestBody UserDTO regDTO){

        Registered reg = regService.update(regMapper.toEntity(regDTO));
        if(reg == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(regMapper.toDto(reg), HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<UserDTO> getRegistered(@PathVariable Integer id){

        Registered reg = regService.findOne(id);

        if(reg == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserDTO regDTO = regMapper.toDto(reg);
        return new ResponseEntity<>(regDTO, HttpStatus.OK);
    }

}
