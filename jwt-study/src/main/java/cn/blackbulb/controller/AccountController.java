package cn.blackbulb.controller;

import cn.blackbulb.pojo.request.AccountArg;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangcan
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @RequestMapping("/login")
    public void login(@RequestBody AccountArg accountArg, HttpServletRequest request, HttpServletResponse response) {
        if (accountArg.getAccount().equals("admin") && accountArg.getPassWord().equals("123456")) {
            Map<String,Object> map = new HashMap<>();
            map.put("role","user");
            map.put("ext",System.currentTimeMillis()+120000l);
//            String token = JWT
//            Cookie loginCookie = new Cookie()
        }
    }
}
