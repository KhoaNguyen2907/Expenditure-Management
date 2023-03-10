package com.devper.tracker.dao;

import com.devper.tracker.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionDAO extends JpaRepository<Transaction, UUID> {

//    Optional<Transaction> findByIdAndOwnerId(UUID id, UUID ownerId);
//
//    List<Transaction> findAllByOwnerId(UUID ownerId);

}
