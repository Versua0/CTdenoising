package com.common.ctdenoising.service.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class RestTemplateMethods {
    @Autowired
    private RestTemplate restTemplate;

    public void RestTemplatePost(String test_patient) {

        String url = "http://10.21.19.104:5000/predict";
        LinkedMultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.set("test_patient", test_patient);
        try {
            URL url1 = new URL(url);
            HttpURLConnection con =(HttpURLConnection)url1.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type","application/from-data");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //请求
        String result = restTemplate.postForObject(url, request, String.class);
        System.out.println(result);


    }

}
