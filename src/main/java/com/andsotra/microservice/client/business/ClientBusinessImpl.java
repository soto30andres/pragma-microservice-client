package com.andsotra.microservice.client.business;

import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.andsotra.microservice.client.domain.ClientDto;
import com.andsotra.microservice.client.domain.DocumentTypeDto;
import com.andsotra.microservice.client.domain.RequestDto;
import com.andsotra.microservice.client.domain.Response;
import com.andsotra.microservice.client.model.Client;
import com.andsotra.microservice.client.provider.FileStorageProvider;
import com.andsotra.microservice.client.repository.ClientRepository;
import com.andsotra.microservice.client.repository.DocumentTypeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;


@Service
public class ClientBusinessImpl implements ClientBusiness{
	private final String urlFileStorage = "http://localhost:8082/dev/api/v1/file";

    private static final Logger logger = LogManager.getLogger(ClientBusinessImpl.class);
	
	@Autowired
	ClientRepository clientRepository;

	@Autowired
	DocumentTypeRepository documentTypeRepository;

	@Autowired
	RestTemplate restClient;

	@Autowired
	FileStorageProvider fileStorageProvider;
	
	@Override
	public List<ClientDto> listClients() {
		
		List<ClientDto> listClients = fromEntityToDto(clientRepository.findAll());
		return listClients;
	}

	@Override
	public ClientDto createClient(ClientDto clientDto) {
		
		Client client = new Client();
		client.setFirstName(clientDto.getFirstName());
		client.setSecondName(clientDto.getSecondName());
		client.setBirthDay(clientDto.getBirthDay());
		client.setDocumentNumber(clientDto.getDocumentNumber());
		client.setDocumentType(documentTypeRepository.findByShortName(clientDto.getShortNameDocumentType()));
		
		try {	
			Response response = fileStorageProvider.createImage(clientDto.getBase64Image());
			Map<String, String> responseMap = (Map<String, String>) response.getData();
			client.setImageId(responseMap.get("id"));
		} catch (ParseException | IOException e) {
			logger.error("Error!", e);
			new Exception(e);
		}
		
		client = clientRepository.save(client);
		
		clientDto.setId(client.getId());
		clientDto.setImageId(client.getImageId());
		
		return clientDto;
		 
	}

	@Override
	public ClientDto updateClient(ClientDto clientDto) {
		Client client = new Client();
		client.setId(clientDto.getId());
		client.setFirstName(clientDto.getFirstName());
		client.setSecondName(clientDto.getSecondName());
		client.setBirthDay(clientDto.getBirthDay());
		client.setDocumentNumber(clientDto.getDocumentNumber());
		client.setDocumentType(documentTypeRepository.findByShortName(clientDto.getShortNameDocumentType()));
		try {
			Response responseObject = fileStorageProvider.createImage(clientDto.getBase64Image());
			Map<String, String> responseMap = (Map<String, String>) responseObject.getData();
			client.setImageId(responseMap.get("id"));
		} catch (ParseException | IOException e) {
			logger.error("Error!", e);
			new Exception(e);
		}
		client = clientRepository.save(client);
		
		
		return clientDto;
	}
	
	@Override
	public boolean deleteClient(int id) {
		
		Client client = clientRepository.findById(id).orElse(null);
		
		boolean deleted = false;
		if(client!=null){
			clientRepository.delete(client);
			deleted = true;
		}
		
		return deleted;
	}
	
	@Override
	public List<ClientDto> listClients(String documentNumber, String documentTypeShortName) {
		
		List<Client> listClients = clientRepository.findByDocumentNumberAndDocumentType_shortName(documentNumber, documentTypeShortName);
    	return fromEntityToDto(listClients);
	}

	@Override
	public List<ClientDto> listClients(int olderThan) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.HOUR_OF_DAY, 0);
    	calendar.set(Calendar.MINUTE, 0);
    	calendar.set(Calendar.SECOND, 0);
    	calendar.add(Calendar.YEAR, -olderThan);
    	List<Client> clients = clientRepository.findClientsViejos(calendar.getTime());
    	return fromEntityToDto(clients);
	}
	
	private List<ClientDto> fromEntityToDto(List<Client> clients){
		List<ClientDto> listClients = clients
				.stream()
				.map((client)-> {
					Response response = restClient.getForObject(urlFileStorage+"//"+client.getImageId(), Response.class);
					Map<String, String> map = (Map<String, String>) response.getData();
					ClientDto dto = new ClientDto(client.getId(), 
						client.getFirstName(), 
						client.getSecondName(), 
						client.getBirthDay(), 
						client.getImageId(),
						client.getDocumentNumber(),
						client.getDocumentType().getShortName(),
						map.get("fileBase64"));
					
					return dto;
				}) 
				.collect(Collectors.toList());
		
		return listClients;
		
	} 
	

}
