package org.example.ehali.repository;

import org.example.ehali.entity.Mesajlasma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesajlasmaRepository extends JpaRepository<Mesajlasma, Long> {
}
