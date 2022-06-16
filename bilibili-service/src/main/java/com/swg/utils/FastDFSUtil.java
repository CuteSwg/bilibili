package com.swg.utils;

import cn.hutool.core.util.StrUtil;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.AppendFileStorageClient;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.swg.common.ConditionException;
import com.swg.constant.FileConstant;
import com.swg.constant.UserConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @description: 文件操作工具类
 * @author: swg
 * @create: 2022-06-14 14:54
 **/
@Component
public class FastDFSUtil {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private AppendFileStorageClient appendFileStorageClient;

    @Value("${fdfs.http.storage-addr}")
    private String httpFdfsStorageAddr;

    private String getFileType(MultipartFile multipartFile){
        if (multipartFile == null){
            throw new ConditionException("非法文件");
        }
        String fileName = multipartFile.getOriginalFilename();
        int index = fileName.lastIndexOf(".");
        return fileName.substring(index + 1);
    }

    public String uploadCommonFile(MultipartFile file)throws Exception{
        HashSet<MetaData> metaData = new HashSet<>();
        String fileType = getFileType(file);
        StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), fileType, metaData);
        return storePath.getPath();
    }

    public String uploadCommonFile(File file, String fileType) throws Exception {
        Set<MetaData> metaDataSet = new HashSet<>();
        StorePath storePath = fastFileStorageClient.uploadFile(new FileInputStream(file),
                file.length(), fileType, metaDataSet);
        return storePath.getPath();
    }

    //上传可分片文件
    private String uploadAppenderFile(MultipartFile file) throws Exception{
        String fileType = getFileType(file);
        StorePath storePath = appendFileStorageClient.uploadAppenderFile(FileConstant.DEFAULT_GROUP, file.getInputStream(), file.getSize(), fileType);
        return storePath.getPath();
    }
    //修改可分片文件
    private void modifyAppenderFile(MultipartFile file,String path,long offset)throws Exception{
        appendFileStorageClient.modifyFile(FileConstant.DEFAULT_GROUP,path, file.getInputStream(), file.getSize(),offset);
    }

    /**
     * 通过分片的方式上传文件
     * @param file
     * @param fileMD5
     * @param sliceNo
     * @param sliceTotalNo
     * @return
     * @throws Exception
     */
    public String uploadFileBySlices(MultipartFile file,String fileMD5,Integer sliceNo,Integer sliceTotalNo)throws Exception{
        if (file == null || sliceNo == null || sliceTotalNo == null){
            throw new ConditionException("参数异常");
        }
        //记录文件存储的路径位置
        String pathkey = FileConstant.PATH_KEY + fileMD5;
        //记录文件上传的大小，每次的偏移量应该从哪里开始
        String uploadedSizeKey = FileConstant.UPLOADED_SIZE_KEY + fileMD5;
        //记录文件分片数，用于判断是否上传完毕
        String uploadedNoKey = FileConstant.UPLOADED_NO_KEY + fileMD5;
        if (sliceNo == 1){
            //第一次上传
            String filePath = uploadAppenderFile(file);
            redisTemplate.opsForValue().set(uploadedSizeKey,String.valueOf(file.getSize()));
            redisTemplate.opsForValue().set(pathkey,filePath);
            redisTemplate.opsForValue().set(uploadedNoKey,"1");
        }else {
            String uploadedSize = redisTemplate.opsForValue().get(uploadedSizeKey);
            modifyAppenderFile(file,pathkey,Long.valueOf(uploadedSize));
            Long currentSize = Long.valueOf(uploadedSize) + file.getSize();
            redisTemplate.opsForValue().set(uploadedSizeKey,String.valueOf(currentSize));
        }
        String uploadedNoStr = redisTemplate.opsForValue().get(uploadedNoKey);
        Integer uploadedNo = Integer.valueOf(uploadedNoStr);
        String resultPath = "";
        if(uploadedNo.equals(sliceTotalNo)){
            resultPath = redisTemplate.opsForValue().get(pathkey);
            List<String> keyList = Arrays.asList(uploadedNoKey, pathkey, uploadedSizeKey);
            redisTemplate.delete(keyList);
        }
        return resultPath;

    }




}
