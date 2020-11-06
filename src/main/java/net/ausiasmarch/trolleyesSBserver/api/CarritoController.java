
package net.ausiasmarch.trolleyesSBserver.api;

import java.util.List;
import javax.servlet.http.HttpSession;
import net.ausiasmarch.trolleyesSBserver.bean.ResponseBean;
import net.ausiasmarch.trolleyesSBserver.entity.CarritoEntity;
import net.ausiasmarch.trolleyesSBserver.repository.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carrito")
public class CarritoController {
    
    @Autowired
    HttpSession oHttpSession;

    @Autowired
    CarritoRepository oCarritoRepository;
    
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<CarritoEntity>(oCarritoRepository.getOne(id), HttpStatus.OK);
    }
    
    @GetMapping("/count")
    public ResponseEntity<?> count() {
        return new ResponseEntity<Long>(oCarritoRepository.count(), HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<?> all() {
        if (oCarritoRepository.count() <= 1000) {
            return new ResponseEntity<List<CarritoEntity>>(oCarritoRepository.findAll(), HttpStatus.OK);
        } else {
            ResponseBean oSsesionBean = new ResponseBean();
            oSsesionBean.setMessage("ERROR: TOO MUCH REGISTRIES");
            oSsesionBean.setStatus(500);
            return new ResponseEntity<ResponseBean>(oSsesionBean, HttpStatus.OK);
        }
    }
    
    @PostMapping("/")
    public ResponseEntity<?> create (@RequestBody CarritoEntity oCarritoEntity) {
                return new ResponseEntity<CarritoEntity>(oCarritoRepository.save(oCarritoEntity), HttpStatus.OK);

    }
    
}
