package net.ausiasmarch.trolleyesSBserver.service;

import java.util.Optional;
import net.ausiasmarch.trolleyesSBserver.entity.CarritoEntity;
import net.ausiasmarch.trolleyesSBserver.entity.ProductoEntity;
import net.ausiasmarch.trolleyesSBserver.entity.UsuarioEntity;
import net.ausiasmarch.trolleyesSBserver.repository.CarritoRepository;
import net.ausiasmarch.trolleyesSBserver.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository oCarritoRepository;
    
    @Autowired
    private ProductoRepository oProductoRepository;

    public CarritoEntity insert(UsuarioEntity oUsuarioEntity, Long id_producto, int cantidad) throws Exception {
        if (oUsuarioEntity != null && oUsuarioEntity.getTipousuario() != null) {
            if (oUsuarioEntity.getTipousuario().getId() == 2) {
                if (id_producto != null) {                    
                    Optional<ProductoEntity> oProductoEntity=oProductoRepository.findById(id_producto);
                    if (cantidad <= oProductoEntity.get().getExistencias()) {
                        //comprobar si ya existe el producto en el carrito de ese cliente
                        if (oCarritoRepository.countByUsuarioAndProducto(oUsuarioEntity, oProductoEntity.get()) == 0) {
                            //dar de alta el producto en el carrito del cliente
                            CarritoEntity oCarritoEntity = new CarritoEntity();
                            oCarritoEntity.setCantidad(cantidad);
                            oCarritoEntity.setProducto(oProductoEntity.get());
                            oCarritoEntity.setPrecio(oProductoEntity.get().getPrecio());
                            oCarritoEntity.setUsuario(oUsuarioEntity);
                            return oCarritoRepository.save(oCarritoEntity);
                        } else {
                            //obtener el producto del carrito del cliente e incrementarlo --> eugenio
                            //y devolver el item de carrito
                            CarritoEntity oCarritoEntity = (CarritoEntity) oCarritoRepository.findByUsuarioAndProducto(oUsuarioEntity.getId(), id_producto);
                            oCarritoEntity.setCantidad(oCarritoEntity.getCantidad() + cantidad);
                            return oCarritoRepository.save(oCarritoEntity);
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
        //return null;
    }

    public Optional<CarritoEntity> reduce(UsuarioEntity oUsuarioEntity, Long id_carrito, int cantidad) throws Exception {
        if (oUsuarioEntity != null && oUsuarioEntity.getTipousuario() != null) {
            if (oUsuarioEntity.getTipousuario().getId() == 2) {
                CarritoEntity oCarritoEntityFromDB = oCarritoRepository.getOne(id_carrito);
                if (oCarritoEntityFromDB.getCantidad() <= cantidad) {
                    //borrar el producto del carrito osea cargarse la linea del carrito
                    oCarritoRepository.delete(oCarritoEntityFromDB);

                } else {
                    //decrementar la cantidad del producto en el carrito
                    oCarritoEntityFromDB.setCantidad(oCarritoEntityFromDB.getCantidad() - cantidad);
                    oCarritoRepository.save(oCarritoEntityFromDB);
                }
                return oCarritoRepository.findById(oCarritoEntityFromDB.getId());
            } else {
                throw new Exception("UNAUTORIZED");
            }
        } else {
            throw new Exception("UNAUTORIZED");
        }
    }

}
