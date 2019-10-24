package com.storage.app.service;

import com.storage.app.model.Asset;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/** @author choang on 10/23/19 */
public interface AmazonS3Service {
  Asset uploadFile(String fileName, String filePath, String username);

  Asset uploadFile(String fileName, MultipartFile multipartFile, String username);

  Asset downloadFile(String fileName);

  List<Asset> listFiles(String username);
}
