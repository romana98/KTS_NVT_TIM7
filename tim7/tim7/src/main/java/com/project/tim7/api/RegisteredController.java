package com.project.tim7.api;

import com.project.tim7.dto.UserDTO;
import com.project.tim7.helper.RegisteredMapper;
import com.project.tim7.model.Administrator;
import com.project.tim7.model.Registered;
import com.project.tim7.service.RegisteredService;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value="/registered-users")
public class RegisteredController {

    @Autowired
    RegisteredService regService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationController authController;

    private RegisteredMapper regMapper;

    public RegisteredController() {
        this.regMapper = new RegisteredMapper();
    }

    @PreAuthorize("hasRole('ROLE_REGISTERED')")
    @RequestMapping(method= RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateRegistered(@Valid @RequestBody UserDTO regDTO){


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Registered regLogged = (Registered) authentication.getPrincipal();

        if(!regDTO.getUsername().equals(regLogged.getUsername()) || regDTO.getId() != regLogged.getId()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String password = regDTO.getPassword();
        regDTO.setPassword(passwordEncoder.encode(regDTO.getPassword()));
        Registered reg = regService.update(regMapper.toEntity(regDTO));

        if(reg == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else{
            authController.updatedLoggedIn(reg.getUsername(), password);
        }

        return new ResponseEntity<>(regMapper.toDto(reg), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ROLE_REGISTERED')")
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<UserDTO> getRegistered(@PathVariable Integer id){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Registered regLogged = (Registered) authentication.getPrincipal();

        Registered reg = regService.findOne(id);

        if(reg == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        }else if(reg.getUsername().equals(regLogged.getUsername())){
            UserDTO regDTO = regMapper.toDto(reg);
            return new ResponseEntity<>(regDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

    }

}
