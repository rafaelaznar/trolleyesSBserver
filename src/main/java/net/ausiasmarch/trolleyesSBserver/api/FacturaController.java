package net.ausiasmarch.trolleyesSBserver.api;

import javax.servlet.http.HttpSession;
import net.ausiasmarch.trolleyesSBserver.repository.FacturaRepository;
import net.ausiasmarch.trolleyesSBserver.entity.FacturaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/facutra")
public class FacturaController {
    
    @Autowired
    HttpSession aHttpSession;
    
    @Autowired
    FacturaRepository oFacturaRepository;
    
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<FacturaEntity>(oFacturaRepository.getOne(id), HttpStatus.OK);
    }
    
}
