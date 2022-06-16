package com.swg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swg.common.JsonResponse;
import com.swg.entity.File;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 文件表 服务类
 * </p>
 *
 * @author swg
 * @since 2022-06-14
 */
public interface IFileService extends IService<File> {

    JsonResponse<String> getFileMD5(MultipartFile file)throws Exception;

    JsonResponse<String> uploadFileBySlices(MultipartFile file, String md5, Integer sliceNo, Integer sliceTotal)throws Exception;

}
