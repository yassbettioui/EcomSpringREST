package com.yassine.ecommerceapi.repository;

import com.yassine.ecommerceapi.Entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findByUserId(Long userId); // Recherche des livraisons par utilisateur
}
