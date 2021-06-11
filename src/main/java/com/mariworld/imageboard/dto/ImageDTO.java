package com.mariworld.imageboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URLEncoder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {


    private String uuid;
    private String path;
    private String imgName;

    public String getImagePath(){
        String uri="";
        try{
            uri  = URLEncoder.encode(path+ "/"+uuid+"_"+imgName ,"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return uri;
    }

    public String getThumbnailPath(){
        String uri="";
        try{
            uri  = URLEncoder.encode(path+ "/s_"+uuid+"_"+imgName ,"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return uri;
    }
}
