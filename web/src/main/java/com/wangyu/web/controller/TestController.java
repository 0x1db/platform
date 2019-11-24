package com.wangyu.web.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangyu
 * @date 2019/11/24 20:56
 */
@RestController
@RequestMapping("/test")
public class TestController {

  @GetMapping
  public String listTasks() {
    return "任务列表";
  }

  @PostMapping
  public String newTasks() {
    return "创建了一个新的任务";
  }

  @PutMapping("/{taskId}")
  public String updateTasks(@PathVariable("taskId") Integer id) {
    return "更新了一下id为:" + id + "的任务";
  }

  @DeleteMapping("/{taskId}")
  public String deleteTasks(@PathVariable("taskId") Integer id) {
    return "删除了id为:" + id + "的任务";
  }
}
