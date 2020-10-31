package net.ausiasmarch.trolleyesSBserver.repository;

import net.ausiasmarch.trolleyesSBserver.entity.CompraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends JpaRepository<CompraEntity, Long>  {
    
}
