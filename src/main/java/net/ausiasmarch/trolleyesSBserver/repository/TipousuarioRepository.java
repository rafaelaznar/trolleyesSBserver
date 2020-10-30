
package net.ausiasmarch.trolleyesSBserver.repository;

import net.ausiasmarch.trolleyesSBserver.entity.TipousuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface TipousuarioRepository extends JpaRepository<TipousuarioEntity, Long> {
    
}
