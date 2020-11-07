package net.ausiasmarch.trolleyesSBserver.api;

import net.ausiasmarch.trolleyesSBserver.bean.UsuarioBean;
import javax.servlet.http.HttpSession;
import net.ausiasmarch.trolleyesSBserver.entity.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    HttpSession oHttpSession;
   
    @GetMapping("/")
    public ResponseEntity<?> check() {
        UsuarioEntity oSessionUsuarioEntity = (UsuarioEntity) oHttpSession.getAttribute("usuario");
        if (oSessionUsuarioEntity == null) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<UsuarioEntity>(oSessionUsuarioEntity, HttpStatus.OK);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> login(@RequestBody UsuarioBean oUsuarioBean) {
        if (oUsuarioBean.getLogin().equalsIgnoreCase("rafa")) {
            if (oUsuarioBean.getPassword().equalsIgnoreCase("79063e8037fff16d297a1fe65136f1251126cddb2cc9870ecf8d653835538e85")) {
                UsuarioEntity oSessionUsuarioEntity = new UsuarioEntity();
                oSessionUsuarioEntity.setLogin(oUsuarioBean.getLogin());
                oHttpSession.setAttribute("usuario", oSessionUsuarioEntity);
                return new ResponseEntity<UsuarioEntity>(oSessionUsuarioEntity, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<?> logout() {
        oHttpSession.invalidate();
        return new ResponseEntity<>("BYE", HttpStatus.OK);        
    }

}
