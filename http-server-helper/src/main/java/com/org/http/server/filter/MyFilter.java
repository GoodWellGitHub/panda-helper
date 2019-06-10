package com.org.http.server.filter;

import com.sise.itree.common.BaseFilter;
import com.sise.itree.common.annotation.Filter;
import com.sise.itree.model.ControllerRequest;

@Filter(order = 1)
public class MyFilter implements BaseFilter {
    public void beforeFilter(ControllerRequest controllerRequest) {
        System.out.println("Before");
    }

    public void afterFilter(ControllerRequest controllerRequest) {
        System.out.println("After");
    }
}
