package com.andsotra.microservice.client.provider;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.andsotra.microservice.client.domain.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;

@Component
public class FileStorageProvider {
	
	@Autowired
	RestTemplate restClient;
	
	@Autowired
	Gson gson;
	
	@Value("${url-file-storage}")
	String urlFileStorage;
	
	public Response createImage(String base64image) throws JsonProcessingException {
		Map<String, String> map = new HashMap<>();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		map.put("fileBase64", base64image);
		map.put("fileName", UUID.randomUUID().toString()+".jpg");
		map.put("id", "");
		String json = gson.toJson(map);
		HttpEntity<String> httpEntity = new HttpEntity<>(json,headers);
		ResponseEntity<String> responseEntity = restClient.postForEntity( urlFileStorage, httpEntity,String.class);
		return gson.fromJson(responseEntity.getBody(),Response.class);
	
	}
	
}
