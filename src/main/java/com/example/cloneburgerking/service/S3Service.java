package com.example.cloneburgerking.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.example.cloneburgerking.entity.Menu;
import com.example.cloneburgerking.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;
    private MenuRepository menuRepository;

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        System.out.println("---------S3 Service-----------");
        String folder = "";
        String fileName = multipartFile.getOriginalFilename();

        //파일 형식 구하기
        String ext = fileName.split("\\.")[1];
        String contentType = "";

        //content type을 지정해서 올려주지 않으면 자동으로 "application/octet-stream"으로 고정이 되서 링크 클릭시 웹에서 열리는게 아니라 자동 다운이 시작됨.
        switch (ext) {
            case "jpeg" -> {
                contentType = "image/jpeg";
                folder = "img/";
            }
            case "png" -> {
                contentType = "image/png";
                folder = "img/";
            }
            case "txt" -> {
                contentType = "text/plain";
                folder = "txt/";
            }
            case "csv" -> {
                contentType = "text/csv";
                folder = "csv/";
            }
        }

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);

            amazonS3.putObject(new PutObjectRequest(bucket,folder+fileName, multipartFile.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (SdkClientException e) {
            e.printStackTrace();
        }


        //object 정보 가져오기
        ListObjectsV2Result listObjectsV2Result = amazonS3.listObjectsV2(bucket);
        List<S3ObjectSummary> objectSummaries = listObjectsV2Result.getObjectSummaries();

        for (S3ObjectSummary object : objectSummaries) {
            System.out.println("object = " + object.toString());
        }
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    public List<String> allFolders() {
        ListObjectsV2Request listObjectsV2Request = new ListObjectsV2Request().withBucketName(bucket);
        String prefix = listObjectsV2Request.getDelimiter();
        System.out.println(prefix);
        return null;
    }

//    private byte[] modifyFile(byte[] bytes) {
//        // 파일 수정 코드 작성
//        return bytes;
//    }

//    public String getFileKey(Long id) {
//        Menu menu = menuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid menu id"));
//        return menu.getKey(); // 파일의 S3 Key 값 반환
//    }
//
//    public String editFile(String key, MultipartFile file) throws IOException {
//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentType(file.getContentType());
//        metadata.setContentLength(file.getSize());
//
//        amazonS3.putObject(new PutObjectRequest(bucket, key, file.getInputStream(), metadata)
//                .withCannedAcl(CannedAccessControlList.PublicRead));
//
//        return amazonS3.getUrl(bucket, key).toString();
//    }
//
//    public void deleteFile(String key) {
//        amazonS3.deleteObject(bucket, key);
//    }
}
