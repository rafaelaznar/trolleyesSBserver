package net.ausiasmarch.trolleyesSBserver.service;

import net.ausiasmarch.trolleyesSBserver.entity.CarritoEntity;
import net.ausiasmarch.trolleyesSBserver.entity.ProductoEntity;
import net.ausiasmarch.trolleyesSBserver.entity.UsuarioEntity;
import net.ausiasmarch.trolleyesSBserver.repository.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository oCarritoRepository;

    public CarritoEntity insert(UsuarioEntity oUsuarioEntity, ProductoEntity oProductoEntity, int cantidad) throws Exception {
        if (oUsuarioEntity != null && oUsuarioEntity.getTipousuario() != null) {
            if (oUsuarioEntity.getTipousuario().getId() == 2) {
                if (oProductoEntity != null) {
                    if (cantidad <= oProductoEntity.getExistencias()) {
                        //comprobar si ya existe el producto en el carrito de ese cliente
                        if (oCarritoRepository.countByUsuarioAndProducto(oUsuarioEntity, oProductoEntity) == 0) {
                            //dar de alta el producto en el carrito del cliente
                            CarritoEntity oCarritoEntity = new CarritoEntity();
                            oCarritoEntity.setCantidad(cantidad);
                            oCarritoEntity.setProducto(oProductoEntity);
                            oCarritoEntity.setPrecio(oProductoEntity.getPrecio());
                            oCarritoEntity.setUsuario(oUsuarioEntity);
                            return oCarritoRepository.save(oCarritoEntity);
                        } else {
                            //obtener el producto del carrito del cliente e incrementarlo --> eugenio
                            //y devolver el item de carrito
                            
                            
                        }

                    } else {
                        throw new Exception("NOT ENOUGH PRODUCT");
                    }
                } else {
                    throw new Exception("PRODUCTO DOES NOT EXIST");
                }

            } else {
                throw new Exception("UNAUTORIZED");
            }
        } else {
            throw new Exception("UNAUTORIZED");
        }
        return null;
    }

}
