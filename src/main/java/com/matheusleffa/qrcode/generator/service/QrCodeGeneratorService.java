package com.matheusleffa.qrcode.generator.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class QrCodeGeneratorService {

    private final QRCodeWriter qrCodeWriter;

    public QrCodeGeneratorService() {
        this(new QRCodeWriter());
    }

    QrCodeGeneratorService(QRCodeWriter qrCodeWriter) {
        this.qrCodeWriter = qrCodeWriter;
    }

    public byte[] generate(String content) throws WriterException, IOException {
        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

        return pngOutputStream.toByteArray();
    }
}
