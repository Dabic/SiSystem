package com.se.source.admin_panel.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.annotation.security.RolesAllowed;

@Component
public class Test {
    @PreAuthorize("hasAuthority('ROLE_STUDENT')")
    public void cao() {
        Logger logger = LoggerFactory.getLogger(Test.class);
        logger.error("CAO BRE");
    }
}
