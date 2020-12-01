package com.project.tim7.api;

import com.project.tim7.dto.AdministratorDTO;
import com.project.tim7.helper.AdministratorMapper;
import com.project.tim7.model.Administrator;
import com.project.tim7.model.Category;
import com.project.tim7.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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
        if(!adminService.saveOne(adminMapper.toEntity(adminDTO))){
            return new ResponseEntity<>("Administrator already exists.",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id){

        if(adminService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value= "/by-page",method = RequestMethod.GET)
    public ResponseEntity<Page<AdministratorDTO>> getAllAdministrators(Pageable pageable) {
        Page<Administrator> page = adminService.findAll(pageable);
        List<AdministratorDTO> culturalContentCategoryDTOS = toAdminDTOList(page.toList());
        Page<AdministratorDTO> pageCulturalContentCategoryDTOS = new PageImpl<>(culturalContentCategoryDTOS,page.getPageable(),page.getTotalElements());

        return new ResponseEntity<>(pageCulturalContentCategoryDTOS, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<AdministratorDTO>> getAllAdministrators() {
        List<Administrator> admins = adminService.findAll();

        return new ResponseEntity<>(toAdminDTOList(admins), HttpStatus.OK);
    }

    private List<AdministratorDTO> toAdminDTOList(List<Administrator> admins) {
        List<AdministratorDTO> adminDTOs = new ArrayList<>();
        for (Administrator admin: admins) {
            adminDTOs.add(adminMapper.toDto(admin));
        }
        return adminDTOs;
    }

}
