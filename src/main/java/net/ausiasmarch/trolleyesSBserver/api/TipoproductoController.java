package net.ausiasmarch.trolleyesSBserver.api;

import java.util.List;
import javax.servlet.http.HttpSession;
import net.ausiasmarch.trolleyesSBserver.bean.ResponseBean;
import net.ausiasmarch.trolleyesSBserver.entity.TipoproductoEntity;
import net.ausiasmarch.trolleyesSBserver.repository.TipoproductoRepository;
import net.ausiasmarch.trolleyesSBserver.service.FillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tipoproducto")
public class TipoproductoController {

    @Autowired
    HttpSession oHttpSession;

    @Autowired
    TipoproductoRepository oTipoproductoRepository;

    @Autowired
    FillService oFillService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {
        if (oTipoproductoRepository.existsById(id)) {
            return new ResponseEntity<TipoproductoEntity>(oTipoproductoRepository.getOne(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<TipoproductoEntity>(oTipoproductoRepository.getOne(id), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> all() {
        if (oTipoproductoRepository.count() <= 1000) {
            return new ResponseEntity<List<TipoproductoEntity>>(oTipoproductoRepository.findAll(), HttpStatus.OK);
        } else {
            ResponseBean oSsesionBean = new ResponseBean();
            oSsesionBean.setMessage("ERROR: TOO MUCH REGISTRIES");
            oSsesionBean.setStatus(500);
            return new ResponseEntity<ResponseBean>(oSsesionBean, HttpStatus.OK);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> count() {
        return new ResponseEntity<Long>(oTipoproductoRepository.count(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody TipoproductoEntity oTipoproductoEntity) {
        return new ResponseEntity<TipoproductoEntity>(oTipoproductoRepository.save(oTipoproductoEntity), HttpStatus.OK);

    }

    @PostMapping("/fill")
    public ResponseEntity<?> fill() {
        oFillService.tipoproductoFill();
        ResponseBean oSessionBean = new ResponseBean();
        oSessionBean.setMessage("OK");
        oSessionBean.setStatus(200);
        return new ResponseEntity<ResponseBean>(oSessionBean, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        oTipoproductoRepository.deleteById(id);
        if (oTipoproductoRepository.existsById(id)) {
            return new ResponseEntity<Long>(id, HttpStatus.NOT_MODIFIED);
        } else {
            return new ResponseEntity<Long>(0L, HttpStatus.OK);
        }
    }
}
