package com.qbaoio.util.http;

import com.qbaoio.exception.QtumException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
@ConfigurationProperties(prefix = "qtum")
public class ReqHttpUtil {

    private String server;
    private String username;
    private String password;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //设置请求头
    private HttpHeaders getReqHeads(){

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        //授权
        String plainCreds = username + ":" + password;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        headers.add("Authorization", "Basic " + base64Creds);
        return headers;

    }


    public <T> T callHttpReq(String methodName, Object[] params, Class<T> cls)throws Exception {
        String resultJson = (String)this.callHttpReq(methodName, params);
        ObjectMapper mapper = new ObjectMapper();
        T o = mapper.readValue(resultJson, cls);
        return o;

    }

    public Object callHttpReq(String methodName, Object[] params){

        HttpHeaders headers=this.getReqHeads();

        RestTemplate rest = new RestTemplate();
        ReqHttpEntity reqHttpEntity = new ReqHttpEntity();
        reqHttpEntity.setMethod(methodName);
        reqHttpEntity.setParams(params);
        //对象转json
        ObjectMapper mapper = new ObjectMapper();
        RespHttpEntity entity;
        try {
            String json = mapper.writeValueAsString(reqHttpEntity);
            //设置请求对象
            HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
            //发送请求
            ResponseEntity<String> responseEntity = rest.exchange(server, HttpMethod.POST, requestEntity, String.class);

            String responseBody = responseEntity.getBody();
            entity = mapper.readValue(responseBody, RespHttpEntity.class);
            return entity.getResult();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (HttpClientErrorException e) {
            String responseBody = e.getResponseBodyAsString();
            try {
                entity = mapper.readValue(responseBody, RespHttpEntity.class);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            throw(new QtumException((Integer) entity.getError().get("code"), (String) entity.getError().get("message")));
        }

    }


    public Object callHttpReq1(String url,String methodName, Object[] params){

        HttpHeaders headers=this.getReqHeads();

        RestTemplate rest = new RestTemplate();
        ReqHttpEntity reqHttpEntity = new ReqHttpEntity();
        reqHttpEntity.setMethod(methodName);
        reqHttpEntity.setParams(params);
        //对象转json
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(reqHttpEntity);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        //设置请求对象
        HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
        //发送请求
        ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.GET,null, String.class);
        String responseBody = responseEntity.getBody();
        RespHttpEntity entity = null;
        try {
            entity = mapper.readValue(responseBody, RespHttpEntity.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return entity.getResult();

    }


}
