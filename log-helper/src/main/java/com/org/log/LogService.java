package com.org.log;

import org.springframework.stereotype.Service;

@Service
public class LogService {

    @SystemServiceLog(description = "日志")
    public String log(){
        System.out.println("execute .....");

        return "result";
    }
}
