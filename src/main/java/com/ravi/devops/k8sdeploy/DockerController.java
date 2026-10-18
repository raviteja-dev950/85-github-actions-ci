package com.ravi.devops.k8sdeploy;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class DockerController {

    @GetMapping("/")
    public Map<String, String> getStatus() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "K8s Ready");
        response.put("project", "84-k8s-deploy");
        response.put("tier", "9 - DevOps + Cloud");
        return response;
    }
}