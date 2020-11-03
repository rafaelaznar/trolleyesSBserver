
package net.ausiasmarch.trolleyesSBserver.api;

import javax.servlet.http.HttpSession;
import net.ausiasmarch.trolleyesSBserver.entity.TipousuarioEntity;
import net.ausiasmarch.trolleyesSBserver.repository.TipousuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tipousuario")
public class TipousuarioController {
    
    @Autowired
    HttpSession oHttpSession;

    @Autowired
    TipousuarioRepository oTipousuarioRepository;
    
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<TipousuarioEntity>(oTipousuarioRepository.getOne(id), HttpStatus.OK);
    }
    
    @GetMapping("/count")
    public ResponseEntity<?> count() {
        return new ResponseEntity<Long>(oTipousuarioRepository.count(), HttpStatus.OK);
    }
    
}
