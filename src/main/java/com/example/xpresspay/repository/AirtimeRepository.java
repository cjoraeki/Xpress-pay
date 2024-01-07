package com.example.xpresspay.repository;

import com.example.xpresspay.entity.Airtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirtimeRepository extends JpaRepository<Airtime, Long> {
}
