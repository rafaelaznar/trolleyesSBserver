package net.ausiasmarch.trolleyesSBserver.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {

    @GetMapping("/check")
    public ResponseEntity<?> check() {
        SessionBean oSessionBean=new SessionBean();
        oSessionBean.setSaludo("Hola mundo");
        oSessionBean.setCodigo(200);
        return new ResponseEntity<SessionBean>(oSessionBean, HttpStatus.OK);
    }

}
