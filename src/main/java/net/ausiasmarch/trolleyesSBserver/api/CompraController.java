package net.ausiasmarch.trolleyesSBserver.api;

import javax.servlet.http.HttpSession;
import net.ausiasmarch.trolleyesSBserver.bean.ResponseBean;
import net.ausiasmarch.trolleyesSBserver.entity.CompraEntity;
import net.ausiasmarch.trolleyesSBserver.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/compra")
public class CompraController {
    
    @Autowired
    HttpSession oHttpSession;

    @Autowired
    CompraRepository oCompraRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<CompraEntity>(oCompraRepository.getOne(id), HttpStatus.OK);
    }
    
}
