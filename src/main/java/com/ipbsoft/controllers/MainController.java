package com.ipbsoft.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

@RestController
public class MainController {

    @PermitAll
    @RequestMapping(value = "/resources/public")
    public String publicResource() {
        return "Public resources";
    }

    @RolesAllowed({"USER", "ADMIN"})
    @RequestMapping(value = "/resources/private")
    public String privateResource() {
        return "Private resources";
    }

    @RolesAllowed("ADMIN")
    @RequestMapping(value = "/resources/admin")
    public String adminResource() {
        return "Admin resources";
    }

    @DenyAll
    @RequestMapping(value = "/resource/secret")
    public String secret() {
        return "Secret";
    }
}
