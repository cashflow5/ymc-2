package com.belle.infrastructure.uploadImages;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class MultiPartFileBean {

    private List<MultipartFile> productImages;

    public void setFiles(List<MultipartFile> files) {
        this.productImages = files;
    }

    public List<MultipartFile> getFiles() {
        return productImages;
    }
}
