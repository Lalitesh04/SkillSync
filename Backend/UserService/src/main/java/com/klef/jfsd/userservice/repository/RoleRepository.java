package com.klef.jfsd.userservice.repository;

import com.klef.jfsd.userservice.models.Role;
import com.klef.jfsd.userservice.models.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, RoleType> {

    Role findByName(String name);
    Role findByType(RoleType type);
}
