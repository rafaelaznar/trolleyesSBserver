package net.ausiasmarch.trolleyesSBserver.api;

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
        SessionBean oSessionBean = (SessionBean) oHttpSession.getAttribute("usuario");
        if (oSessionBean == null) {
            SessionBean oSessionBean2 = new SessionBean();
            oSessionBean2.setMessage("NO SESSION");
            oSessionBean2.setStatus(403);
            return new ResponseEntity<SessionBean>(oSessionBean2, HttpStatus.OK);
        } else {
            SessionBean oSessionBean3 = new SessionBean();
            oSessionBean3.setMessage("HOLA MUNDO SECRETO");
            oSessionBean3.setStatus(200);
            return new ResponseEntity<SessionBean>(oSessionBean3, HttpStatus.OK);
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> check() {
        SessionBean oSessionBean = (SessionBean) oHttpSession.getAttribute("usuario");
        if (oSessionBean == null) {
            SessionBean oSessionBean2 = new SessionBean();
            oSessionBean2.setMessage("NO SESSION");
            oSessionBean2.setStatus(403);
            return new ResponseEntity<SessionBean>(oSessionBean2, HttpStatus.OK);
        } else {
            return new ResponseEntity<SessionBean>(oSessionBean, HttpStatus.OK);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> login(@RequestBody UsuarioBean oUsuarioBean) {
        if (oUsuarioBean.getLogin().equalsIgnoreCase("rafa")) {
            if (oUsuarioBean.getPassword().equalsIgnoreCase("79063e8037fff16d297a1fe65136f1251126cddb2cc9870ecf8d653835538e85")) {
                SessionBean oSessionBean = new SessionBean();
                oSessionBean.setMessage(oUsuarioBean.getLogin());
                oSessionBean.setStatus(200);
                oHttpSession.setAttribute("usuario", oSessionBean);
                return new ResponseEntity<SessionBean>(oSessionBean, HttpStatus.OK);
            }
        }
        SessionBean oSessionBean = new SessionBean();
        oSessionBean.setMessage("ERROR");
        oSessionBean.setStatus(500);
        return new ResponseEntity<SessionBean>(oSessionBean, HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> logout() {
        oHttpSession.invalidate();
        SessionBean oSessionBean = new SessionBean();
        oSessionBean.setMessage("Bye");
        oSessionBean.setStatus(200);
        return new ResponseEntity<SessionBean>(oSessionBean, HttpStatus.OK);
    }

}
