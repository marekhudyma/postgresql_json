package com.marekhudyma.postgresql.json.repository;

import com.marekhudyma.postgresql.json.model.InfoJson;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface InfoJsonRepository extends JpaRepository<InfoJson, Long> {

    @Query(value = "SELECT * FROM info_json ij WHERE ij.json ->> 'customerId' = :customerId", nativeQuery = true)
    List<InfoJson> findByCustomerId(String customerId);

    @Query(value = "SELECT * FROM info_json ij WHERE ij.json -> 'items' ->> 'productId' = :productId", nativeQuery = true)
    List<InfoJson> findByProductId(String productId);
}