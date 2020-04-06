package com.andsotra.microservice.client.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.andsotra.microservice.client.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>{
	
	
	public List<Client> findByDocumentNumberAndDocumentType_shortName(String documentNumber, String shortName);
	
	@Query("Select c from Client c where c.birthDay < ?1")
	public List<Client> findClientsViejos(Date date);
	
}
