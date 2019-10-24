package com.storage.app.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.storage.app.config.AwsProperties;
import com.storage.app.exception.SystemException;
import com.storage.app.model.Asset;
import com.storage.app.model.User;
import com.storage.app.repository.AssetRepository;
import com.storage.app.repository.UserRepository;
import com.storage.app.service.AmazonS3Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/** @author choang on 10/23/19 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AmazonS3ServiceImpl implements AmazonS3Service {
  private final AmazonS3 amazonS3;
  private final AwsProperties awsProperties;
  private final UserRepository userRepository;
  private final AssetRepository assetRepository;

  @Override
  public Asset uploadFile(String fileName, String filePath, String username) {
    User user =
        userRepository
            .findOneByLogin(username)
            .orElseThrow(
                () ->
                    new SystemException(
                        "Unable to find username " + username, HttpStatus.BAD_REQUEST));

    Path p = Paths.get(filePath);
    String rootFileName = p.getFileName().toString();

    fileName = resolveFileName(username, fileName, rootFileName);

    PutObjectResult putObjectResult =
        amazonS3.putObject(
            new PutObjectRequest(awsProperties.getS3Bucket(), fileName, filePath)
                .withCannedAcl(CannedAccessControlList.PublicRead));

    String s3Url = String.format("%s/%s", awsProperties.getEndpointUrl(), fileName);

    return assetRepository.save(
        Asset.builder()
            .s3Url(s3Url)
            .version(putObjectResult.getVersionId())
            .user(user)
            .createdBy(username)
            .lastModifiedBy(username)
            .build());
  }

  private String resolveFileName(String username, String fileName, String rootFileName) {
    return String.format("%s/%s-%s", username, fileName, rootFileName);
  }

  @Override
  public Asset uploadFile(String fileName, MultipartFile multipartFile, String username) {
    User user =
        userRepository
            .findOneByLogin(username)
            .orElseThrow(
                () ->
                    new SystemException(
                        "Unable to find username " + username, HttpStatus.BAD_REQUEST));

    fileName = resolveFileName(username, fileName, multipartFile.getOriginalFilename());

    File file = convertMultipartToFile(multipartFile);

    PutObjectResult putObjectResult =
        amazonS3.putObject(
            new PutObjectRequest(awsProperties.getS3Bucket(), fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));

    file.delete();

    String s3Url = String.format("%s/%s", awsProperties.getEndpointUrl(), fileName);

    return assetRepository.save(
        Asset.builder()
            .fileName(fileName)
            .s3Url(s3Url)
            .version(putObjectResult.getVersionId())
            .user(user)
            .createdBy(username)
            .lastModifiedBy(username)
            .build());
  }

  private File convertMultipartToFile(MultipartFile multipartFile) {
    File file = new File(multipartFile.getOriginalFilename());

    try (FileOutputStream fos = new FileOutputStream(file)) {
      fos.write(multipartFile.getBytes());
    } catch (IOException ex) {
      log.error("Error getting file from system", ex);
      throw new SystemException(
          "Error getting file from system: " + multipartFile.getOriginalFilename(),
          HttpStatus.BAD_REQUEST);
    }
    return file;
  }

  @Override
  public Asset downloadFile(String fileName) {
    return null;
  }

  @Override
  public Asset deleteFile(String fileName) {
    try {
      amazonS3.deleteObject(new DeleteObjectRequest(awsProperties.getS3Bucket(), fileName));
    } catch (AmazonServiceException ex) {
      log.error("Error getting file from system", ex);
      throw new SystemException("Error deleting file from S3: " + fileName, HttpStatus.BAD_REQUEST);
    }

    return Asset.builder().fileName(fileName).build();
  }

  @Override
  public List<Asset> listFiles(String username) {
    return null;
  }
}
