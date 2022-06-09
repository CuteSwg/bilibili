package com.swg.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swg.common.ConditionException;
import com.swg.common.JsonResponse;
import com.swg.constant.UserConstant;
import com.swg.dao.UserInfoMapper;
import com.swg.dao.UserMapper;
import com.swg.entity.User;
import com.swg.entity.UserInfo;
import com.swg.service.UserService;
import com.swg.utils.MD5Util;
import com.swg.utils.RSAUtil;
import com.swg.utils.TokenUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @author swg
 * @Date: 2022/6/9 10:53
 * @Description:
 */
@Service
public class IUserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public JsonResponse<String> addUser(User user) {
        User dbUser = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getPhone, user.getPhone()));
        if (Objects.nonNull(dbUser)){
            throw new ConditionException("该手机号已经注册");
        }
        Date now = new Date();
        String salt = String.valueOf(now.getTime());
        //加密过的密码
        String password = user.getPassword();
        //得到数据库中存储的md5加密密码
        String dbPassword = passwordConvert(password,salt,"UTF-8");
        user.setPassword(dbPassword);
        user.setSalt(salt);
        user.setId(IdWorker.getId());
        userMapper.insert(user);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setNick(UserConstant.DEFAULT_NICK);
        userInfo.setBirth(UserConstant.DEFAULT_BIRTH);
        userInfo.setGender(UserConstant.GENDER_MALE);
        userInfoMapper.insert(userInfo);
        return JsonResponse.success();
    }

    @Override
    public JsonResponse<String> login(User user) throws Exception{
        User dbUser = userMapper.getUserByPhoneOrEmail(user.getPhone(),user.getEmail());
        if (dbUser == null){
            throw new ConditionException("当前用户不存在！");
        }

        String password = user.getPassword();
        String salt = dbUser.getSalt();
        String md5Password = passwordConvert(password, salt, "UTF-8");

        if (!md5Password.equals(dbUser.getPassword())){
            throw new ConditionException("密码错误!");
        }
        String token = TokenUtil.generateToken(user.getId());
        return new JsonResponse<String>(token);
    }

    @Override
    public JsonResponse<String> updateUser(User user) {
        User dbUser = userMapper.selectById(user.getId());
        if (dbUser == null){
            throw new ConditionException("用户不存在");
        }
        if (StrUtil.isNotEmpty(user.getPassword())){
            String MD5Password = passwordConvert(dbUser.getPassword(), dbUser.getSalt(), "UTF-8");
            user.setPassword(MD5Password);
        }
        userMapper.updateUserById(user);
        return JsonResponse.success();
    }

    /**
     * 将加密的密码解密过再通过md5加密
     * @param password 加密密码
     * @param salt 盐值
     * @param charset 编码
     * @return 加密密码
     */
    private String passwordConvert(String password,String salt,String charset){
        String realPassword;
        try {
            //解密出真实密码
            realPassword = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("密码解密失败");
        }
        return MD5Util.sign(realPassword, salt, charset);
    }
}
