package org.example.ehali.repository;

import org.example.ehali.entity.Favori;
import org.example.ehali.entity.FavoriId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriRepository extends JpaRepository<Favori, FavoriId> {
}
