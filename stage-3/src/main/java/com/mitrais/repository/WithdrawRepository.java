package com.mitrais.repository;

import com.mitrais.entity.WithdrawEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WithdrawRepository extends JpaRepository<WithdrawEntity, UUID> {
}
