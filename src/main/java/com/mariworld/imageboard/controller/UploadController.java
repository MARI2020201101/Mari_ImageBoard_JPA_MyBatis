package com.mariworld.imageboard.controller;

import com.mariworld.imageboard.dto.ImageDTO;
import com.mariworld.imageboard.entity.Image;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class UploadController {

    @Value("${com.mariworld.upload.path}")
    private String uploadPath;

    @PostMapping("/uploadFile")
    public ResponseEntity<List<ImageDTO>> upload(MultipartFile[] uploadFiles){
        log.info("----------------------upload------------------------------");
        log.info(uploadFiles.toString());

        List<ImageDTO> imageDTOList = new ArrayList<>();
        for(MultipartFile uploadFile : uploadFiles){

            if(!(uploadFile.getContentType().startsWith("image"))){
                log.info("this is not image file");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            String originalName = uploadFile.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String folderPath = makeFolderPath();
            String saveFileName = uploadPath + File.separator + folderPath + File.separator
                    + uuid + "_" + originalName;

            Path savePath = Paths.get(saveFileName);


            try{
                uploadFile.transferTo(savePath);

                String thumbnailSaveName = uploadPath + File.separator + folderPath +
                        File.separator + "s_" + uuid + "_" + originalName;
                File thumbnailFile = new File(thumbnailSaveName);
                Thumbnailator.createThumbnail(savePath.toFile(),thumbnailFile,150,150);

            }catch(Exception e){
                e.printStackTrace();
            }

            ImageDTO imageDTO = ImageDTO.builder()
                    .imgName(originalName)
                    .uuid(uuid)
                    .path(folderPath)
                    .build();

            imageDTOList.add(imageDTO);

        }
        return new ResponseEntity<>(imageDTOList,HttpStatus.OK);
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> displayFile(String fileName) {
        try{
            String srcFile = URLDecoder.decode(fileName,"UTF-8");
            File file = new File(uploadPath + File.separator + srcFile);
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", Files.probeContentType(file.toPath()));

            return new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/removeFile")
    public ResponseEntity<Boolean> removeFile(String filePath){
        try{
            String srcFilePath = URLDecoder.decode(filePath,"UTF-8");
            File file = new File(uploadPath+File.separator + srcFilePath);
            File thumbnailFile = new File(file.getParent(), "s_"+file.getName());
            file.delete();
            thumbnailFile.delete();
            return new ResponseEntity<>(true, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    private String makeFolderPath() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("//", File.separator);

        File uploadPathFolder = new File(uploadPath,folderPath);
        if(uploadPathFolder.exists()==false){
            uploadPathFolder.mkdirs();
        }
        return folderPath;

    }
}
