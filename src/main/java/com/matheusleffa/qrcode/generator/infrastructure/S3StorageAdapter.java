package com.matheusleffa.qrcode.generator.infrastructure;

import com.matheusleffa.qrcode.generator.ports.StoragePort;

public class S3StorageAdapter implements StoragePort {
    @Override
    public String uploadFile(byte[] fileData, String fileName, String contentType) {
        return "";
    }
}
