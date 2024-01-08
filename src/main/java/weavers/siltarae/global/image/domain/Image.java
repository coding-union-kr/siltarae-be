package weavers.siltarae.global.image.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import weavers.siltarae.global.exception.BadRequestException;

import java.util.UUID;

import static weavers.siltarae.global.exception.ExceptionCode.*;

@Slf4j
@Getter
public class Image {

    private static final String EXTENSION_DELIMITER = ".";

    private final MultipartFile file;
    private final String name;

    public Image(MultipartFile file) {
        validateEmptyImage(file);
        this.file = file;
        this.name = createUUIDName(file);
    }

    public void validateEmptyImage(MultipartFile image) {
        if(image.isEmpty() || !image.getContentType().startsWith("image")) {
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
