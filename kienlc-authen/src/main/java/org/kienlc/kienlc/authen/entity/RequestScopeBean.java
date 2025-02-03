package org.kienlc.kienlc.authen.entity;

import jakarta.persistence.Column;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class RequestScopeBean {
    public RequestScopeBean() {
        System.out.println("RequestScopeBean() created");
    }

    public String getHashCode() {
        return "Process request scope bean" + this.hashCode();
    }
}
