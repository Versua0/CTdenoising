package com.common.ctdenoising.service.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateMethods {
    @Autowired
    private RestTemplate restTemplate;

    public void RestTemplatePost(String url,String test_patient) {
        //String url = "http://192.168.161.1:5000/predict";
        LinkedMultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.set("test_patient", test_patient);

        //请求
        String result = restTemplate.postForObject(url, request, String.class);
        System.out.println(result);


    }

}
