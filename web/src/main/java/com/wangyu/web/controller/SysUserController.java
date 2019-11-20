package com.wangyu.web.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.platform.core.entity.ResponseModel;
import com.platform.core.web.controller.BaseController;
import com.wangyu.web.domain.SysUser;
import com.wangyu.web.dto.SysUserDTO;
import com.wangyu.web.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 后台用户控制器
 *
 * @author wangyu
 * @date 2019/11/6 0:19
 */
@CrossOrigin
@RestController
@RequestMapping("/sys_user/")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 新增后台用户
     *
     * @param sysUserDTO
     * @return
     */
    @PostMapping("/")
    public ResponseModel addUser(@RequestBody SysUserDTO sysUserDTO) {
        sysUserService.insert(sysUserDTO);
        return this.buildSuccessResult();
    }

    /**
     * 条件分页查询
     *
     * @param pageSize
     * @param pageNum
     * @param baseUserId
     * @return
     */
    @GetMapping("/paging")
    public ResponseModel getPages(@RequestParam("pageSize") Integer pageSize, @RequestParam("pageNum") Integer pageNum, @RequestParam("baseUserId") String baseUserId) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("pageNum", pageNum);
        params.put("pageSize", pageSize);
        params.put("baseUserId", baseUserId);
        PageInfo<SysUser> pages = sysUserService.findPages(params);
        return this.buildSuccessResult(pages);
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @DeleteMapping("/")
    public ResponseModel deleteUser(@RequestParam("id") String id) {
        if (StringUtils.isEmpty(id)) {

        }
        sysUserService.delete(id);
        return this.buildSuccessResult();
    }
}
