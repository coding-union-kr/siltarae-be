package weavers.siltarae.integration.member;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import weavers.siltarae.integration.BaseIntegrationTest;
import weavers.siltarae.login.dto.response.TokenPair;

import static io.restassured.http.ContentType.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;


class MemberIntegrationTest extends BaseIntegrationTest {

    @Test
    void 최대크기를_초과하는_이미지를_업로드하면_예외가_발생한다() {
        // given
        byte[] bytes = new byte[1024 * 1024 * 6];

        // when
        ExtractableResponse<Response> response = requestUpdateMemberImage(tokenPair, bytes);

        // then
        assertThat(response.statusCode()).isEqualTo(PAYLOAD_TOO_LARGE.value());
    }

    @Test
    void 이미지가_비어있는_경우_예외가_발생한다() {
        // given

        // when
        ExtractableResponse<Response> response = requestUpdateEmptyMemberImage(tokenPair);

        // then
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.value());
    }

    private static ExtractableResponse<Response> requestUpdateMemberImage(TokenPair tokenPair, byte[] bytes) {
        return RestAssured
                .given().log().all()
                .header(AUTHORIZATION, "Bearer " + tokenPair.getAccessToken())
                .cookie("refresh-token", tokenPair.getRefreshToken())
                .contentType(MULTIPART)
                .multiPart("file", "big_image.png", bytes, "image/png")
                .when().post("/api/v1/member/image")
                .then().log().all()
                .extract();
    }

    private static ExtractableResponse<Response> requestUpdateEmptyMemberImage(TokenPair tokenPair) {
        return RestAssured
                .given().log().all()
                .header(AUTHORIZATION, "Bearer " + tokenPair.getAccessToken())
                .cookie("refresh-token", tokenPair.getRefreshToken())
                .contentType(MULTIPART)
                .multiPart("file", "empty_image.png", "image/png")
                .when().post("/api/v1/member/image")
                .then().log().all()
                .extract();
    }
}