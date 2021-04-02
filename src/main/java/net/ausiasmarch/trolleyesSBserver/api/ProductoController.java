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
import java.util.Optional;
import javax.servlet.http.HttpSession;
import net.ausiasmarch.trolleyesSBserver.entity.ProductoEntity;
import net.ausiasmarch.trolleyesSBserver.entity.TipoproductoEntity;
import net.ausiasmarch.trolleyesSBserver.entity.UsuarioEntity;
import net.ausiasmarch.trolleyesSBserver.repository.ProductoRepository;
import net.ausiasmarch.trolleyesSBserver.repository.TipoproductoRepository;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    HttpSession oHttpSession;

    @Autowired
    ProductoRepository oProductoRepository;

    @Autowired
    TipoproductoRepository oTipoproductoRepository;

    @Autowired
    FillService oFillService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {
        if (oProductoRepository.existsById(id)) {
            return new ResponseEntity<ProductoEntity>(oProductoRepository.getOne(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<ProductoEntity>(oProductoRepository.getOne(id), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> all() {
        if (oProductoRepository.count() <= 1000) {
            return new ResponseEntity<List<ProductoEntity>>(oProductoRepository.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.PAYLOAD_TOO_LARGE);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> count() {
        return new ResponseEntity<Long>(oProductoRepository.count(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody ProductoEntity oProductoEntityFromRequest) {
        UsuarioEntity oUsuarioEntityFromSession = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntityFromSession == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioEntityFromSession.getTipousuario().getId() == 1) {
                if (oProductoEntityFromRequest.getId() == null) {
                    return new ResponseEntity<ProductoEntity>(oProductoRepository.save(oProductoEntityFromRequest), HttpStatus.OK);
                } else {
                    return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
                }
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody ProductoEntity oProductoEntityFromRequest) {
        UsuarioEntity oUsuarioEntityFromSession = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntityFromSession == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioEntityFromSession.getTipousuario().getId() == 1) { //administrador
                oProductoEntityFromRequest.setId(id);
                if (oProductoRepository.existsById(id)) {
                    return new ResponseEntity<ProductoEntity>(oProductoRepository.save(oProductoEntityFromRequest), HttpStatus.OK);
                } else {
                    return new ResponseEntity<Long>(0L, HttpStatus.NOT_MODIFIED);
                }
            } else {  //cliente
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        }
    }

    @PostMapping("/fill/{amount}")
    public ResponseEntity<?> fill(@PathVariable(value = "amount") Long amount) {
        UsuarioEntity oUsuarioEntityFromSession = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntityFromSession == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioEntityFromSession.getTipousuario().getId() == 1) {
                return new ResponseEntity<Long>(oFillService.productoFill(amount), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        UsuarioEntity oUsuarioEntityFromSession = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntityFromSession == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            if (oUsuarioEntityFromSession.getTipousuario().getId() == 1) {
                oProductoRepository.deleteById(id);
                if (oProductoRepository.existsById(id)) {
                    return new ResponseEntity<Long>(id, HttpStatus.NOT_MODIFIED);
                } else {
                    return new ResponseEntity<Long>(0L, HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        }
    }

    @GetMapping("/page")
    public ResponseEntity<?> getPage(@RequestParam("filter") Optional<String> strSearch, @PageableDefault(page = 0, size = 10, direction = Direction.ASC) Pageable oPageable) {
        Page<ProductoEntity> oPage = null;
        if (strSearch.isPresent()) {
            oPage = oProductoRepository.findByCodigoContainingIgnoreCaseOrNombreContainingIgnoreCaseOrTipoproductoNombreContainingIgnoreCase(strSearch.get(), strSearch.get(), strSearch.get(), oPageable);
        } else {
            oPage = oProductoRepository.findAll(oPageable);
        }
        return new ResponseEntity<Page<ProductoEntity>>(oPage, HttpStatus.OK);
    }

    @GetMapping("/page/tipoproducto/{id}")
    public ResponseEntity<?> getPageXTipoproducto(@PageableDefault(page = 0, size = 10, direction = Direction.ASC) Pageable oPageable, @PathVariable(value = "id") Long id) {
        if (oTipoproductoRepository.existsById(id)) {
            TipoproductoEntity oTipoproductoEntity = oTipoproductoRepository.getOne(id);
            Page<ProductoEntity> oPage = oProductoRepository.findByTipoproducto(oTipoproductoEntity, oPageable);
            return new ResponseEntity<Page<ProductoEntity>>(oPage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

    }

    //-----------INFORMES------------

    @GetMapping("/orderdescuento/10/desc")
    public ResponseEntity<?> get10ProductoOrderByDescuentoDesc() {
        UsuarioEntity oUsuarioEntityFromSession = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntityFromSession == null || oUsuarioEntityFromSession.getTipousuario().getId()!=1) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            List<ProductoEntity> oPage = oProductoRepository.findTop10ByOrderByDescuentoDesc();
            return new ResponseEntity<List<ProductoEntity>>(oPage, HttpStatus.OK);
        }
    }

    @GetMapping("/orderdescuento/50/desc")
    public ResponseEntity<?> get50ProductoOrderByDescuentoDesc() {
        UsuarioEntity oUsuarioEntityFromSession = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntityFromSession == null || oUsuarioEntityFromSession.getTipousuario().getId()!=1) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            List<ProductoEntity> oPage = oProductoRepository.findTop50ByOrderByDescuentoDesc();
            return new ResponseEntity<List<ProductoEntity>>(oPage, HttpStatus.OK);
        }
    }

    @GetMapping("/orderdescuento/100/desc")
    public ResponseEntity<?> get100ProductoOrderByDescuentoDesc() {
        UsuarioEntity oUsuarioEntityFromSession = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntityFromSession == null || oUsuarioEntityFromSession.getTipousuario().getId()!=1) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            List<ProductoEntity> oPage = oProductoRepository.findTop100ByOrderByDescuentoDesc();
            return new ResponseEntity<List<ProductoEntity>>(oPage, HttpStatus.OK);
        }
    }

    @GetMapping("/orderdescuento/10/asc")
    public ResponseEntity<?> get10ProductoOrderByDescuentoAsc() {
        UsuarioEntity oUsuarioEntityFromSession = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntityFromSession == null || oUsuarioEntityFromSession.getTipousuario().getId()!=1) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            List<ProductoEntity> oPage = oProductoRepository.findTop10ByOrderByDescuentoAsc();
            return new ResponseEntity<List<ProductoEntity>>(oPage, HttpStatus.OK);
        }
    }

    @GetMapping("/orderdescuento/50/asc")
    public ResponseEntity<?> get50ProductoOrderByDescuentoAsc() {
        UsuarioEntity oUsuarioEntityFromSession = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntityFromSession == null || oUsuarioEntityFromSession.getTipousuario().getId()!=1) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            List<ProductoEntity> oPage = oProductoRepository.findTop50ByOrderByDescuentoAsc();
            return new ResponseEntity<List<ProductoEntity>>(oPage, HttpStatus.OK);
        }
    }

    @GetMapping("/orderdescuento/100/asc")
    public ResponseEntity<?> get100ProductoOrderByDescuentoAsc() {
        UsuarioEntity oUsuarioEntityFromSession = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oUsuarioEntityFromSession == null || oUsuarioEntityFromSession.getTipousuario().getId()!=1) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            List<ProductoEntity> oPage = oProductoRepository.findTop100ByOrderByDescuentoAsc();
            return new ResponseEntity<List<ProductoEntity>>(oPage, HttpStatus.OK);
        }
    }
}
