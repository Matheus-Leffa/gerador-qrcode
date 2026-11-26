package com.matheusleffa.qrcode.generator.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class QrCodeGeneratorServiceTest {

    private QRCodeWriter qrCodeWriter;
    private QrCodeGeneratorService service;

    @BeforeEach
    void setUp() {
        qrCodeWriter = mock(QRCodeWriter.class);
        service = new QrCodeGeneratorService(qrCodeWriter);
    }

    @Test
    void generateReturnsEncodedPngBytes() throws Exception {
        BitMatrix bitMatrix = mock(BitMatrix.class);
        byte[] expectedPng = { (byte) 0x89, 0x50, 0x4E, 0x47 };

        when(qrCodeWriter.encode("https://example.com", BarcodeFormat.QR_CODE, 200, 200))
                .thenReturn(bitMatrix);

        try (MockedStatic<MatrixToImageWriter> matrixWriter = mockStatic(MatrixToImageWriter.class)) {
            matrixWriter.<Void>when(() -> MatrixToImageWriter.writeToStream(
                            eq(bitMatrix), eq("PNG"), any(ByteArrayOutputStream.class)))
                    .thenAnswer(invocation -> {
                        ByteArrayOutputStream outputStream = invocation.getArgument(2);
                        outputStream.write(expectedPng);
                        return null;
                    });

            byte[] result = service.generate("https://example.com");

            assertArrayEquals(expectedPng, result);
            verify(qrCodeWriter).encode("https://example.com", BarcodeFormat.QR_CODE, 200, 200);
        }
    }

    @Test
    void generatePropagatesWriterException() throws Exception {
        WriterException exception = new WriterException("Encoding failed");
        when(qrCodeWriter.encode("invalid", BarcodeFormat.QR_CODE, 200, 200))
                .thenThrow(exception);

        WriterException thrown = assertThrows(WriterException.class,
                () -> service.generate("invalid"));

        assertSame(exception, thrown);
    }

    @Test
    void generatePropagatesIoException() throws Exception {
        BitMatrix bitMatrix = mock(BitMatrix.class);
        IOException exception = new IOException("Could not write PNG");

        when(qrCodeWriter.encode("content", BarcodeFormat.QR_CODE, 200, 200))
                .thenReturn(bitMatrix);

        try (MockedStatic<MatrixToImageWriter> matrixWriter = mockStatic(MatrixToImageWriter.class)) {
            matrixWriter.when(() -> MatrixToImageWriter.writeToStream(
                            eq(bitMatrix), eq("PNG"), any(ByteArrayOutputStream.class)))
                    .thenThrow(exception);

            IOException thrown = assertThrows(IOException.class,
                    () -> service.generate("content"));

            assertSame(exception, thrown);
        }
    }
}
