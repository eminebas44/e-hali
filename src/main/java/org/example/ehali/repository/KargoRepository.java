package org.example.ehali.repository;

import org.example.ehali.entity.Kargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KargoRepository extends JpaRepository<Kargo, Long> {
}
