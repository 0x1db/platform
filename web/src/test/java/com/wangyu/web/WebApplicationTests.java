package com.wangyu.web;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.google.common.collect.Maps;
import com.platform.common.enums.StatusTypeEnum;
import com.platform.common.enums.UserTypeEnum;
import com.wangyu.web.dao.BaseUserInfoMapper;
import com.wangyu.web.domain.BaseUserInfo;
import com.wangyu.web.domain.SysUser;
import com.wangyu.web.service.SysUserService;
import org.checkerframework.checker.units.qual.Temperature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
public class WebApplicationTests {

    @Test
    public void contextLoads() {
    }

    public static void main() {
        int aHundredMillion = 10000000;

        Map<Integer, Integer> map = Maps.newHashMap();
        long s1 = System.currentTimeMillis();
        for (int i = 0; i < aHundredMillion; i++) {
            map.put(i, i);
        }
        long s2 = System.currentTimeMillis();
        System.out.println("未初始化容量，耗时 ： " + (s2 - s1));
        Map<Integer, Integer> map1 = Maps.newHashMapWithExpectedSize(aHundredMillion / 2);
        long s5 = System.currentTimeMillis();
        for (int i = 0; i < aHundredMillion; i++) {
            map1.put(i, i);
        }
        long s6 = System.currentTimeMillis();
        System.out.println("初始化容量" + aHundredMillion / 2 + "，耗时 ： " + (s6 - s5));
        Map<Integer, Integer> map2 = Maps.newHashMapWithExpectedSize(aHundredMillion);
        long s3 = System.currentTimeMillis();
        for (int i = 0; i < aHundredMillion; i++) {
            map2.put(i, i);
        }
        long s4 = System.currentTimeMillis();
        System.out.println("初始化容量为" + aHundredMillion + "，耗时 ： " + (s4 - s3));
        Map<Integer, Integer> map3 = Maps.newHashMapWithExpectedSize(16);
        long s7 = System.currentTimeMillis();
        for (int i = 0; i < aHundredMillion; i++) {
            map3.put(i, i);
        }
        long s8 = System.currentTimeMillis();
        System.out.println("初始化容量为16，耗时 ： " + (s8 - s7));
    }

    @Autowired
    private BaseUserInfoMapper baseUserInfoMapper;

    @Test
    public void insert() {
        BaseUserInfo userInfo = new BaseUserInfo();
        userInfo.setUsername("wangyu");
        userInfo.setPassword("123456");
        userInfo.setType(UserTypeEnum.MANAGE_USER);
        userInfo.setStatus(StatusTypeEnum.NORMAL);
        System.out.println(UserTypeEnum.MANAGE_USER);
        baseUserInfoMapper.insert(userInfo);
    }

    @Test
    public void get() {
        BaseUserInfo userInfo = baseUserInfoMapper.get(1001);
        System.out.println(userInfo.getRemark());
        System.out.println(userInfo.getUsername());
        System.out.println(userInfo.getPassword());
        System.out.println(userInfo.getStatus());
        System.out.println(userInfo.getType());

        List<BaseUserInfo> list = baseUserInfoMapper.findList(new HashMap<>());
        for (BaseUserInfo userInfo1 : list) {
            System.out.println(userInfo1.getRemark());
            System.out.println(userInfo1.getUsername());
            System.out.println(userInfo1.getPassword());
            System.out.println(userInfo1.getStatus());
            System.out.println(userInfo1.getType());
        }
    }

    @Autowired
    private SysUserService sysUserService;

    @Test
    public void testSysUserInsert() {
        for (int i = 0; i <= 1000; i++) {
            BaseUserInfo userInfo = new BaseUserInfo();
            userInfo.setUsername("wangyu" + i);
            userInfo.setPassword("123456");
            userInfo.setType(UserTypeEnum.MANAGE_USER);
            userInfo.setStatus(StatusTypeEnum.NORMAL);

            sysUserService.insert(userInfo);

        }
    }
}
