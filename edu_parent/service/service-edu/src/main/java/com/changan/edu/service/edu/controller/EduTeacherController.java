package com.changan.edu.service.edu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import org.apache.commons.lang3.StringUtils;
import com.changan.edu.service.base.exceptionhandler.MyException;
import org.springframework.util.StringUtils;
//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.changan.edu.commonutils.RestResult;
import com.changan.edu.service.edu.model.EduTeacher;
import com.changan.edu.service.edu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    // 访问地址： http://localhost:8088/eduservice/teacher/findAll
    // 把 service 注入
    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation(value = "查询所有讲师列表")
    @GetMapping("findAll")   // 访问地址： http://localhost:8088/eduservice/teacher/findAll
    public RestResult findAll()
    {
        try
        {
            int a = 10/0;
        }
        catch (Exception e)
        {
            throw new MyException(20001, "执行了自定义异常处理...");
        }
        List<EduTeacher> list = teacherService.list(null); // 调用service的方法实现查询所有的操作
        return RestResult.ok().data("items",list);
    }

    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")    // id 需要通过路径传递
    public RestResult removeTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if(flag)
            return RestResult.ok();
        else
            return RestResult.error();
    }

    @ApiOperation(value = "分页查询讲师")
    @GetMapping("pageTeacher/{current}/{limit}")
    public RestResult pageListTeacher(
            @ApiParam(name = "current", value = "当前页", required = true)
            @PathVariable long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable long limit ){

        // 创建 Page 对象
        Page<EduTeacher> pageTeacher = new Page<EduTeacher>(current, limit);
        /*
         * 调用方法实现分页
         * 调用方法时，底层封装，将分页所有的数据封装到 pageTeacher 对象中
         */
        teacherService.page(pageTeacher, null);
        long total = pageTeacher.getTotal();  // 获取总记录数
        List<EduTeacher> list = pageTeacher.getRecords();  // 数据 list 集合
        return RestResult.ok().data("total", total).data("rows", list);
    }

    @ApiOperation(value = "多条件组合查询带分页")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public RestResult pageTeacherCondition(
            @ApiParam(name = "current", value = "当前页", required = true)
            @PathVariable long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable long limit,
            @RequestBody(required = false) com.xzy.edu.service.edu.model.vo.TeacherQuery teacherQuery) {

        // 创建 Page 对象
        Page<EduTeacher> pageTeacher = new Page<EduTeacher>(current, limit);
        // 创建条件构造器
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        /*
         * 多条件组合查询
         *    获取条件
         *   判断条件是否为空，若不为空，拼接条件
         */
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name))
            wrapper.like("name", name);  // 构造条件
        if(!StringUtils.isEmpty(level))
            wrapper.eq("level", level);  // 构造条件
        if(!StringUtils.isEmpty(begin))
            wrapper.ge("gmt_create", begin);  // 构造条件
        if(!StringUtils.isEmpty(end))
            wrapper.le("gmt_create", end);  // 构造条件

        // 调用方法实现分页
        teacherService.page(pageTeacher, wrapper);
        long total = pageTeacher.getTotal();  // 获取总记录数
        List<EduTeacher> list = pageTeacher.getRecords();  // 数据 list 集合
        return RestResult.ok().data("total", total).data("rows", list);
    }

    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public RestResult addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        if(save) {
            return RestResult.ok();
        } else {
            return RestResult.error();
        }
    }

    @ApiOperation(value = "根据讲师id进行查询")
    @GetMapping("getTeacher/{id}")
    public RestResult getTeacher(@PathVariable String id) {
        EduTeacher eduTeacher = teacherService.getById(id);
        return RestResult.ok().data("teacher",eduTeacher);
    }

    @ApiOperation(value = "讲师修改")
    @PostMapping("updateTeacher")
    public RestResult updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.updateById(eduTeacher);
        if(flag) {
            return RestResult.ok();
        } else {
            return RestResult.error();
        }
    }
}

