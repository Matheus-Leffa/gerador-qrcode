package com.matheusleffa.qrcode.generator.controller;

import com.google.zxing.WriterException;
import com.matheusleffa.qrcode.generator.dto.QrCodeGenerateRequest;
import com.matheusleffa.qrcode.generator.service.QrCodeGeneratorService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/qrcode")
public class QrCodeController {

    private final QrCodeGeneratorService qrCodeGeneratorService;

    public QrCodeController(QrCodeGeneratorService qrCodeGeneratorService) {
        this.qrCodeGeneratorService = qrCodeGeneratorService;
    }


    @PostMapping(produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generate(@RequestBody QrCodeGenerateRequest request){
        try {
            byte[] qrCode = this.qrCodeGeneratorService.generate(request.content());

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(qrCode);
        } catch (WriterException | IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
