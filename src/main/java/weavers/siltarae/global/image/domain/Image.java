package weavers.siltarae.global.image.domain;

import lombok.Getter;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import weavers.siltarae.global.exception.BadRequestException;

import java.util.UUID;

import static weavers.siltarae.global.exception.ExceptionCode.*;

@Getter
public class Image {

    private static final String EXTENSION_DELIMITER = ".";

    private final MultipartFile file;
    private final String name;

    public Image(MultipartFile file) {
        validateEmptyFile(file);
        validateImageFile(file);
        this.file = file;
        this.name = createUUIDName(file);
    }

    private void validateEmptyFile(MultipartFile file) {
        if(file == null || file.isEmpty()) {
            throw new BadRequestException(INVALID_IMAGE_FILE);
        }
    }

    private void validateImageFile(MultipartFile file) {
        if(file.getContentType() == null || !file.getContentType().startsWith("image")) {
            throw new BadRequestException(INVALID_IMAGE_FILE);
        }
    }

    private String createUUIDName(MultipartFile image) {
        String name = UUID.randomUUID().toString();
        String fileExtension = EXTENSION_DELIMITER + FilenameUtils.getExtension(image.getOriginalFilename());

        return name + fileExtension;
    }

    public String getContentType() {
        return this.file.getContentType();
    }

    public Long getContentLength() {
        return this.file.getSize();
    }
}
