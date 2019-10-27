package com.storage.app.service;

import com.storage.app.model.Asset;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/** @author choang on 10/24/19 */
public interface AssetService {
  Asset createAsset(
      String fileName, String description, MultipartFile multipartFile, String username);

  Asset getAsset(long id);

  Asset deleteAsset(long id);

  Asset deleteAsset(long id, String username);

  List<Asset> listAssets(String username);
}
