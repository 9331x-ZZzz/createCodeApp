package com.lw.creatcodeapp.controller;

import com.lw.creatcodeapp.common.BaseResponse;
import com.lw.creatcodeapp.common.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HealthController {

    @GetMapping("/health")
    public BaseResponse<String> health() {

        return ResultUtils.success("服务运行正常");
    }
}
