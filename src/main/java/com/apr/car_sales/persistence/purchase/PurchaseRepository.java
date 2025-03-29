package com.apr.car_sales.persistence.purchase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Long> {
    @Query("SELECT pe FROM PurchaseEntity pe WHERE pe.user.id = :userId")
    Optional<List<PurchaseEntity>> findAllByUserId(long userId);
}
