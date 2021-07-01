package com.service.yygh.hosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.model.yygh.model.hosp.HospitalSet;
import com.model.yygh.vo.hosp.HospitalSetQueryVo;
import com.service.yygh.common.utils.MD5;
import com.service.yygh.hosp.service.HospitalSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import result.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("hosp/hospitalSet")
public class HospitalSetController {
    @Autowired
    private HospitalSetService hospitalSetService;

    @GetMapping("findAll")
    public List<HospitalSet> findAllHospitalSet(){
        List<HospitalSet> list = hospitalSetService.list();
        return list;
    }

    //条件查询
    @GetMapping("findPageHospSet/{current}/{limit}")
    public Result findpageHospSet(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo){
        //创建page对象，传递当前页，每页记录数
        Page<HospitalSet> page = new Page<>(current,limit);
        //构建条件
        QueryWrapper<HospitalSet> queryWrapper = new QueryWrapper<>();
        /*queryWrapper.like("hosname", hospitalSetQueryVo.getHosname());
        queryWrapper.eq("hoscode", hospitalSetQueryVo.getHoscode());*/

        //调用方法实现分页查询
        Page<HospitalSet> pageHospSet = hospitalSetService.page(page,queryWrapper);

        return Result.ok(pageHospSet);
    }

    //添加医院设置
    @PostMapping("saveHospitalSet")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet){
        //设置状态
        hospitalSet.setStatus(1);
        //签名密钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+random.nextInt(1000)));

        //调用service
        boolean save = hospitalSetService.save(hospitalSet);
        return save ? Result.ok() : Result.fail();
    }

    //发送签名密钥
    @PostMapping("sendKey/{id}")
    public Result lockHospitalSet(@PathVariable long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);

        String signKey = hospitalSet.getSignKey();
        String code = hospitalSet.getHoscode();
        //Todo 发送短信
        return Result.ok();
    }

    @PostMapping("file")
    public Result upload(HttpServletRequest request){
        MultipartRequest multiR = (MultipartRequest) request;
        MultipartFile mpf = multiR.getFile("file");
        MultipartFile mpfb = multiR.getFile("blod");
        String b = request.getParameter("blod");
        return Result.ok();
    }
}
