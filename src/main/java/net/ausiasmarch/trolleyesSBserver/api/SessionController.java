package net.ausiasmarch.trolleyesSBserver.api;

import net.ausiasmarch.trolleyesSBserver.bean.UsuarioBean;
import net.ausiasmarch.trolleyesSBserver.bean.ResponseBean;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    HttpSession oHttpSession;

    @GetMapping("/private")
    public ResponseEntity<?> secret() {
        ResponseBean oSessionBean = (ResponseBean) oHttpSession.getAttribute("usuario");
        if (oSessionBean == null) {
            ResponseBean oSessionBean2 = new ResponseBean();
            oSessionBean2.setMessage("NO SESSION");
            oSessionBean2.setStatus(403);
            return new ResponseEntity<ResponseBean>(oSessionBean2, HttpStatus.OK);
        } else {
            ResponseBean oSessionBean3 = new ResponseBean();
            oSessionBean3.setMessage("HOLA MUNDO SECRETO");
            oSessionBean3.setStatus(200);
            return new ResponseEntity<ResponseBean>(oSessionBean3, HttpStatus.OK);
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> check() {
        ResponseBean oSessionBean = (ResponseBean) oHttpSession.getAttribute("usuario");
        if (oSessionBean == null) {
            ResponseBean oSessionBean2 = new ResponseBean();
            oSessionBean2.setMessage("NO SESSION");
            oSessionBean2.setStatus(403);
            return new ResponseEntity<ResponseBean>(oSessionBean2, HttpStatus.OK);
        } else {
            return new ResponseEntity<ResponseBean>(oSessionBean, HttpStatus.OK);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> login(@RequestBody UsuarioBean oUsuarioBean) {
        if (oUsuarioBean.getLogin().equalsIgnoreCase("rafa")) {
            if (oUsuarioBean.getPassword().equalsIgnoreCase("79063e8037fff16d297a1fe65136f1251126cddb2cc9870ecf8d653835538e85")) {
                ResponseBean oSessionBean = new ResponseBean();
                oSessionBean.setMessage(oUsuarioBean.getLogin());
                oSessionBean.setStatus(200);
                oHttpSession.setAttribute("usuario", oSessionBean);
                return new ResponseEntity<ResponseBean>(oSessionBean, HttpStatus.OK);
            }
        }
        ResponseBean oSessionBean = new ResponseBean();
        oSessionBean.setMessage("ERROR");
        oSessionBean.setStatus(500);
        return new ResponseEntity<ResponseBean>(oSessionBean, HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> logout() {
        oHttpSession.invalidate();
        ResponseBean oSessionBean = new ResponseBean();
        oSessionBean.setMessage("Bye");
        oSessionBean.setStatus(200);
        return new ResponseEntity<ResponseBean>(oSessionBean, HttpStatus.OK);
    }

}
