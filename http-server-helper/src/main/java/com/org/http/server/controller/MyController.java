package com.org.http.server.controller;

import com.sise.itree.common.BaseController;
import com.sise.itree.common.annotation.ControllerMapping;
import com.sise.itree.core.handle.response.BaseResponse;
import com.sise.itree.model.ControllerRequest;


@ControllerMapping(url = "/myController")
public class MyController implements BaseController {
    public BaseResponse doGet(ControllerRequest controllerRequest) {
        String userName=(String)controllerRequest.getParameter("userName");
        System.out.println(userName);
        return new BaseResponse(1,userName);
    }

    public BaseResponse doPost(ControllerRequest controllerRequest) {
        return null;
    }
}
