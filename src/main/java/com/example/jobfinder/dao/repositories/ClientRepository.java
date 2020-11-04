package com.example.jobfinder.dao.repositories;

import com.example.jobfinder.dao.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {


    Client findByEmail(String email);
}
