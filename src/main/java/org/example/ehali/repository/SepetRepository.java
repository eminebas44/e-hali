package org.example.ehali.repository;

import org.example.ehali.entity.Sepet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SepetRepository extends JpaRepository<Sepet, Long> {
    Optional<Sepet> findByMusteri_MusteriId(Long musteriId);
}