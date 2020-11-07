/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ausiasmarch.trolleyesSBserver.service;

import net.ausiasmarch.trolleyesSBserver.entity.ProductoEntity;
import net.ausiasmarch.trolleyesSBserver.entity.TipoproductoEntity;
import net.ausiasmarch.trolleyesSBserver.entity.TipousuarioEntity;
import net.ausiasmarch.trolleyesSBserver.entity.UsuarioEntity;
import net.ausiasmarch.trolleyesSBserver.helper.RandomHelper;
import net.ausiasmarch.trolleyesSBserver.repository.ProductoRepository;
import net.ausiasmarch.trolleyesSBserver.repository.TipoproductoRepository;
import net.ausiasmarch.trolleyesSBserver.repository.TipousuarioRepository;
import net.ausiasmarch.trolleyesSBserver.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FillService {

    @Autowired
    TipoproductoRepository oTipoproductoRepository;

    @Autowired
    ProductoRepository oProductoRepository;

    @Autowired
    TipousuarioRepository oTipousuarioRepository;

    @Autowired
    UsuarioRepository oUsuarioRepository;

    public int tipoproductoFill() {

        TipoproductoEntity oTipoproductoEntity = new TipoproductoEntity();
        oTipoproductoEntity.setNombre("Limpieza");
        oTipoproductoRepository.save(oTipoproductoEntity);

        oTipoproductoEntity = new TipoproductoEntity();
        oTipoproductoEntity.setNombre("Herramienta");
        oTipoproductoRepository.save(oTipoproductoEntity);

        oTipoproductoEntity = new TipoproductoEntity();
        oTipoproductoEntity.setNombre("Moda");
        oTipoproductoRepository.save(oTipoproductoEntity);

        oTipoproductoEntity = new TipoproductoEntity();
        oTipoproductoEntity.setNombre("Electrónica");
        oTipoproductoRepository.save(oTipoproductoEntity);

        oTipoproductoEntity = new TipoproductoEntity();
        oTipoproductoEntity.setNombre("Accesorios");
        oTipoproductoRepository.save(oTipoproductoEntity);

        oTipoproductoEntity = new TipoproductoEntity();
        oTipoproductoEntity.setNombre("Juguete");
        oTipoproductoRepository.save(oTipoproductoEntity);

        oTipoproductoEntity = new TipoproductoEntity();
        oTipoproductoEntity.setNombre("Alimentación");
        oTipoproductoRepository.save(oTipoproductoEntity);

        oTipoproductoEntity = new TipoproductoEntity();
        oTipoproductoEntity.setNombre("Automóvil");
        oTipoproductoRepository.save(oTipoproductoEntity);

        oTipoproductoEntity = new TipoproductoEntity();
        oTipoproductoEntity.setNombre("Deporte");
        oTipoproductoRepository.save(oTipoproductoEntity);

        oTipoproductoEntity = new TipoproductoEntity();
        oTipoproductoEntity.setNombre("Hogar");
        oTipoproductoRepository.save(oTipoproductoEntity);

        return 10;

    }

    public void productoFill(Long cantidad) {

        for (int i = 1; i <= cantidad; i++) {
            ProductoEntity oProductoEntity = new ProductoEntity();
            oProductoEntity.setCodigo(RandomHelper.getRandomHexString(RandomHelper.getRandomInt(5, 10)).toUpperCase());
            oProductoEntity.setDescuento(RandomHelper.getRandomInt(0, 3));
            oProductoEntity.setExistencias(RandomHelper.getRandomInt(0, 1000));

            String nombre = "";

            switch (RandomHelper.getRandomInt(1, 8)) {
                case 1:
                    nombre = "Producto para ";
                    break;
                case 2:
                    nombre = "Accesorio de ";
                    break;
                case 3:
                    nombre = "Herramienta de ";
                    break;
                case 4:
                    nombre = "Útil para ";
                    break;
                case 5:
                    nombre = "Aperaje para ";
                    break;
                case 6:
                    nombre = "Instrumento de ";
                    break;
                case 7:
                    nombre = "Complemento de ";
                    break;
                case 8:
                    nombre = "Recambio de ";
                    break;
            }
            switch (RandomHelper.getRandomInt(1, 7)) {
                case 1:
                    nombre += "limpieza de ";
                    break;
                case 2:
                    nombre += "reaparación de  ";
                    break;
                case 3:
                    nombre += "mecanizado de ";
                    break;
                case 4:
                    nombre += "medida de ";
                    break;
                case 5:
                    nombre += "expansión de ";
                    break;
                case 6:
                    nombre += "ayuda de ";
                    break;
                case 7:
                    nombre += "instalación de ";
                    break;
            }
            switch (RandomHelper.getRandomInt(1, 7)) {
                case 1:
                    nombre += "animales domésticos";
                    break;
                case 2:
                    nombre += "juguetes";
                    break;
                case 3:
                    nombre += "niños de preescolar";
                    break;
                case 4:
                    nombre += "maquillaje";
                    break;
                case 5:
                    nombre += "automóviles";
                    break;
                case 6:
                    nombre += "ascensores";
                    break;
                case 7:
                    nombre += "libros";
                    break;
            }

            oProductoEntity.setNombre(nombre);
            oProductoEntity.setId_tipoproducto(Long.valueOf(RandomHelper.getRandomInt(1, 10))); //del 1 al 10
            oProductoEntity.setImagen("no-product-image.png");
            oProductoEntity.setPrecio(RandomHelper.getRadomDouble(1, 3000));
            oProductoRepository.save(oProductoEntity);
        }

    }

    public void tipousuarioFill() {

        TipousuarioEntity oTipousuarioEntity = new TipousuarioEntity();
        oTipousuarioEntity.setNombre("Administrador");
        oTipousuarioRepository.save(oTipousuarioEntity);

        oTipousuarioEntity = new TipousuarioEntity();
        oTipousuarioEntity.setNombre("Cliente");
        oTipousuarioRepository.save(oTipousuarioEntity);

    }

    public void usuarioFill(Long cantidad) {

        String[] nombres = {"Andrea", "David", "Baldomero", "Balduino", "Baldwin", "Baltasar", "Barry", "Bartolo",
            "Bartolomé", "Baruc", "Baruj", "Candelaria", "Cándida", "Canela", "Caridad", "Carina", "Carisa",
            "Caritina", "Carlota", "Baltazar"};
        String[] apellidos = {"Gomez", "Guerrero", "Cardenas", "Cardiel", "Cardona", "Cardoso", "Cariaga", "Carillo",
            "Carion", "Castiyo", "Castorena", "Castro", "Grande", "Grangenal", "Grano", "Grasia", "Griego",
            "Grigalva"};

        for (int i = 1; i <= cantidad; i++) {

            UsuarioEntity oUsuarioEntity = new UsuarioEntity();

            oUsuarioEntity.setDni(String.valueOf(RandomHelper.getRandomInt(11111111, 99999999) + String.valueOf(RandomHelper.getRadomChar()).toUpperCase()));
            String nombre = nombres[(int) (Math.floor(Math.random() * ((nombres.length - 1) - 0 + 1) + 0))];
            String apellido1 = apellidos[(int) (Math.floor(Math.random() * ((apellidos.length - 1) - 0 + 1) + 0))];
            String apellido2 = apellidos[(int) (Math.floor(Math.random() * ((apellidos.length - 1) - 0 + 1) + 0))];
            oUsuarioEntity.setNombre(nombre);
            oUsuarioEntity.setApellido1(apellido1);
            oUsuarioEntity.setApellido2(apellido2);
            //Maybe esta bien
            oUsuarioEntity.setLogin(nombre.substring(0, 4) + apellido1.substring(0, 3) + apellido2.substring(0, 3) + String.valueOf(RandomHelper.getRandomInt(1, 999)));
            oUsuarioEntity.setPassword("da8ab09ab4889c6208116a675cad0b13e335943bd7fc418782d054b32fdfba04");
            oUsuarioEntity.setEmail(nombre + apellido1.charAt(0) + "@ausiasmarch.net");
            oUsuarioEntity.setDescuento(0);
            oUsuarioEntity.setId_tipousuario(2L);
            oUsuarioEntity.setToken("");
            oUsuarioEntity.setValidado(true);
            oUsuarioEntity.setActivo(true);

            oUsuarioRepository.save(oUsuarioEntity);
        }
    }

}
