# ☸️ Project 84 – K8s Deploy + Container Orchestration | Full Stack DevOps System | K8s Ready

<p align="left">

<img src="https://img.shields.io/badge/Java-21-E76F00?logo=openjdk&logoColor=white" alt="Java 21">

<img src="https://img.shields.io/badge/Spring%20Boot-4.1.1-6DB33F?logo=springboot&logoColor=white" alt="Spring Boot 4.1.1">

<img src="https://img.shields.io/badge/Docker-Ready-2496ED?logo=docker&logoColor=white" alt="Docker Ready">

<img src="https://img.shields.io/badge/Kubernetes-Ready-326CE5?logo=kubernetes&logoColor=white" alt="Kubernetes Ready">

<img src="https://img.shields.io/badge/Kubectl-Pods%202%2F2%20Running-326CE5" alt="K8s Pods">

<img src="https://img.shields.io/badge/Apache%20Tomcat-11.0.24-D4AF00?logo=apachetomcat&logoColor=white" alt="Apache Tomcat 11.0.24">

<img src="https://img.shields.io/badge/Maven-3.9+-C71A36?logo=apachemaven&logoColor=white" alt="Maven 3.9+">

<img src="https://img.shields.io/badge/Port-9494%20%7C%203000-20B000" alt="Port 9494 | 3000">

<img src="https://img.shields.io/badge/Status-K8s%20Ready-20B000" alt="K8s Ready">

</p>

---

# 📖 Project Overview

**K8s Deploy System** is Project 84 of **Tier 9 – DevOps + Cloud Deployment**, developed using **Java 21**, **Spring Boot 4.1.1**, **Docker**, **Kubernetes (Docker Desktop)**, **kubectl**, and **Apache Tomcat 11.0.24** in a **K8s orchestrated repo**.

Spring Boot REST API running on port 9494 inside Docker container now deployed to Kubernetes with 2 replicas returns live JSON `{"status":"K8s Ready","project":"84-k8s-deploy","tier":"9 - DevOps + Cloud"}`. This repo contains **Dockerfile**, **k8s/deployment.yaml**, **k8s/service.yaml**, and **Spring Boot source** - eliminating separate DevOps setup. One clone gives full K8s deployed app.

This is Project 84 of Tier 9 - After Docker (Project 83), now K8s orchestration base for CI/CD, AWS EKS, Helm.

**Bug Fixed:** `kubectl port-forward svc/k8s-service 9494:9494 Unable to listen on port 9494: bind: Only one usage of each socket address is normally permitted.` - Port 9494 already used by local Project 83 container `0.0.0.0:9494->9494/tcp`. Fixed by forwarding to different local port `kubectl port-forward svc/k8s-service 3000:9494` + `http://localhost:3000`.

**Bug Fixed:** `Failed to pull image "84-k8s-deploy:latest": rpc error: code = Unknown desc = failed to pull and unpack image: failed to resolve reference: 84-k8s-deploy:latest: not found` - Docker Desktop K8s cannot pull local image without registry. Fixed by adding `imagePullPolicy: IfNotPresent` + enabling Docker Engine `containerd image store` feature + `docker build -t 84-k8s-deploy:latest .`

**Bug Fixed:** `ERR_CONNECTION_REFUSED localhost:30094` on NodePort - Docker Desktop Windows firewall blocks NodePort direct access. Fixed by using `kubectl port-forward` method.

---

# ✨ Features

- K8s Ready status API with JSON response - `{"status":"K8s Ready",...}`
- Fully orchestrated Spring Boot app - Running inside K8s Pods, not just Docker
- Deployment with 2 replicas - `k8s-deploy-566985b7c9-qjxfr 1/1 Running` + `k8s-deploy-566985b7c9-wdwxq 1/1 Running`
- Dockerfile with `eclipse-temurin:21-jdk` base image - WORKDIR /app
- K8s Service NodePort - `9494:30094/TCP` with port-forward handling
- imagePullPolicy: IfNotPresent for local image support
- Port mapping conflict resolution - `8080 -> login page, 3000 -> K8s Ready JSON`
- Tomcat initialized with port 9494 (http) inside K8s Pod
- Started Application in 30.084 seconds (K8s Pod) vs 6.505 seconds (Docker)
- Spring Boot banner v4.1.1 inside `kubectl logs`
- Auto-healing Pods - RESTARTS 0, AGE 10m
- Verified with `kubectl get pods`, `kubectl logs`, `kubectl port-forward`, browser `localhost:3000`

---

# 🛠 Technologies Used

- Java 21.0.12.1
- Spring Boot 4.1.1
- Spring Web - REST API
- Docker Desktop - desktop-linux + containerd image store
- Kubernetes - Docker Desktop K8s v1.31+
- kubectl - v1.31
- Dockerfile - FROM eclipse-temurin:21-jdk
- K8s Deployment - replicas: 2, selector app: k8s-deploy
- K8s Service - NodePort 9494:30094
- Maven 3.9+
- Apache Tomcat 11.0.24 (Embedded) - TomcatWebServer
- JavaScript (for JSON response)
- VS Code / STS
- Windows 11 - PowerShell

---

# 📂 Project Structure - K8s Repo

```text
84-k8s-deploy
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── ravi
│   │   │           └── devops
│   │   │               └── k8sdeploy
│   │   │                   ├── Application.java
│   │   │                   │
│   │   │                   └── DockerController.java
│   │   │
│   │   └── resources
│   │       └── application.properties
│   │
│   └── test
│       └── java
│           └── com
│               └── ravi
│                   └── devops
│                       └── k8sdeploy
│                           └── ApplicationTests.java
│
├── k8s
│   ├── deployment.yaml
│   └── service.yaml
│
├── screenshots
│   ├── demo1-pods.png
│   ├── demo2-logs.png
│   ├── demo3-deployment-logs.png
│   ├── demo4-k8s-ready-3000.png
│   └── demo5-docker-ready-9494.png
│
├── Dockerfile
├── .dockerignore
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

---

# ▶ How to Run - K8s Repo

## 1⃣ Clone the Repository

```bash
git clone https://github.com/raviteja-dev950/84-k8s-deploy.git
cd 84-k8s-deploy
```

---

## 2⃣ Build JAR First (Required for Docker)

- Verify `src/main/resources/application.properties`

```properties
spring.application.name=84-k8s-deploy
server.port=9494
```

- Verify `DockerController.java`

```java
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
```

- Build:

```bash
.\mvnw clean package -DskipTests
```

Check:

```text
target/84-k8s-deploy-0.0.1-SNAPSHOT.jar
```

---

## 3⃣ Docker Build (Enable containerd store first)

Docker Desktop > Settings > General > Enable "Use containerd for pulling and storing images"

```bash
docker build --no-cache -t 84-k8s-deploy:latest .
docker images | findstr 84-k8s-deploy
```

Expected:

```text
84-k8s-deploy   latest   xxxMB
```

---

## 4⃣ K8s Deployment - Fix imagePullPolicy

Verify `k8s/deployment.yaml`:

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: k8s-deploy
spec:
  replicas: 2
  selector:
    matchLabels:
      app: k8s-deploy
  template:
    metadata:
      labels:
        app: k8s-deploy
    spec:
      containers:
      - name: k8s-deploy
        image: 84-k8s-deploy:latest
        imagePullPolicy: IfNotPresent
        ports:
        - containerPort: 9494
```

Verify `k8s/service.yaml`:

```yaml
apiVersion: v1
kind: Service
metadata:
  name: k8s-service
spec:
  type: NodePort
  selector:
    app: k8s-deploy
  ports:
  - port: 9494
    targetPort: 9494
    nodePort: 30094
```

Apply:

```bash
kubectl apply -f k8s/
kubectl get pods
```

Expected:

```text
NAME                          READY   STATUS    RESTARTS   AGE
k8s-deploy-566985b7c9-qjxfr    1/1     Running   0          10m
k8s-deploy-566985b7c9-wdwxq    1/1     Running   0          10m
```

---

## 5⃣ K8s Logs - Verify Spring Boot Inside Pod

```bash
kubectl logs deployment/k8s-deploy --tail=30
```

Expected:

```text
:: Spring Boot :: (v4.1.1)
Tomcat initialized with port 9494 (http)
Tomcat started on port 9494 (http) with context path '/'
Started Application in 30.084 seconds
```

---

## 6⃣ Access App - Port-Forward Fix

Bug: `9494 already in use` because Project 83 container uses 0.0.0.0:9494

Fix - Use 3000:

```bash
kubectl port-forward svc/k8s-service 3000:9494
```

Open:

```
http://localhost:3000
```

Expected:

```json
{
  "tier":"9 - DevOps + Cloud",
  "project":"84-k8s-deploy",
  "status":"K8s Ready"
}
```

Handling connections log:

```text
Forwarding from 127.0.0.1:3000 -> 9494
Forwarding from [::1]:3000 -> 9494
Handling connection for 3000
Handling connection for 3000
```

---

# 🔄 Flow Diagram - How K8s Works

```text
    User Laptop
        │
        ├── E:\Java-100-Projects\84-k8s-deploy
        │       ├── src -> Application.java + DockerController.java
        │       ├── Dockerfile -> FROM eclipse-temurin:21-jdk
        │       └── k8s/ -> deployment.yaml + service.yaml
        │
        ▼
    mvn clean package -> target/*.jar
        │
        ▼
    docker build -t 84-k8s-deploy:latest .
        │
        ▼
    Docker Engine (containerd store enabled)
        │
        ▼
    kubectl apply -f k8s/
        │
        ├── Deployment -> 2 Replicas
        │     ├── Pod k8s-deploy-566985b7c9-qjxfr 1/1 Running
        │     └── Pod k8s-deploy-566985b7c9-wdwxq 1/1 Running
        │           └── Tomcat 11.0.24 on 9494
        │                 └── Spring Boot 4.1.1
        │
        ├── Service -> NodePort 9494:30094
        │
        ▼
    kubectl port-forward svc/k8s-service 3000:9494
        │
        ▼
        Browser: localhost:3000
             │
             ▼
        {
          status: K8s Ready,
          project: 84-k8s-deploy,
          tier: 9 - DevOps + Cloud
        }
```

---

# 📸 Screenshots

### Demo 1 - kubectl get pods - 2/2 Running

Pods `k8s-deploy-566985b7c9-qjxfr` and `k8s-deploy-566985b7c9-wdwxq` both `1/1 Running` with `0 Restarts` - 10m AGE - Proof of replica set working.

[screenshots/demo1-pods.png]

---

### Demo 2 - kubectl logs single pod - Spring Boot Banner

Logs `k8s-deploy-566985b7c9-qjxfr --tail=20` - Spring Boot banner v4.1.1 - Java 21.0.12.1 PID 1 - Tomcat initialized port 9494 - Started in 30.084 seconds.

[screenshots/demo2-logs.png]

---

### Demo 3 - kubectl logs deployment - 2 pods handling

Logs `deployment/k8s-deploy --tail=30` - Found 2 pods, using pod/k8s-deploy-566985b7c9-qjxfr - Initializing Spring DispatcherServlet + Completed initialization in 30 ms - Proof traffic hits pods.

[screenshots/demo3-deployment-logs.png]

---

### Demo 4 - Browser K8s Ready - Port-Forward 3000 Success

Browser `localhost:3000` returns `{"tier":"9 - DevOps + Cloud","project":"84-k8s-deploy","status":"K8s Ready"}` - After fixing `bind: Only one usage of each socket address` by using 3000 instead of 9494. Main success proof for Project 84.

[screenshots/demo4-k8s-ready-3000.png]

---

### Demo 5 - Browser Docker Ready - Project 83 Still Running on 9494

Browser `localhost:9494` returns `{"status":"Docker Ready","project":"83-dockerized-app"}` - Shows Project 83 container still occupies 9494, which caused port-forward conflict - Explains why we used 3000 for K8s.

[screenshots/demo5-docker-ready-9494.png]

---

### Demo 6 - Port Forward Handling Connections

PowerShell `kubectl port-forward svc/k8s-service 3000:9494` - Forwarding from 127.0.0.1:3000 -> 9494 - Handling connection for 3000 x4 - Proof live traffic.

[screenshots/demo6-port-forward.png]

---

# 🧪 API Testing Examples

```bash
# GET K8s Ready - Browser via Port-Forward
curl http://localhost:3000

# GET Docker Ready - Old Project 83 Direct
curl http://localhost:9494

# K8s Pods
kubectl get pods

# K8s Services
kubectl get svc

# K8s Logs single pod
kubectl logs k8s-deploy-566985b7c9-qjxfr --tail=20

# K8s Logs deployment (auto pick pod)
kubectl logs deployment/k8s-deploy --tail=30

# Port-Forward
kubectl port-forward svc/k8s-service 3000:9494

# Describe pod
kubectl describe pod k8s-deploy-566985b7c9-qjxfr

# Scaling
kubectl scale deployment k8s-deploy --replicas=3
kubectl get pods

# Cleanup
kubectl delete -f k8s/
```

### Expected Response (localhost:3000) - K8s

```json
{
  "tier":"9 - DevOps + Cloud",
  "project":"84-k8s-deploy",
  "status":"K8s Ready"
}
```

### Expected Response (localhost:9494) - Docker (Project 83)

```json
{
  "status":"Docker Ready",
  "project":"83-dockerized-app",
  "tier":"9 - DevOps + Cloud"
}
```

### Expected kubectl get pods Response

```text
NAME                        READY   STATUS    RESTARTS   AGE
k8s-deploy-566985b7c9-qjxfr  1/1     Running   0          10m
k8s-deploy-566985b7c9-wdwxq  1/1     Running   0          10m
```

### Expected kubectl logs Response

```text
:: Spring Boot :: (v4.1.1)
2026-10-17T13:11:50.315Z INFO 1 --- [84-k8s-deploy] [main] com.ravi.devops.k8sdeploy.Application : Starting Application v0.0.1-SNAPSHOT using Java 21.0.12.1 with PID 1 (/app/app.jar started by root in /app)
2026-10-17T13:12:06.950Z INFO 1 --- [84-k8s-deploy] [main] o.s.boot.tomcat.TomcatWebServer : Tomcat initialized with port 9494 (http)
2026-10-17T13:12:13.626Z INFO 1 --- [84-k8s-deploy] [main] o.s.boot.tomcat.TomcatWebServer : Tomcat started on port 9494 (http) with context path '/'
2026-10-17T13:12:14.030Z INFO 1 --- [84-k8s-deploy] [main] com.ravi.devops.k8sdeploy.Application : Started Application in 30.084 seconds (process running for 37.743)
2026-10-17T13:17:22.423Z INFO 1 --- [84-k8s-deploy] [nio-9494-exec-3] o.a.c.c.C.[Tomcat].[localhost].[/] : Initializing Spring DispatcherServlet 'dispatcherServlet'
```

**Testing Steps:**

1. Run `docker build -t 84-k8s-deploy:latest .` - Should create image
2. Run `kubectl apply -f k8s/` - Should create deployment + service
3. Run `kubectl get pods` - Should show 2 pods 1/1 Running 0 Restarts
4. Run `kubectl logs deployment/k8s-deploy --tail=30` - Should show Tomcat started on port 9494
5. Run `kubectl port-forward svc/k8s-service 3000:9494` - Should show Forwarding from 127.0.0.1:3000 -> 9494
6. Open `http://localhost:3000` - Should return K8s Ready JSON
7. Check `Handling connection for 3000` in port-forward terminal - Proof traffic

---

# 🎯 Learning Outcomes

- Understanding Kubernetes architecture - Pod vs Deployment vs Service vs NodePort
- Creating deployment.yaml with replicas: 2, selector matchLabels
- Using imagePullPolicy: IfNotPresent for local Docker images without registry
- Enabling Docker Desktop containerd image store - For K8s to pull local images
- Understanding NodePort vs ClusterIP vs LoadBalancer - 9494:30094 mapping
- Handling port conflicts - `bind: Only one usage of each socket address` - Using alternate local port 3000
- Using kubectl get pods, get svc, get deployments
- Using kubectl logs with pod name and deployment name
- Using kubectl port-forward svc/k8s-service 3000:9494 - Fix for Windows NodePort firewall
- Understanding K8s auto-healing - 0 Restarts, 10m Age
- Running Spring Boot inside K8s Pod - Tomcat 11.0.24 on 9494 inside Pod
- Comparing Docker vs K8s - Project 83 Docker Ready on 9494 vs Project 84 K8s Ready on 3000
- Debugging FailedToPullImage / ImagePullBackOff errors
- Verifying K8s deployment with browser JSON response
- Preparing base for CI/CD, EKS, Helm

---

# 🚀 Future Enhancements

- 🔄 Add GitHub Actions CI - Project 85 - Auto build & push to Docker Hub + Auto deploy to K8s on git push
- ☁️ Deploy to AWS EKS - Project 86 - Run this deployment on EKS cluster
- 📦 Add Helm Chart - Project 87 - Helm install for deployment + service
- 🐬 Add MySQL with K8s Secret + ConfigMap - Project 88 - Connect Pod to MySQL Pod
- 📊 Add Prometheus + Grafana Monitoring - Project 89 - Monitor Pods CPU/Memory
- ⚖️ Add Ingress + Load Balancer - Project 90 - Nginx Ingress for 84-k8s-deploy
- 🏗️ Add Terraform EKS Script - Project 91 - IaC for EKS infrastructure
- 🔵🟢 Add Blue-Green Deploy - Project 92 - Zero-downtime deployment with 2 deployments
- 📉 Optimize Dockerfile - Multi-stage build - Reduce image from 350MB to 150MB
- 🔍 Add K8s Liveness & Readiness Probes - HTTP GET / on port 9494
- 📝 Add K8s HPA - Horizontal Pod Autoscaler - Scale 2 to 10 pods on CPU
- 🔐 Add K8s Secrets for application.properties

---

# 👨💻 Author

Ravi Teja

Java Full Stack Developer

100 Java Full Stack Projects Challenge

Project 84 / 100

Tier 9 – DevOps + Cloud Deployment

---

## ⭐ Support

If you found this project helpful, consider giving it a ⭐ Star on GitHub.

**Single Repo:** [84-k8s-deploy](https://github.com/raviteja-dev950/84-k8s-deploy)

**Docker Image:** `84-k8s-deploy:latest`

**K8s Deployment:** `k8s-deploy` - 2 replicas - Port 9494

**K8s Service:** `k8s-service` - NodePort 9494:30094

**Port-Forward:** `kubectl port-forward svc/k8s-service 3000:9494` -> `http://localhost:3000`

**Build:** `docker build -t 84-k8s-deploy:latest .`

**Apply:** `kubectl apply -f k8s/`

