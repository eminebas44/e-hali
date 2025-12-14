package org.example.ehali.repository;

import org.example.ehali.entity.Puanlama;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PuanlamaRepository extends JpaRepository<Puanlama, Long> {
}
