package com.swg.api;

import com.swg.common.JsonResponse;
import com.swg.service.IFileService;
import com.swg.utils.FastDFSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @description:
 * @author: swg
 * @create: 2022-06-14 16:58
 **/
@RestController
public class fileApi {

    @Resource
    private IFileService fileService;

    @Autowired
    private FastDFSUtil fastDFSUtil;

    @PostMapping("/md5files")
    public JsonResponse<String> getFileMD5(MultipartFile file)throws Exception{
        return fileService.getFileMD5(file);
    }

    @PutMapping("/file-slices")
    public JsonResponse<String> uploadFileBySlices(MultipartFile file,String md5,Integer sliceNo,Integer sliceTotal)throws Exception{
        return fileService.uploadFileBySlices(file,md5,sliceNo,sliceTotal);
    }
}
