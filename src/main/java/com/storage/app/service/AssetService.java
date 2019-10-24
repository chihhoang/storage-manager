package com.storage.app.service;

import com.storage.app.model.Asset;
import java.util.List;

/** @author choang on 10/24/19 */
public interface AssetService {
  Asset getAsset(long id);

  Asset deleteAsset(long id);

  Asset deleteAsset(long id, String username);

  List<Asset> listAssets(String username);
}
