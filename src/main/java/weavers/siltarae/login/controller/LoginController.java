package weavers.siltarae.login.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import weavers.siltarae.login.service.LoginService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login/{socialType}")
    public ResponseEntity<Void> login(@PathVariable final String socialType,
                                               @RequestParam final String code) {

        loginService.login(socialType, code);
        return ResponseEntity.ok().build();
    }
}
