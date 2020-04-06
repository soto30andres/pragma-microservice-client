package com.andsotra.microservice.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.andsotra.microservice.client.model.Client;
import com.andsotra.microservice.client.model.DocumentType;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long>{
	
	public DocumentType findByShortName(String shortName);

}
