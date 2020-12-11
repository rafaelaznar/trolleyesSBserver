package net.ausiasmarch.trolleyesSBserver.api;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import net.ausiasmarch.trolleyesSBserver.entity.FileEntity;
import net.ausiasmarch.trolleyesSBserver.repository.FileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    HttpSession oSession;

    @Autowired
    protected FileRepository oFileRepository;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            BufferedImage oBufferedImage = createImageFromBytes(file.getBytes());
            if (file.getBytes().length > 500000) {
                FileEntity img2 = new FileEntity(file.getOriginalFilename(), file.getContentType());
                //--
                BufferedImage scaledImg = createThumb(oBufferedImage, 800, 800);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(scaledImg, "jpg", baos);
                baos.flush();
                byte[] imageInByte = baos.toByteArray();
                baos.close();
                //--
                img2.setFile(new javax.sql.rowset.serial.SerialBlob(imageInByte));
                return new ResponseEntity<Long>(oFileRepository.save(img2).getId(), HttpStatus.OK);
            } else {
                FileEntity img = new FileEntity(file.getOriginalFilename(), file.getContentType());
                img.setFile(new javax.sql.rowset.serial.SerialBlob(file.getBytes()));                
                return new ResponseEntity<Long>(oFileRepository.save(img).getId(), HttpStatus.OK);
            }
        } catch (IOException ex) {
            return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (SQLException ex) {
            return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }        
    }

    public BufferedImage createThumb(BufferedImage in, int w, int h) {
        // scale w, h to keep aspect constant
        double outputAspect = 1.0 * w / h;
        double inputAspect = 1.0 * in.getWidth() / in.getHeight();
        if (outputAspect < inputAspect) {
            // width is limiting factor; adjust height to keep aspect
            h = (int) (w / inputAspect);
        } else {
            // height is limiting factor; adjust width to keep aspect
            w = (int) (h * inputAspect);
        }
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bi.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(in, 0, 0, w, h, null);
        g2.dispose();
        return bi;
    }

    private BufferedImage createImageFromBytes(byte[] imageData) {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        try {
            return ImageIO.read(bais);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(path = {"/get/{name}"})
    public ResponseEntity<?> getImageByName(@PathVariable("name") String imageName) throws IOException {
        try {
            final Optional<FileEntity> retrievedImage = oFileRepository.findByName(imageName);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(retrievedImage.get().getType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + retrievedImage.get().getName() + "\"")
                    .body(new ByteArrayResource(retrievedImage.get().getFile().getBytes(1, (int) retrievedImage.get().getFile().length())));
        } catch (SQLException ex) {
            return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<?> getImageById(@PathVariable("id") Long id) throws IOException {
        try {
            final Optional<FileEntity> retrievedImage = oFileRepository.findById(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(retrievedImage.get().getType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + retrievedImage.get().getName() + "\"")
                    .body(new ByteArrayResource(retrievedImage.get().getFile().getBytes(1, (int) retrievedImage.get().getFile().length())));
        } catch (SQLException ex) {
            return new ResponseEntity<Boolean>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
