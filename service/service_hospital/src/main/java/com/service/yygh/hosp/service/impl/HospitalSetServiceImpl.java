package com.service.yygh.hosp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.model.yygh.model.hosp.HospitalSet;
import com.service.yygh.hosp.mapper.HospitalSetMapper;
import com.service.yygh.hosp.service.HospitalSetService;
import org.springframework.stereotype.Service;

@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet> implements HospitalSetService {
}
