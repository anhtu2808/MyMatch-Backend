package com.mymatch.service;

import java.io.InputStream;

import com.mymatch.dto.request.material.MaterialItemUploadRequest;
import com.mymatch.dto.response.filemanager.FileDownloadResponse;
import com.mymatch.dto.response.material.MaterialItemPreviewResponse;

public interface MaterialItemService {
    public MaterialItemPreviewResponse uploadMaterialItem(MaterialItemUploadRequest request, InputStream inputStream)
            throws Exception;

    FileDownloadResponse downloadMaterialItem(Long materialItemId);
}
