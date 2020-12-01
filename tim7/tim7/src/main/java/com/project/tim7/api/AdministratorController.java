package com.project.tim7.api;

import com.project.tim7.dto.AdministratorDTO;
import com.project.tim7.helper.AdministratorMapper;
import com.project.tim7.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value="/administrators", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdministratorController {

    @Autowired
    AdministratorService adminService;

    private AdministratorMapper adminMapper;

    public AdministratorController(){
        adminMapper = new AdministratorMapper();
    }

    @RequestMapping(method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createAdministrator(@Valid @RequestBody AdministratorDTO adminDTO){
        boolean saved = adminService.saveOne(adminMapper.toEntity(adminDTO));

        if(!saved){
            return new ResponseEntity<>("Administrator already exists.",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
