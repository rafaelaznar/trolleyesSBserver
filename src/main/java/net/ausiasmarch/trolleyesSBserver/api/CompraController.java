package net.ausiasmarch.trolleyesSBserver.api;

import java.util.List;
import javax.servlet.http.HttpSession;
import net.ausiasmarch.trolleyesSBserver.bean.ResponseBean;
import net.ausiasmarch.trolleyesSBserver.entity.CompraEntity;
import net.ausiasmarch.trolleyesSBserver.repository.CompraRepository;
import net.ausiasmarch.trolleyesSBserver.service.FillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/compra")
public class CompraController {
    
    @Autowired
    HttpSession oHttpSession;

    @Autowired
    CompraRepository oCompraRepository;
    
    @Autowired
    FillService oFillService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<CompraEntity>(oCompraRepository.getOne(id), HttpStatus.OK);
    }
    
    @GetMapping("/all")
    public ResponseEntity<?> all() {
        if (oCompraRepository.count() <= 1000) {
            return new ResponseEntity<List<CompraEntity>>(oCompraRepository.findAll(), HttpStatus.OK);
        } else {
            ResponseBean oSsesionBean = new ResponseBean();
            oSsesionBean.setMessage("ERROR: TOO MUCH REGISTRIES");
            oSsesionBean.setStatus(500);
            return new ResponseEntity<ResponseBean>(oSsesionBean, HttpStatus.OK);
        }
    }
    
    @GetMapping("/count")
    public ResponseEntity<?> count() {
        return new ResponseEntity<Long>(oCompraRepository.count(), HttpStatus.OK);
    }
    
    @PostMapping("/fill/{compras}")
    public ResponseEntity<?> fill(@PathVariable(value = "compras") Long compras) {
        Long countInicio = oCompraRepository.count();
        oFillService.CompraFill(compras);
        Long countFinal = oCompraRepository.count();
        Long diferencia = countFinal - countInicio;
        if (diferencia <= 0) {
            return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
        } else {
            return new ResponseEntity<Long>(diferencia, HttpStatus.OK);
        }
    }
}
