package com.project.tim7.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.project.tim7.model.Picture;
import com.project.tim7.repository.PictureRepository;

@Service
public class PictureService implements ServiceInterface<Picture> {
	
	@Autowired 
	PictureRepository pictureRepo;

	@Override
	public List<Picture> findAll() {
		return null;
	}

	@Override
	public Picture findOne(int id) {
		return pictureRepo.findById(id).orElse(null);
	}

	@Override
	public boolean saveOne(Picture entity) {
		if (findByPicture(entity.getPicture()) != null)
			return false;
		pictureRepo.save(entity);
		return true;
	}

	@Override
	public boolean saveAll(List<Picture> entities) {
		return false;
	}

	@Override
	public boolean delete(int id) {
		Picture picture = findOne(id);
		if (picture.getPicture().substring(0,25).equals("src/main/resources/images")) {
			File img = new File (picture.getPicture());
			img.delete();
		}
		pictureRepo.delete(picture);
		return true;
	}
	
	public Picture findByPicture(String pictureStr) {
		return pictureRepo.findByPicture(pictureStr);
	}

	@Override
	public Page<Picture> findAll(Pageable pageable) {
		return null;
	}

	@Override
	public Picture update(Picture entity) {
		return pictureRepo.save(entity);
	}
	
	public Picture saveAndReturn(Picture entity) {
		return pictureRepo.save(entity);
	}
	
	public long countPictureInNewsletters(int id) {
		return pictureRepo.countPictureInNewsletters(id);
	}
	
	public Picture upload(MultipartFile multipartFile, int culturalOfferId) {
		String pictureStr = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		String uploadDir = "src/main/resources/images/" + culturalOfferId;
		String path = uploadDir + "/" + pictureStr;
		Picture picture = null;
		try {
			File img = new File(path);
			if (img.exists()) {
				picture = findByPicture(path);
				if (picture != null) 
					return picture;
			}
			saveFile(uploadDir, pictureStr, multipartFile);
		} catch (IOException e) {
			System.out.println("big mistake");
			e.printStackTrace();
		}   
		picture = update(new Picture(path));
		return picture;
	}
	
	
	public void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
         
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);            
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {        
            throw new IOException("Could not save image file: " + fileName, ioe);
        }      
    }

}
