/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ausiasmarch.trolleyesSBserver.api;

import java.util.List;
import javax.servlet.http.HttpSession;
import net.ausiasmarch.trolleyesSBserver.bean.ResponseBean;
import net.ausiasmarch.trolleyesSBserver.entity.UsuarioEntity;
import net.ausiasmarch.trolleyesSBserver.repository.UsuarioRepository;
import net.ausiasmarch.trolleyesSBserver.service.FillService;
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
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    HttpSession oHttpSession;

    @Autowired
    UsuarioRepository oUsuarioRepository;

    @Autowired
    FillService oFillService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {
        if (oUsuarioRepository.existsById(id)) {
            return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.getOne(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.getOne(id), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> get() {
        if (oUsuarioRepository.count() <= 1000) {
            return new ResponseEntity<List<UsuarioEntity>>(oUsuarioRepository.findAll(), HttpStatus.OK);
        } else {
            ResponseBean oSessionBean = new ResponseBean();
            oSessionBean.setMessage("ERROR: TOO MUCH REGISTRIES");
            oSessionBean.setStatus(500);
            return new ResponseEntity<ResponseBean>(oSessionBean, HttpStatus.OK);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody UsuarioEntity oUsuarioEntity) {
        if (oUsuarioEntity.getId() == null) {
        return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.save(oUsuarioEntity), HttpStatus.OK);
        } else {
            return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
        }
    }
        
    @GetMapping("/count")
    public ResponseEntity<?> count() {
        return new ResponseEntity<Long>(oUsuarioRepository.count(), HttpStatus.OK);
    }

    @PostMapping("/fill/{amount}")
    public ResponseEntity<?> fill(@PathVariable(value = "amount") Long amount) {

        Long countInicio = oUsuarioRepository.count();
        oFillService.usuarioFill(amount);
        Long countFinal = oUsuarioRepository.count();
        Long diferencia = countFinal - countInicio;
        if (diferencia <= 0) {
            return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
        } else {
            return new ResponseEntity<Long>(diferencia, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        oUsuarioRepository.deleteById(id);
        if (oUsuarioRepository.existsById(id)) {
            return new ResponseEntity<Long>(id, HttpStatus.NOT_MODIFIED);
        } else {
            return new ResponseEntity<Long>(0L, HttpStatus.OK);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody UsuarioEntity oUsuarioEntity) {
        oUsuarioEntity.setId(id);
        if (oUsuarioRepository.existsById(id)) {
            return new ResponseEntity<UsuarioEntity>(oUsuarioRepository.save(oUsuarioEntity), HttpStatus.OK);
        } else {
            return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
        }
    }
    
    @GetMapping("/page")
    public ResponseEntity<?> getPage(@PageableDefault(page = 0, size = 10, direction = Direction.ASC) Pageable oPageable) {
        
        Page<UsuarioEntity> oPage= oUsuarioRepository.findAll(oPageable);            
        return new ResponseEntity<Page<UsuarioEntity>>(oPage, HttpStatus.OK);        
    }

}
