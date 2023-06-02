package com.softwaregrouppro.lowcodedata.web.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.softwaregrouppro.lowcodedata.utils.Result;
import com.softwaregrouppro.lowcodedata.utils.ShortIdUtils;
import com.softwaregrouppro.lowcodedata.web.entity.UserFormDataEntity;
import com.softwaregrouppro.lowcodedata.web.entity.UserFormEntity;
import com.softwaregrouppro.lowcodedata.web.entity.enums.FormStatusEnum;
import com.softwaregrouppro.lowcodedata.web.entity.struct.BaseEntity;
import com.softwaregrouppro.lowcodedata.web.entity.vo.RecycleFormVO;
import com.softwaregrouppro.lowcodedata.web.entity.vo.UserFormStruVO;
import com.softwaregrouppro.lowcodedata.web.request.QueryFormRequest;
import com.softwaregrouppro.lowcodedata.web.service.UserFormDataService;
import com.softwaregrouppro.lowcodedata.web.service.UserFormService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 用户表单
 *
 * @author : smalljop
 * @description : 用户表单
 * @create : 2020-11-18 18:17
 **/
@RequiredArgsConstructor
@RestController
@Slf4j
public class UserFormController {

    @Autowired
    private UserFormService userFormService;
    @Autowired
    private UserFormDataService userFormDataService;


    /**
     * 创建表单
     */
    @PostMapping("/user/form/create")
    public Result createForm(@RequestBody UserFormEntity form, @RequestAttribute Long userId) {
        System.out.println(form.toString());
        form.setFormKey(ShortIdUtils.genId());
        form.setUserId(userId);
//        form.setUserId(1l);
        form.setStatus(FormStatusEnum.CREATE);
        form.setDeleted(false);
        userFormService.save(form);
        return Result.success(form);
    }

    @PostMapping("/user/form/createStru")
    public Result createFormStru(@RequestBody UserFormStruVO userFormStruVO, @RequestAttribute Long userId) {
        System.out.println(userFormStruVO);
        LambdaQueryWrapper<UserFormEntity> queryWrapper=Wrappers.<UserFormEntity>lambdaQuery()
                .eq(UserFormEntity::getFormKey,userFormStruVO.getFormKey());
        UserFormEntity userFormEntity=userFormService.getOne(queryWrapper);
        userFormEntity.setStru(userFormStruVO.getStru());
        System.out.println(userFormEntity);
        userFormService.updateById(userFormEntity);
        return Result.success();
    }

    @PostMapping("/user/form/queryStruById")
    public Result createFormStruById(@RequestParam String formKey,@RequestParam String weChatSignal, @RequestAttribute Long userId) {
        LambdaQueryWrapper<UserFormDataEntity> queryWrapper=Wrappers.<UserFormDataEntity>lambdaQuery()
                .eq(UserFormDataEntity::getFormKey,formKey)
                .eq(UserFormDataEntity::getPin,weChatSignal);
        UserFormDataEntity userFormDataEntity=userFormDataService.getOne(queryWrapper);
        return Result.success(userFormDataEntity.getOriginalData());
    }

    @GetMapping("/user/form/queryStru")
    public Result queryStru(@RequestParam String formKey){
        System.out.println(formKey);
        LambdaQueryWrapper<UserFormEntity> queryWrapper=Wrappers.<UserFormEntity>lambdaQuery()
                .eq(UserFormEntity::getFormKey,formKey);
        UserFormEntity userFormEntity=userFormService.getOne(queryWrapper);
        return Result.success(userFormEntity.getStru());
    }


    @PostMapping("/user/form/logic/delete")
    public Result logicDeleteForm(@RequestBody UserFormEntity request) {
        boolean del = userFormService.update(new UserFormEntity() {{
            setDeleted(Boolean.TRUE);
        }}, Wrappers.<UserFormEntity>lambdaQuery().eq(UserFormEntity::getFormKey, request.getFormKey()));
        return Result.success(del);
    }

    @GetMapping("/user/form/recycle/page")
    public Result queryRecycleForms(@RequestAttribute Long userId, QueryFormRequest.Page request) {
        Page page = userFormService.page(request.toMybatisPage(), Wrappers.<UserFormEntity>lambdaQuery().eq(UserFormEntity::getUserId, userId).eq(UserFormEntity::getDeleted, 1).orderByDesc(BaseEntity::getUpdateTime));
        List<UserFormEntity> records = page.getRecords();
        List<RecycleFormVO> FormVOList = records.stream().map(item -> {
            long count = userFormDataService.count(Wrappers.<UserFormDataEntity>lambdaQuery().eq(UserFormDataEntity::getFormKey, item.getFormKey()));
            return new RecycleFormVO(item.getFormKey(), count, item.getTextName(),item.getCreateTime(), item.getUpdateTime());
        }).collect(Collectors.toList());
        page.setRecords(FormVOList);
        return Result.success(page);
    }

    @PostMapping("/user/form/recycle/restore")
    public Result restoreRecycleForm(@RequestBody UserFormEntity request) {
        boolean flag = userFormService.update(new UserFormEntity() {{
            setDeleted(Boolean.FALSE);
        }}, Wrappers.<UserFormEntity>lambdaQuery().eq(UserFormEntity::getFormKey, request.getFormKey()));
        return Result.success(flag);
    }

    @GetMapping("/user/form/page")
    public Result queryMyForms(@RequestAttribute Long userId, QueryFormRequest.Page request) {
        System.out.println(userId);
        System.out.println(request.toString());
        LambdaQueryWrapper<UserFormEntity> queryWrapper = Wrappers.<UserFormEntity>lambdaQuery()
                .eq(UserFormEntity::getUserId, userId)
                .eq(UserFormEntity::getDeleted, 0)
                .eq(ObjectUtil.isNotNull(request.getStatus()), UserFormEntity::getStatus, request.getStatus())
                .like(StrUtil.isNotBlank(request.getName()), UserFormEntity::getName, request.getName())
                .le(ObjectUtil.isNotNull(request.getEndDateTime()), UserFormEntity::getUpdateTime, request.getEndDateTime())
                .ge(ObjectUtil.isNotNull(request.getBeginDateTime()), UserFormEntity::getUpdateTime, request.getBeginDateTime());
        System.out.println(request.toMybatisPage().toString());
        System.out.println(queryWrapper);
        return Result.success(userFormService.page(request.toMybatisPage(), queryWrapper));
    }



    @PostMapping("/user/form/publish")
    public Result publishForm(@RequestBody UserFormEntity request) {
        UserFormEntity entity = userFormService.getByKey(request.getFormKey());
        entity.setStatus(FormStatusEnum.RELEASE);
        return Result.success(userFormService.updateById(entity));
    }

    @PostMapping("/user/form/stop")
    public Result stopForm(@RequestBody UserFormEntity request) {
        UserFormEntity entity = userFormService.getByKey(request.getFormKey());
        entity.setStatus(FormStatusEnum.STOP);
        return Result.success(userFormService.updateById(entity));
    }

    @GetMapping("/user/form/{key}")
    public Result queryFormByKey(@PathVariable @NotBlank String key) {
        return Result.success(userFormService.getByKey(key));
    }

}
