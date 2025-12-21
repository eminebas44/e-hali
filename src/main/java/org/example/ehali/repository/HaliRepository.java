package org.example.ehali.repository;

import org.example.ehali.entity.Hali;
import org.example.ehali.entity.Kategori;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HaliRepository extends JpaRepository<Hali, Long> {
    List<Hali> findByKategoriIn(List<Kategori> kategoriler);
}