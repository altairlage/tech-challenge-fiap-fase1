package com.fiap.GastroHub.modules.users.infra.orm.repositories;

import com.fiap.GastroHub.modules.users.infra.orm.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long id);
}
