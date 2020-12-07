package com.project.tim7.api;

import com.project.tim7.dto.UserDTO;
import com.project.tim7.helper.AdministratorMapper;
import com.project.tim7.model.Administrator;
import com.project.tim7.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/administrators", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdministratorController {

    @Autowired
    AdministratorService adminService;

    @Autowired
    AuthenticationController authController;

    @Autowired
    PasswordEncoder passwordEncoder;

    private AdministratorMapper adminMapper;

    public AdministratorController(){
        adminMapper = new AdministratorMapper();
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(method= RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createAdministrator(@Valid @RequestBody UserDTO adminDTO){

        adminDTO.setPassword(passwordEncoder.encode(adminDTO.getPassword()));

        if(!adminService.saveOne(adminMapper.toEntity(adminDTO))){
            return new ResponseEntity<>("Username or email already exists.",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Administrator created.", HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<String> deleteAdministrator(@PathVariable Integer id){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Administrator adminLogged = (Administrator) authentication.getPrincipal();

        if(adminLogged.getId() == id){
            return new ResponseEntity<>("You are logged in.", HttpStatus.BAD_REQUEST);
        }
        if(adminService.delete(id)) {
            return new ResponseEntity<>("Administrator " + id + " deleted.", HttpStatus.OK);
        }

        return new ResponseEntity<>("Administrator " + id + " not found.", HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateAdministrator(@RequestBody UserDTO adminDTO){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Administrator adminLogged = (Administrator) authentication.getPrincipal();

        if(adminDTO.getId() != adminLogged.getId()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String password = adminDTO.getPassword();
        adminDTO.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        Administrator admin = adminService.update(adminMapper.toEntity(adminDTO));

        if(admin == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else{
            authController.updatedLoggedIn(admin.getUsername(), password);
        }

        return new ResponseEntity<>(adminMapper.toDto(admin), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<UserDTO> getAdministrator(@PathVariable Integer id){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Administrator adminLogged = (Administrator) authentication.getPrincipal();

        Administrator admin = adminService.findOne(id);

        if(admin == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else if(admin.getUsername().equals(adminLogged.getUsername())){
            UserDTO adminDTO = adminMapper.toDto(admin);
            return new ResponseEntity<>(adminDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(value= "/by-page",method = RequestMethod.GET)
    public ResponseEntity<Page<UserDTO>> getAllAdministrators(Pageable pageable) {
        Page<Administrator> page = adminService.findAll(pageable);
        List<UserDTO> culturalContentCategoryDTOS = toAdminDTOList(page.toList());
        Page<UserDTO> pageCulturalContentCategoryDTOS = new PageImpl<>(culturalContentCategoryDTOS,page.getPageable(),page.getTotalElements());

        return new ResponseEntity<>(pageCulturalContentCategoryDTOS, HttpStatus.OK);
    }

    private List<UserDTO> toAdminDTOList(List<Administrator> admins) {
        List<UserDTO> adminDTOs = new ArrayList<>();
        for (Administrator admin: admins) {
            adminDTOs.add(adminMapper.toDto(admin));
        }
        return adminDTOs;
    }

}
