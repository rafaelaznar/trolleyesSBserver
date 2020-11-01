package net.ausiasmarch.trolleyesSBserver.repository;


import net.ausiasmarch.trolleyesSBserver.entity.CarritoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarritoRepository extends JpaRepository<CarritoEntity, Long>  {
    
}
