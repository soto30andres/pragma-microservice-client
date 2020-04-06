package com.andsotra.microservice.client.business;

import java.util.List;

import com.andsotra.microservice.client.domain.ClientDto;
import com.andsotra.microservice.client.domain.RequestDto;;
public interface ClientBusiness {
	
	public ClientDto createClient(ClientDto request);
	
	public ClientDto updateClient(ClientDto request);

	boolean deleteClient(int id);

	public List<ClientDto> listClients(String documentNumber, String documentTypeShortName);
	
	public List<ClientDto> listClients(int olderThan);
	
	public List<ClientDto> listClients();
	
}
