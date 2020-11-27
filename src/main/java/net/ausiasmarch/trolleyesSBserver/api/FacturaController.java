/*
 * Copyright (c) 2020
 *
 * by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com) & 2020 DAW students
 * 
 * TROLLEYES: Free Open Source Shopping Site
 *
 *
 * Sources at:                https://github.com/rafaelaznar/trolleyesSBserver                            
 * Database at:               https://github.com/rafaelaznar/trolleyesSBserver
 * Client at:                 https://github.com/rafaelaznar/TrolleyesAngularJSClient
 *
 * TROLLEYES is distributed under the MIT License (MIT)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.ausiasmarch.trolleyesSBserver.api;

import java.util.List;
import javax.servlet.http.HttpSession;
import net.ausiasmarch.trolleyesSBserver.entity.FacturaEntity;
import net.ausiasmarch.trolleyesSBserver.entity.UsuarioEntity;
import net.ausiasmarch.trolleyesSBserver.repository.FacturaRepository;
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
@RequestMapping("/factura")

public class FacturaController {

    @Autowired
    HttpSession oHttpSession;

    @Autowired
    FacturaRepository oFacturaRepository;
    
    @Autowired
    UsuarioRepository oUsuarioRepository;

    @Autowired
    FillService oFillService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        FacturaEntity oFacturaEntity = new FacturaEntity();

        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioEntity.getTipousuario().getId() == 1) { //administrador
                if (oFacturaRepository.existsById(id)) {
                    return new ResponseEntity<FacturaEntity>(oFacturaRepository.getOne(id), HttpStatus.OK);
                } else {
                    return new ResponseEntity<FacturaEntity>(oFacturaRepository.getOne(id), HttpStatus.NOT_FOUND);
                }
            } else {  //cliente
                if (oFacturaEntity.getUsuario().getId().equals(oUsuarioEntity.getId())) {  //los datos pedidos por el cliente son sus propios datos?
                    return new ResponseEntity<FacturaEntity>(oFacturaRepository.getOne(id), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
                }
            }
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> all() {
 if (oFacturaRepository.count() <= 1000) {
                    return new ResponseEntity<List<FacturaEntity>>(oFacturaRepository.findAll(), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(null, HttpStatus.PAYLOAD_TOO_LARGE);
                }
            }
   
    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody FacturaEntity oFacturaEntity) {

        if (oFacturaEntity.getId() == null) {
            return new ResponseEntity<FacturaEntity>(oFacturaRepository.save(oFacturaEntity), HttpStatus.OK);
        } else {
            return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        oFacturaRepository.deleteById(id);
        if (oFacturaRepository.existsById(id)) {
            return new ResponseEntity<Long>(id, HttpStatus.NOT_MODIFIED);
        } else {
            return new ResponseEntity<Long>(0L, HttpStatus.OK);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> count() {
        return new ResponseEntity<Long>(oFacturaRepository.count(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody FacturaEntity oFacturaEntity) {
        oFacturaEntity.setId(id);
        if (oFacturaRepository.existsById(id)) {
            return new ResponseEntity<FacturaEntity>(oFacturaRepository.save(oFacturaEntity), HttpStatus.OK);
        } else {
            return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
        }
    }

    @GetMapping("/page")
    public ResponseEntity<?> getPage(@PageableDefault(page = 0, size = 10, direction = Direction.ASC) Pageable oPageable) {

        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        FacturaEntity oFacturaEntity = new FacturaEntity();

        Page<FacturaEntity> oPage = oFacturaRepository.findAll(oPageable);
        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioEntity.getTipousuario().getId() == 1) { //administrador
                return new ResponseEntity<Page<FacturaEntity>>(oPage, HttpStatus.OK);
            } else {  //cliente
                if (oFacturaEntity.getUsuario().getId().equals(oUsuarioEntity.getId())) {  //los datos pedidos por el cliente son sus propios datos?
                    return new ResponseEntity<Page<FacturaEntity>>(oPage, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
                }
            }
        }
    }
    
    @GetMapping("/pagexusuario/{id}")
    public ResponseEntity<?> getPageXUsuario(@PageableDefault(page = 0, size = 10, direction = Direction.ASC) Pageable oPageable, @PathVariable(value = "id") Long id) {

        if (oUsuarioRepository.existsById(id)) {
            UsuarioEntity oUsuarioEntity = oUsuarioRepository.getOne(id);
            Page<FacturaEntity> oPage = oFacturaRepository.findByUsuario(oUsuarioEntity, oPageable);
            return new ResponseEntity<Page<FacturaEntity>>(oPage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }
    
    @PostMapping("/fill/{amount}")
    public ResponseEntity<?> fill(@PathVariable(value = "amount") Long amount) {
        return new ResponseEntity<Long>(oFillService.facturaFill(amount), HttpStatus.OK);
    }

}
