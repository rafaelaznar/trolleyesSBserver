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
package net.ausiasmarch.trolleyesSBserver.repository;

import java.util.List;
import net.ausiasmarch.trolleyesSBserver.entity.ProductoEntity;
import net.ausiasmarch.trolleyesSBserver.entity.TipoproductoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {

    @Query(value = "SELECT * FROM productos p WHERE p.id_tipoproducto = :id_tipoproducto", nativeQuery = true)
    Page<ProductoEntity> findByCompraXFactura(Long id_tipoproducto, Pageable pageable);

    Page<ProductoEntity> findByTipoproducto(TipoproductoEntity oTipoproductoEntity, Pageable oPageable);

    
    List<ProductoEntity> findTop10ByOrderByDescuentoDesc();
    List<ProductoEntity> findTop50ByOrderByDescuentoDesc();
    List<ProductoEntity> findTop100ByOrderByDescuentoDesc();
    List<ProductoEntity> findTop10ByOrderByDescuentoAsc();
    List<ProductoEntity> findTop50ByOrderByDescuentoAsc();
    List<ProductoEntity> findTop100ByOrderByDescuentoAsc();


    Page<ProductoEntity> findByCodigoContainingIgnoreCaseOrNombreContainingIgnoreCaseOrTipoproductoNombreContainingIgnoreCase(String codigo, String descripcion, String nombre, Pageable pageable);


}
