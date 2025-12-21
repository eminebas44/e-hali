package org.example.ehali.repository;

import org.example.ehali.entity.Kategori;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface KategoriRepository extends JpaRepository<Kategori, Long> {
    // Bu metodun ismi HaliService'dekiyle birebir aynı olmalı
    Optional<Kategori> findByKategoriAdiIgnoreCase(String kategoriAdi);
}