/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ausiasmarch.trolleyesSBserver.api;

import java.util.List;
import javax.servlet.http.HttpSession;
import net.ausiasmarch.trolleyesSBserver.bean.ResponseBean;
import net.ausiasmarch.trolleyesSBserver.entity.ProductoEntity;
import net.ausiasmarch.trolleyesSBserver.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    HttpSession oHttpSession;

    @Autowired
    ProductoRepository oProductoRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<ProductoEntity>(oProductoRepository.getOne(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> all() {
        if (oProductoRepository.count() <= 1000) {
            return new ResponseEntity<List<ProductoEntity>>(oProductoRepository.findAll(), HttpStatus.OK);
        } else {
            ResponseBean oSessionBean = new ResponseBean();
            oSessionBean.setMessage("ERROR: TOO MUCH REGISTRIES");
            oSessionBean.setStatus(500);
            return new ResponseEntity<ResponseBean>(oSessionBean, HttpStatus.OK);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> count() {
        return new ResponseEntity<Long>(oProductoRepository.count(), HttpStatus.OK);
    }

}
