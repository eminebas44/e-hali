package org.example.ehali.repository;

import org.example.ehali.entity.Odeme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OdemeRepository extends JpaRepository<Odeme, Long> {
}
