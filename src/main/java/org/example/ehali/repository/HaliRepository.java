package org.example.ehali.repository;

import org.example.ehali.entity.Hali;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HaliRepository extends JpaRepository<Hali, Long> {
}
