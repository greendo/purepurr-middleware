package com.purepurr.middlewareservice.controller;

import com.purepurr.middlewareservice.MiddlewareServiceApplication;
import org.apache.curator.x.discovery.ServiceInstance;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.URL;

@RestController
public class TestController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "hello, nigga, i'm middleware";
    }

    @RequestMapping(value = "/test-api", method = RequestMethod.GET)
    public String testApi() {
        try {
            ServiceInstance instance = MiddlewareServiceApplication.serviceProvider.getInstance();
            String address = instance.buildUriSpec();
            InputStream in = new URL(address + "/test").openStream();
            return IOUtils.toString(in);
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }
}
