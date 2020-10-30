package net.ausiasmarch.trolleyesSBserver.repository;

import net.ausiasmarch.trolleyesSBserver.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity, Long>  {
    
}
