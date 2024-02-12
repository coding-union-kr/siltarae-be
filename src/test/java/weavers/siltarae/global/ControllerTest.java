package weavers.siltarae.global;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import weavers.siltarae.login.AuthArgumentResolver;
import weavers.siltarae.login.infrastructure.TokenProvider;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public abstract class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected AuthArgumentResolver authArgumentResolver;

    @MockBean
    protected TokenProvider tokenProvider;

    protected static String ACCESS_TOKEN = "access-token";
    protected static String REFRESH_TOKEN = "refresh-token";

    @BeforeEach
    void setUp() {
        given(authArgumentResolver.resolveArgument(any(MethodParameter.class), any(ModelAndViewContainer.class), any(NativeWebRequest.class), any(WebDataBinderFactory.class)))
                .willReturn(1L);
    }
}
