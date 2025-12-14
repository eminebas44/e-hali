package org.example.ehali.repository;

import org.example.ehali.entity.Geribildirim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeribildirimRepository extends JpaRepository<Geribildirim, Long> {
}
