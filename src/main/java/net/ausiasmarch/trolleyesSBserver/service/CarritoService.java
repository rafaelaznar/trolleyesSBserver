package net.ausiasmarch.trolleyesSBserver.service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import net.ausiasmarch.trolleyesSBserver.entity.CarritoEntity;
import net.ausiasmarch.trolleyesSBserver.entity.CompraEntity;
import net.ausiasmarch.trolleyesSBserver.entity.FacturaEntity;
import net.ausiasmarch.trolleyesSBserver.entity.ProductoEntity;
import net.ausiasmarch.trolleyesSBserver.entity.UsuarioEntity;
import net.ausiasmarch.trolleyesSBserver.repository.CarritoRepository;
import net.ausiasmarch.trolleyesSBserver.repository.CompraRepository;
import net.ausiasmarch.trolleyesSBserver.repository.FacturaRepository;
import net.ausiasmarch.trolleyesSBserver.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository oCarritoRepository;

    @Autowired
    private ProductoRepository oProductoRepository;

    @Autowired
    private FacturaRepository oFacturaRepository;

    @Autowired
    private CompraRepository oCompraRepository;

    public CarritoEntity insert(UsuarioEntity oUsuarioEntity, Long id_producto, int cantidad) throws Exception {
        if (oUsuarioEntity != null && oUsuarioEntity.getTipousuario() != null) {
            if (oUsuarioEntity.getTipousuario().getId() == 2) {
                if (id_producto != null) {
                    Optional<ProductoEntity> oProductoEntity = oProductoRepository.findById(id_producto);
                    if (cantidad <= oProductoEntity.get().getExistencias()) {
                        //comprobar si ya existe el producto en el carrito de ese cliente
                        if (oCarritoRepository.countByUsuarioAndProducto(oUsuarioEntity, oProductoEntity.get()) == 0) {
                            //dar de alta el producto en el carrito del cliente
                            CarritoEntity oCarritoEntity = new CarritoEntity();
                            oCarritoEntity.setCantidad(cantidad);
                            oCarritoEntity.setProducto(oProductoEntity.get());
                            oCarritoEntity.setPrecio(oProductoEntity.get().getPrecio());
                            oCarritoEntity.setUsuario(oUsuarioEntity);
                            if (oProductoEntity.get().getExistencias() >= oCarritoEntity.getCantidad()) {
                                return oCarritoRepository.save(oCarritoEntity);
                            } else {
                                oCarritoEntity.setCantidad(oProductoEntity.get().getExistencias());
                                return oCarritoRepository.save(oCarritoEntity);
                            }
                        } else {
                            //obtener el producto del carrito del cliente e incrementarlo --> eugenio
                            //y devolver el item de carrito
                            CarritoEntity oCarritoEntity = (CarritoEntity) oCarritoRepository.findByUsuarioAndProducto(oUsuarioEntity.getId(), id_producto);
                            oCarritoEntity.setCantidad(oCarritoEntity.getCantidad() + cantidad);
                            if (oProductoEntity.get().getExistencias() >= oCarritoEntity.getCantidad()) {
                                return oCarritoRepository.save(oCarritoEntity);
                            } else {
                                oCarritoEntity.setCantidad(oProductoEntity.get().getExistencias());
                                return oCarritoRepository.save(oCarritoEntity);
                            }
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
    }

    public Optional<CarritoEntity> reduce(UsuarioEntity oUsuarioEntity, Long id_carrito, int cantidad) throws Exception {
        if (oUsuarioEntity != null && oUsuarioEntity.getTipousuario() != null) {
            if (oUsuarioEntity.getTipousuario().getId() == 2) {
                Optional<CarritoEntity> oCarritoEntityFromDB = oCarritoRepository.findById(id_carrito);
                if (oCarritoEntityFromDB.get().getCantidad() <= cantidad) {
                    //borrar el producto del carrito osea cargarse la linea del carrito
                    oCarritoRepository.delete(oCarritoEntityFromDB.get());
                } else {
                    //decrementar la cantidad del producto en el carrito
                    oCarritoEntityFromDB.get().setCantidad(oCarritoEntityFromDB.get().getCantidad() - cantidad);
                    oCarritoRepository.save(oCarritoEntityFromDB.get());
                }
                return oCarritoRepository.findById(oCarritoEntityFromDB.get().getId());
            } else {
                throw new Exception("UNAUTORIZED");
            }
        } else {
            throw new Exception("UNAUTORIZED");
        }
    }

    @Transactional
    public void remove(UsuarioEntity oUsuarioEntity, Long id_carrito) throws Exception {
        if (oUsuarioEntity != null && oUsuarioEntity.getTipousuario() != null) {
            if (oUsuarioEntity.getTipousuario().getId() == 2) {
                oCarritoRepository.deleteByIdAndUsuario(id_carrito, oUsuarioEntity);
                if (oCarritoRepository.existsById(id_carrito)) {
                    throw new Exception("CAN'T REMOVE");
                }
            } else {
                throw new Exception("UNAUTORIZED");
            }
        } else {
            throw new Exception("UNAUTORIZED");
        }
    }

    @Transactional
    public void clear(UsuarioEntity oUsuarioEntity) throws Exception {
        if (oUsuarioEntity != null && oUsuarioEntity.getTipousuario() != null) {
            if (oUsuarioEntity.getTipousuario().getId() == 2) {
                oCarritoRepository.deleteByUsuario(oUsuarioEntity);
            } else {
                throw new Exception("UNAUTORIZED");
            }
        } else {
            throw new Exception("UNAUTORIZED");
        }
    }

    @Transactional
    public void purchase(UsuarioEntity oUsuarioEntity) {
        if (oUsuarioEntity != null && oUsuarioEntity.getTipousuario() != null) {
            if (oUsuarioEntity.getTipousuario().getId() == 2) {
                //crear factura f               
                FacturaEntity oFacturaEntity = new FacturaEntity();
                oFacturaEntity.setFecha(LocalDateTime.now()); //OJO!!!!!!!!!!!!!
                oFacturaEntity.setIva(22);
                oFacturaEntity.setPagado(Boolean.FALSE);
                oFacturaEntity.setUsuario(oUsuarioEntity);
                oFacturaEntity = oFacturaRepository.save(oFacturaEntity);
                //--
                List<CarritoEntity> oCarritoList = oCarritoRepository.findAllByUsuario(oUsuarioEntity);
                //por cada linea del carrito 
                Iterator iterator = oCarritoList.iterator();
                while (iterator.hasNext()) {
                    CarritoEntity oCarritoEntity = (CarritoEntity) iterator.next();
                    //crear una compra y asignarla a f
                    CompraEntity oCompraEntity = new CompraEntity();
                    //rellenar la compra con el item del carrito y la factura
                    oCompraEntity.setCantidad(oCarritoEntity.getCantidad());
                    //obtener el producto para sacar el descuento y el precio
                    ProductoEntity oProductoEntity = oProductoRepository.getOne(oCarritoEntity.getProducto().getId());
                    oCompraEntity.setDescuento_producto(oProductoEntity.getDescuento());
                    oCompraEntity.setDescuento_usuario(oUsuarioEntity.getDescuento());
                    oCompraEntity.setFactura(oFacturaEntity);
                    oCompraEntity.setFecha(LocalDateTime.now());
                    oCompraEntity.setPrecio(oCarritoEntity.getProducto().getPrecio());
                    oCompraEntity.setProducto(oProductoEntity);
                    oCompraEntity.setCantidad(oCarritoEntity.getCantidad());
                    oCompraRepository.save(oCompraEntity);
                    //restar existencias del producto en la compra
                    oProductoEntity.setExistencias(oProductoEntity.getExistencias() - oCarritoEntity.getCantidad());
                    oProductoRepository.save(oProductoEntity);
                }
                //vaciar el carrito 
                oCarritoRepository.deleteByUsuario(oUsuarioEntity);
            }

        }

    }

}
