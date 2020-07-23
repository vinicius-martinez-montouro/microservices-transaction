package com.itau.security.repository;

import com.itau.security.model.ApplicationUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author vinicius.montouro
 */
@Repository
public interface ApplicationUserRepository extends CrudRepository<ApplicationUser,Long> {
    ApplicationUser findByUserName(String nome);
}
