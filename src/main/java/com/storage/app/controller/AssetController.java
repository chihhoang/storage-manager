package com.storage.app.controller;

import com.storage.app.Utils;
import com.storage.app.model.Asset;
import com.storage.app.security.JwtTokenProvider;
import com.storage.app.service.AmazonS3Service;
import com.storage.app.service.AssetService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/** @author choang on 10/23/19 */
@RestController
@RequestMapping("/assets")
@Slf4j
@RequiredArgsConstructor
public class AssetController {
  private final AmazonS3Service amazonS3Service;
  private final JwtTokenProvider tokenProvider;
  private final AssetService assetService;

  @PostMapping("/upload")
  public ResponseEntity<Asset> uploadAsset(
      @RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
    String username = tokenProvider.getUserLogin(tokenProvider.resolveToken(request));

    String fileName = Utils.getUUID(7);

    // TODO refactor to AssetService
    return ResponseEntity.ok(amazonS3Service.uploadFile(fileName, file, username));
  }

  @GetMapping("/users")
  public ResponseEntity<List<Asset>> listAssets(HttpServletRequest request) {
    String username = tokenProvider.getUserLogin(tokenProvider.resolveToken(request));

    return ResponseEntity.ok(assetService.listAssets(username));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Asset> deleteAsset(@PathVariable long id, HttpServletRequest request) {
    String username = tokenProvider.getUserLogin(tokenProvider.resolveToken(request));

    // Verify the actual user or admin before delete, pass username
    assetService.deleteAsset(id);

    return ResponseEntity.ok(Asset.builder().id(id).build());
  }
}
