package com.huangzb.consumer.application;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "provider" )
public interface ProviderClient {

    @GetMapping("/hi")
    String hi(@RequestParam(value = "name", defaultValue = "huangzb", required = false) String name);
}
