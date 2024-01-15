package weavers.siltarae.integration.member;

import org.junit.jupiter.api.Test;
import weavers.siltarae.integration.BaseIntegrationTest;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;


class MemberIntegrationTest extends BaseIntegrationTest {

    @Test
    void 최대크기를_초과하는_이미지를_업로드하면_예외가_발생한다() {
        byte[] bytes = new byte[1024 * 1024 * 6];

        given().log().all()
                .header(AUTHORIZATION, tokenPair.getAccessToken())
                .cookie("refresh-token", tokenPair.getRefreshToken())
                .contentType(MULTIPART)
                .multiPart("file", "big_image.png", bytes, "image/png")
        .when().post("/api/v1/member/image")
        .then().log().all()
                .statusCode(PAYLOAD_TOO_LARGE.value());
    }

    @Test
    void 이미지가_비어있는_경우_예외가_발생한다() {

        given().log().all()
                .header(AUTHORIZATION, tokenPair.getAccessToken())
                .cookie("refresh-token", tokenPair.getRefreshToken())
                .contentType(MULTIPART)
                .multiPart("file", "empty_image.png", "image/png")
        .when().post("/api/v1/member/image")
        .then().log().all()
                .statusCode(BAD_REQUEST.value());
    }
}