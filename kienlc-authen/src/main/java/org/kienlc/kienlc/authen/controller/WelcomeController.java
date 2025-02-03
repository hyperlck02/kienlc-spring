package org.kienlc.kienlc.authen.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.kienlc.kienlc.authen.entity.RequestScopeBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    private final RequestScopeBean requestScopeBean;

    public WelcomeController(RequestScopeBean requestScopeBean) {
        this.requestScopeBean = requestScopeBean;
    }

    @GetMapping("")
    public String welcome() {
        return "welcome kienlc";
    }

    @GetMapping("/csrf")
    public CsrfToken getToken(HttpServletRequest request) {
        return request.getAttribute("_csrf") == null ? null : (CsrfToken) request.getAttribute("_csrf");
    }

    @GetMapping("/test-scope")
    public String testScope() {
        return requestScopeBean.getHashCode();
    }
}
