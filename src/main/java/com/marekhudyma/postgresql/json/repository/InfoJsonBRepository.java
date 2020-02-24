package com.marekhudyma.postgresql.json.repository;

import com.marekhudyma.postgresql.json.model.InfoJson;
import com.marekhudyma.postgresql.json.model.InfoJsonB;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface InfoJsonBRepository extends JpaRepository<InfoJsonB, Long> {

    @Query(value = "SELECT * FROM info_jsonb ij WHERE ij.json ->> 'customerId' = :customerId", nativeQuery = true)
    List<InfoJsonB> findByCustomerId(String customerId);

    @Query(value = "SELECT * FROM info_jsonb ij WHERE ij.json -> 'items' ->> 'productId' = :productId", nativeQuery = true)
    List<InfoJsonB> findByProductId(String productId);
}