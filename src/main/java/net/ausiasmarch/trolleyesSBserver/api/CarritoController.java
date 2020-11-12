package net.ausiasmarch.trolleyesSBserver.api;

import java.util.List;
import javax.servlet.http.HttpSession;
import net.ausiasmarch.trolleyesSBserver.bean.ResponseBean;
import net.ausiasmarch.trolleyesSBserver.entity.CarritoEntity;
import net.ausiasmarch.trolleyesSBserver.repository.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
        if (oCarritoRepository.existsById(id)) {
            return new ResponseEntity<CarritoEntity>(oCarritoRepository.getOne(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<CarritoEntity>(oCarritoRepository.getOne(id), HttpStatus.NOT_FOUND);
        }
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
    public ResponseEntity<?> create(@RequestBody CarritoEntity oCarritoEntity) {
        return new ResponseEntity<CarritoEntity>(oCarritoRepository.save(oCarritoEntity), HttpStatus.OK);

    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        oCarritoRepository.deleteById(id);
        if (oCarritoRepository.existsById(id)) {
            return new ResponseEntity<Long>(id, HttpStatus.NOT_MODIFIED);
        } else {
            return new ResponseEntity<Long>(0L, HttpStatus.OK);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody CarritoEntity oCarritoEntity) {
        oCarritoEntity.setId(id);
        if (oCarritoRepository.existsById(id)) {
            return new ResponseEntity<CarritoEntity>(oCarritoRepository.save(oCarritoEntity), HttpStatus.OK);
        } else {
            return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
        }
    }
    
    @GetMapping("/page")
    public ResponseEntity<?> getPage(@PageableDefault(page = 0, size = 10, direction = Direction.ASC) Pageable oPageable) {
        
        Page<CarritoEntity> oPage= oCarritoRepository.findAll(oPageable);            
        return new ResponseEntity<Page<CarritoEntity>>(oPage, HttpStatus.OK);        
    }
    
}
