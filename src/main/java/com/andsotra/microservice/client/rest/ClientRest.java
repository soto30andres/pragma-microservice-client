package com.andsotra.microservice.client.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andsotra.microservice.client.business.ClientBusiness;
import com.andsotra.microservice.client.domain.ClientDto;
import com.andsotra.microservice.client.domain.Response;

@RestController
@RequestMapping("dev/api/v1/client")
public class ClientRest {
	
	@Autowired
	ClientBusiness clientBusiness;
	
	
	@GetMapping
	public ResponseEntity<Response> listClient(@RequestParam(name = "documentType", required = false) String documentType, 
			@RequestParam(name = "documentNumber", required = false) String documentNumber,
			@RequestParam(name = "olderThan", required = false) Integer olderThan) {
		Map<String, List<ClientDto>> data = new HashMap<String, List<ClientDto>>();
		
		Response response = null;
		if((!Objects.isNull(documentType) && !documentType.isEmpty()) 
				&& !Objects.isNull(documentNumber) && !documentNumber.isEmpty()) {
			data.put("data", clientBusiness.listClients(documentNumber, documentType));
		}else if(!Objects.isNull(olderThan) && olderThan>0){
			data.put("data", clientBusiness.listClients(olderThan));
		}else {
			data.put("data", clientBusiness.listClients());
		}

		response = new Response("OK", 200, data);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@PostMapping	
	public ResponseEntity<Response> saveClient(@RequestBody ClientDto clientDto) {
		Map<String, ClientDto> data = new HashMap<String, ClientDto>();
		data.put("data", clientBusiness.createClient(clientDto));
		Response response = new Response("OK", 200, data);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<Response> updateClient(@RequestBody ClientDto clientDto) {
		Map<String, ClientDto> data = new HashMap<String, ClientDto>();
		data.put("data", clientBusiness.updateClient(clientDto));
		Response response = new Response("OK", 200, data);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Response> deleteClient(@PathVariable int id) {
		Map<String, Boolean> data = new HashMap<>();
		data.put("deleted", clientBusiness.deleteClient(id));
		Response response = new Response("OK", 200, data);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
