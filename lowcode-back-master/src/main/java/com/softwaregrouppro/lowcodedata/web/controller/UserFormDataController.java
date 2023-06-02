package com.softwaregrouppro.lowcodedata.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.softwaregrouppro.lowcodedata.utils.Result;
import com.softwaregrouppro.lowcodedata.web.entity.UserFormDataEntity;
import com.softwaregrouppro.lowcodedata.web.service.UserFormDataService;
import com.softwaregrouppro.lowcodedata.web.service.UserFormService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/user/form/data")
public class UserFormDataController {

    @Autowired
    private UserFormService userFormService;
    @Autowired
    private UserFormDataService userFormDataService;

//    @GetMapping("/user/form/")

    @PostMapping("/create")
    public Result createFormResult(@RequestBody UserFormDataEntity entity) {
        System.out.println(entity.toString());
        LambdaQueryWrapper<UserFormDataEntity> queryWrapper= Wrappers.<UserFormDataEntity>lambdaQuery()
                .eq(UserFormDataEntity::getFormKey,entity.getFormKey())
                        .eq(UserFormDataEntity::getPin,entity.getPin());
        userFormDataService.saveOrUpdate(entity,queryWrapper);
        System.out.println("success_suceess");
        System.out.println(entity.toString());
        return Result.success();
    }

    @GetMapping("/query")
    public Result queryFormResult(@RequestParam String formKey) {
        LambdaQueryWrapper<UserFormDataEntity> queryWrapper= Wrappers.<UserFormDataEntity>lambdaQuery()
                .eq(UserFormDataEntity::getFormKey,formKey);
        List<UserFormDataEntity> userFormDataEntityList=userFormDataService.getBaseMapper().selectList(queryWrapper);
        List<Map<String, Object>> originalDatas=new ArrayList<>();
        for(int i=0;i<userFormDataEntityList.size();i++){
            originalDatas.add(userFormDataEntityList.get(i).getOriginalData());
        }
        return Result.success(originalDatas);
    }

    @GetMapping("/queryDataById")
    public Result createFormStruById(@RequestParam String formKey,@RequestParam String weChatSignal, @RequestAttribute Long userId) {
        LambdaQueryWrapper<UserFormDataEntity> queryWrapper=Wrappers.<UserFormDataEntity>lambdaQuery()
                .eq(UserFormDataEntity::getFormKey,formKey)
                .eq(UserFormDataEntity::getPin,weChatSignal);
        UserFormDataEntity userFormDataEntity=userFormDataService.getOne(queryWrapper);

        if(userFormDataEntity==null){
            return Result.success();
        }
//        System.out.println(userFormDataEntity.getOriginalData());
        return Result.success(userFormDataEntity.getOriginalData());
    }

    @PostMapping("/setPin")
    public Result setPin(@RequestBody UserFormDataEntity userFormDataEntity) {
        LambdaQueryWrapper<UserFormDataEntity> queryWrapper=Wrappers.<UserFormDataEntity>lambdaQuery()
                .eq(UserFormDataEntity::getFormKey,userFormDataEntity.getFormKey())
                .eq(UserFormDataEntity::getPin,userFormDataEntity.getPin());
        userFormDataService.saveOrUpdate(userFormDataEntity,queryWrapper);

        return Result.success(userFormDataEntity.getOriginalData());
    }
}
