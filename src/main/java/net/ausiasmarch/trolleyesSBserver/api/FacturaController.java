
package net.ausiasmarch.trolleyesSBserver.api;

import java.util.List;
import javax.servlet.http.HttpSession;
import net.ausiasmarch.trolleyesSBserver.bean.ResponseBean;
import net.ausiasmarch.trolleyesSBserver.entity.FacturaEntity;
import net.ausiasmarch.trolleyesSBserver.repository.FacturaRepository;
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
@RequestMapping("/factura")

public class FacturaController {
    
    @Autowired
    HttpSession oHttpSession;
    
    @Autowired
    FacturaRepository oFacturaRepository;
    
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<FacturaEntity>(oFacturaRepository.getOne(id), HttpStatus.OK);
    }
    

    @GetMapping("/all")
    public ResponseEntity<?> all() {
        if (oFacturaRepository.count() <= 1000) {
            return new ResponseEntity<List<FacturaEntity>>(oFacturaRepository.findAll(), HttpStatus.OK);
        } else {
            ResponseBean oSessionBean = new ResponseBean();
            oSessionBean.setMessage("ERROR: TOO MUCH REGISTRIES");
            oSessionBean.setStatus(500);
            return new ResponseEntity<ResponseBean>(oSessionBean, HttpStatus.OK);
        }
        
    }
        
        
    @PostMapping("/")
    public ResponseEntity<?> create (@RequestBody FacturaEntity oFacturaEntity) {
                return new ResponseEntity<FacturaEntity>(oFacturaRepository.save(oFacturaEntity), HttpStatus.OK);

    }
    
    
    
}
