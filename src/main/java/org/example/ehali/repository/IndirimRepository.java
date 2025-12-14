package org.example.ehali.repository;

import org.example.ehali.entity.Indirim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndirimRepository extends JpaRepository<Indirim, Long> {
}
