package com.project.tim7.helper;

import com.project.tim7.model.Picture;
import com.project.tim7.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;

public class PictureMapper implements MapperInterface<Picture, String> {

    @Autowired
    PictureService pictureService;

    @Override
    public Picture toEntity(String dto) {
        if(pictureService.findByPicture(dto) == null){
            return new Picture(dto);
        }
        return pictureService.findByPicture(dto);

    }

    @Override
    public String toDto(Picture entity) {
        return entity.getPicture();
    }

}
