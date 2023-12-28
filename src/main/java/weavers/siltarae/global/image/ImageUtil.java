package weavers.siltarae.global.image;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import weavers.siltarae.global.exception.BadRequestException;
import weavers.siltarae.global.image.domain.Image;

import java.io.IOException;
import java.io.InputStream;

import static weavers.siltarae.global.exception.ExceptionCode.*;

@Component
@RequiredArgsConstructor
public class ImageUtil {

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadImage(String folder, Image image) throws IOException {
        String path = folder + image.getName();
        ObjectMetadata metadata = new ObjectMetadata();

        metadata.setContentType(image.getContentType());
        metadata.setContentLength(image.getContentLength());

        try {
            InputStream inputStream = image.getFile().getInputStream();
            s3Client.putObject(bucket, path, inputStream, metadata);
        } catch(AmazonServiceException e) {
            throw new BadRequestException(INVALID_IMAGE_PATH);
        }

        return s3Client.getUrl(bucket, path).toString();
    }

    public void deleteImage(String folder, String imageName) {
        String path = folder + imageName;

        try {
            s3Client.deleteObject(bucket, path);
        } catch(AmazonServiceException e) {
            throw new BadRequestException(INVALID_IMAGE_PATH);
        }
    }
}
