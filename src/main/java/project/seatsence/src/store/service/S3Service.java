package project.seatsence.src.store.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    @Value("${AWS_S3_BUCKET}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;

    // MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
    public List<String> upload(List<MultipartFile> files, String dirName, Long storeId)
            throws IOException {
        List<String> imagePathList = new ArrayList<>();
        int index = 1;
        for (MultipartFile file : files) {
            String originalName = file.getOriginalFilename(); // 파일 이름
            assert originalName != null;
            String extension = originalName.split("\\.")[1]; // 파일 확장자
            String fileName = storeId + "_" + index + "." + extension;
            String filePath = dirName + "/" + fileName;
            long size = file.getSize(); // 파일 크기

            ObjectMetadata objectMetaData = new ObjectMetadata();
            objectMetaData.setContentType(file.getContentType());
            objectMetaData.setContentLength(size);

            // S3에 업로드
            amazonS3Client.putObject(
                    new PutObjectRequest(bucket, filePath, file.getInputStream(), objectMetaData)
                            .withCannedAcl(CannedAccessControlList.PublicRead));

            String imagePath = amazonS3Client.getUrl(bucket, filePath).toString(); // 접근가능한 URL 가져오기
            imagePathList.add(imagePath);
            index++;
        }
        return imagePathList;
    }

    public void deleteOriginImages(Long storeId) {
        String prefix = storeId + "_";
        ObjectListing objectListing = amazonS3Client.listObjects(bucket, prefix);

        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            amazonS3Client.deleteObject(bucket, objectSummary.getKey());
        }
    }
}
