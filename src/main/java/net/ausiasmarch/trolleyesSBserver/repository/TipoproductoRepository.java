package net.ausiasmarch.trolleyesSBserver.repository;


import net.ausiasmarch.trolleyesSBserver.entity.TipoproductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoproductoRepository extends JpaRepository<TipoproductoEntity, Long>  {
    
}
