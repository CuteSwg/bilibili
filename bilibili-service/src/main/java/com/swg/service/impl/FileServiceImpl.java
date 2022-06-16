package com.swg.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swg.common.JsonResponse;
import com.swg.dao.FileMapper;
import com.swg.entity.File;
import com.swg.service.IFileService;
import com.swg.utils.FastDFSUtil;
import com.swg.utils.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * <p>
 * 文件表 服务实现类
 * </p>
 *
 * @author swg
 * @since 2022-06-14
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {

    @Resource
    private FileMapper fileMapper;

    @Resource
    private FastDFSUtil fastDFSUtil;

    @Override
    public JsonResponse<String> getFileMD5(MultipartFile file) throws Exception{
        String fileMD5 = MD5Util.getFileMD5(file);
        return JsonResponse.success(fileMD5);
    }

    @Override
    public JsonResponse<String> uploadFileBySlices(MultipartFile file, String md5, Integer sliceNo, Integer sliceTotal) throws Exception{
        if (md5 != null){
            File md5File = fileMapper.selectOne(new QueryWrapper<File>().lambda().eq(File::getMd5, md5));
            return JsonResponse.success(md5File.getUrl());
        }
        String resultPath = fastDFSUtil.uploadFileBySlices(file, md5, sliceNo, sliceTotal);
        if (StrUtil.isNotBlank(resultPath)){
            File dbFile = new File();
            dbFile.setUrl(resultPath);
            dbFile.setMd5(md5);
            fileMapper.insert(dbFile);
        }
        return JsonResponse.success(resultPath);
    }
}
