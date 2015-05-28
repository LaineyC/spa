package com.lite.app.framework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SignController extends BaseController {

    @ResponseBody
    @RequestMapping(params="method=application.sign.in")
    public Object signIn() {
        return null;
    }

    @ResponseBody
    @RequestMapping(params="method=application.sign.out")
    public Object signOut() {
        return null;
    }
}
