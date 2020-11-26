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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import net.ausiasmarch.trolleyesSBserver.entity.CarritoEntity;
import net.ausiasmarch.trolleyesSBserver.entity.ProductoEntity;
import net.ausiasmarch.trolleyesSBserver.entity.UsuarioEntity;
import net.ausiasmarch.trolleyesSBserver.repository.CarritoRepository;
import net.ausiasmarch.trolleyesSBserver.repository.ProductoRepository;
import net.ausiasmarch.trolleyesSBserver.service.CarritoService;
import net.ausiasmarch.trolleyesSBserver.repository.UsuarioRepository;
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

    @Autowired
    ProductoRepository oProductoRepository;

    @Autowired
    CarritoService oCarritoService;

    @Autowired
    UsuarioRepository oUsuarioRepository;

    //ADD añadir un producto al carrito con una determinada cantidad -> params: producto, cantidad (POST)
    //REDUCE quitar un producto del carrito en una determinada cantidad -> params: producto, cantidad (DELETE)
    //REMOVE quitar un producto totalmente del carrito -> params: producto (DELETE)
    //CLEAR vaciar el carrito completamente (DELETE)
    //BUY comprar los productos en el carrito (PUT)
    //
    //PAGE (GET)
    //GET (GET)
    @PostMapping("/{producto}/{cantidad}")
    public ResponseEntity<?> add(@PathVariable(value = "producto") ProductoEntity oProductoEntity, @PathVariable(value = "cantidad") int cantidad) {
        UsuarioEntity oUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            try {
                return new ResponseEntity<>(oCarritoService.insert(oUsuarioEntity, oProductoEntity, cantidad), HttpStatus.OK);
            } catch (Exception ex) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
            }
        }
    }

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
            return new ResponseEntity<>(null, HttpStatus.PAYLOAD_TOO_LARGE);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody CarritoEntity oCarritoEntity) {
        if (oCarritoEntity.getId() == null) {
            return new ResponseEntity<CarritoEntity>(oCarritoRepository.save(oCarritoEntity), HttpStatus.OK);
        } else {
            return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
        }
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
        Page<CarritoEntity> oPage = oCarritoRepository.findAll(oPageable);
        return new ResponseEntity<Page<CarritoEntity>>(oPage, HttpStatus.OK);
    }

    @GetMapping("/page/producto/{id}")
    public ResponseEntity<?> getPageXProducto(@PageableDefault(page = 0, size = 10, direction = Direction.ASC) Pageable oPageable, @PathVariable(value = "id") Long id) {

        if (oProductoRepository.existsById(id)) {
            ProductoEntity oProductoEntity = oProductoRepository.getOne(id);
            Page<CarritoEntity> oPage = oCarritoRepository.findByProducto(oProductoEntity, oPageable);
            return new ResponseEntity<Page<CarritoEntity>>(oPage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @GetMapping("/page/usuario/{id}")
    public ResponseEntity<?> getPageXUsuario(@PageableDefault(page = 0, size = 10, direction = Direction.ASC) Pageable oPageable, @PathVariable(value = "id") Long id) {

        if (oUsuarioRepository.existsById(id)) {
            UsuarioEntity oUsuarioEntity = oUsuarioRepository.getOne(id);
            Page<CarritoEntity> oPage = oCarritoRepository.findByUsuario(oUsuarioEntity, oPageable);
            return new ResponseEntity<Page<CarritoEntity>>(oPage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }
}
