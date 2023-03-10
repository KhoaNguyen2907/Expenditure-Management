package com.devper.authenticator.dao;

import com.devper.authenticator.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthenticatorDAO extends JpaRepository<AppUser, UUID> {

    Optional<AppUser> findByUsername(String username);
}
