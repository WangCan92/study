package cn.blackbulb.controller;

import cn.blackbulb.pojo.common.BaseResult;
import cn.blackbulb.pojo.request.TestParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/param")
public class ParamValidateController {

    @PostMapping("/validate")
    public BaseResult validate(@RequestBody @Validated TestParam testParam){
        return BaseResult.success();
    }
}
