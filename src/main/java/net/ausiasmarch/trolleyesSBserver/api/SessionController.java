package net.ausiasmarch.trolleyesSBserver.api;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    HttpSession oHttpSession;

    @GetMapping("/")
    public ResponseEntity<?> check() {
        SessionBean oSessionBean = (SessionBean) oHttpSession.getAttribute("usuario");
        if (oSessionBean == null) {
            SessionBean oSessionBean2 = new SessionBean();
            oSessionBean2.setMessage("NO SESION");
            oSessionBean2.setStatus(200);
            return new ResponseEntity<SessionBean>(oSessionBean2, HttpStatus.OK);
        } else {
            return new ResponseEntity<SessionBean>(oSessionBean, HttpStatus.OK);
        }
    }

    /*
    Queda pendiente...
    @PostMapping("/")
    public ResponseEntity<?> login(@RequestBody UsuarioBean oUsuarioBean) {
     */
    @GetMapping("/{login}/{password}")
    public ResponseEntity<?> login(@PathVariable(value = "login") String strLogin, @PathVariable(value = "password") String strPassword) {

        if (strLogin.equalsIgnoreCase("rafa")) {
            if (strPassword.equalsIgnoreCase("79063e8037fff16d297a1fe65136f1251126cddb2cc9870ecf8d653835538e85")) {
                SessionBean oSessionBean = new SessionBean();
                oSessionBean.setMessage(strLogin);
                oSessionBean.setStatus(200);
                oHttpSession.setAttribute("usuario", oSessionBean);
                return new ResponseEntity<SessionBean>(oSessionBean, HttpStatus.OK);
            }
        }
        /*
        if (oUsuarioBean.getLogin().equalsIgnoreCase("rafa")) {
            if (oUsuarioBean.getPassword().equalsIgnoreCase("rafael")) {
                SessionBean oSessionBean = new SessionBean();
                oSessionBean.setMessage(oUsuarioBean.getLogin());
                oSessionBean.setStatus(200);
                return new ResponseEntity<SessionBean>(oSessionBean, HttpStatus.OK);
            }
        }
         */
        SessionBean oSessionBean = new SessionBean();
        oSessionBean.setMessage("ERROR");
        oSessionBean.setStatus(500);
        return new ResponseEntity<SessionBean>(oSessionBean, HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> logout() {
        oHttpSession.invalidate();
        SessionBean oSessionBean = new SessionBean();
        oSessionBean.setMessage("OK");
        oSessionBean.setStatus(200);
        return new ResponseEntity<SessionBean>(oSessionBean, HttpStatus.OK);
    }

}
