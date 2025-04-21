# Keycloak JWT Validator 

[<img alt="Keycloak" src="https://img.shields.io/badge/Keycloak-26.1.4-0071C1.svg?logo=keycloak">](https://www.keycloak.org/) 
[<img alt="Spring Boot" src="https://img.shields.io/badge/Spring Boot-3.4.4-6DB33F.svg?logo=springboot">](<https://spring.io/projects/spring-boot>) 
[<img alt="React" src="https://img.shields.io/badge/React-19.1.0-61DAFB?logo=react&logoColor=white">](https://react.dev/) 
[<img alt="Gradle" src="https://img.shields.io/badge/Gradle-8.13-02303A.svg?logo=gradle">](https://gradle.org/)



### 🧰 Tech & Tools

<a href="https://www.java.com/en/"><img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white"/></a>
<a href="https://openjdk.org/projects/jdk/17/"><img src="https://img.shields.io/badge/Java 17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/></a>
<a href="https://openjdk.org/projects/jdk/21/"><img src="https://img.shields.io/badge/Java 21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/></a>
<a href="https://spring.io/projects/spring-boot"><img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/></a>
<a href="https://projectlombok.org/"><img src="https://img.shields.io/badge/Lombok-FF0000?style=for-the-badge&logoColor=white"/></a>
<a href="https://maven.apache.org/"><img src="https://img.shields.io/badge/Apache Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white"/></a>
<a href="https://jakarta.ee/"><img src="https://img.shields.io/badge/Java EE-007396?style=for-the-badge&logo=java&logoColor=white"/></a>
<a href="https://hibernate.org/jpa/"><img src="https://img.shields.io/badge/JPA-59666C?style=for-the-badge&logo=hibernate&logoColor=white"/></a>
<a href="https://jakarta.ee/specifications/faces/"><img src="https://img.shields.io/badge/JSF-2C2255?style=for-the-badge&logoColor=white"/></a>
<a href="https://jakarta.ee/specifications/cdi/"><img src="https://img.shields.io/badge/CDI-0099CC?style=for-the-badge&logoColor=white"/></a>
<a href="https://jakarta.ee/specifications/transactions/"><img src="https://img.shields.io/badge/JTA-7B4F9A?style=for-the-badge&logoColor=white"/></a>
<a href="https://www.mongodb.com/"><img src="https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white"/></a>
<a href="https://kafka.apache.org/"><img src="https://img.shields.io/badge/Apache Kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white"/></a>
<a href="https://kafka.apache.org/documentation/#kraft"><img src="https://img.shields.io/badge/KRaft-FF6F00?style=for-the-badge&logo=apachekafka&logoColor=white"/></a>
<a href="https://oauth.net/2/"><img src="https://img.shields.io/badge/OAuth 2.0-0064A5?style=for-the-badge&logo=oauth&logoColor=white"/></a>
<a href="https://www.docker.com/"><img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"/></a>
<a href="https://kubernetes.io/"><img src="https://img.shields.io/badge/Kubernetes-326CE5?style=for-the-badge&logo=kubernetes&logoColor=white"/></a>
<a href="https://github.com/thecoderscorner/boot-faces"><img src="https://img.shields.io/badge/Boot Faces-0E76A8?style=for-the-badge&logoColor=white"/></a>
<a href="https://www.primefaces.org/"><img src="https://img.shields.io/badge/PrimeFaces-205081?style=for-the-badge&logo=primefaces&logoColor=white"/></a>
<a href="https://www.payara.fish/"><img src="https://img.shields.io/badge/Payara Server-00AEEF?style=for-the-badge&logoColor=white"/></a>
<a href="https://code.visualstudio.com/"><img src="https://img.shields.io/badge/VS Code-007ACC?style=for-the-badge&logo=visualstudiocode&logoColor=white"/></a>
<a href="https://www.jetbrains.com/idea/"><img src="https://img.shields.io/badge/IntelliJ IDEA-000000?style=for-the-badge&logo=intellijidea&logoColor=white"/></a>
<a href="https://www.python.org/"><img src="https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=python&logoColor=white"/></a>
<a href="https://www.djangoproject.com/"><img src="https://img.shields.io/badge/Django-092E20?style=for-the-badge&logo=django&logoColor=white"/></a>
<a href="https://www.thymeleaf.org/"><img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logoColor=white"/></a>
<a href="https://site.mockito.org/"><img src="https://img.shields.io/badge/Mockito-0A0A0A?style=for-the-badge&logo=mockito&logoColor=white"/></a>
<a href="https://junit.org/"><img src="https://img.shields.io/badge/JUnit-25A162?style=for-the-badge&logo=junit5&logoColor=white"/></a>
<a href="https://assertj.github.io/doc/"><img src="https://img.shields.io/badge/AssertJ-4B0082?style=for-the-badge&logoColor=white"/></a>
<a href="https://www.sonarsource.com/products/sonarqube/"><img src="https://img.shields.io/badge/SonarQube-4E9BCD?style=for-the-badge&logo=sonarqube&logoColor=white"/></a>




👉 **Keycloak JWT Validator** is a fullstack application to decode and validate **JSON Web Tokens (JWTs)** tokens issued by **Keycloak**. It consists of a **React** frontend and a **Spring Boot** backend, working together to ensure token authenticity and integrity. The backend is configured as a Resource Server, leveraging Spring Security’s OAuth2 capabilities and the `NimbusJwtDecoder` to verify tokens against the public keys provided via Keycloak’s JWK Set URI.

![Keycloak JWT Validator - success](/src/main/resources/static/images/url1.png "Keycloak JWT Validator - success")

![Keycloak JWT Validator - error](/src/main/resources/static/images/url2.png "Keycloak JWT Validator - error")

## Features
**Keycloak JWT Validator** application:
- Parses and decodes the JWT token,
- Validates the token structure (ensures it contains three parts and valid JSON),
- Checks the expiry date,
- Forwards the token to the backend for full signature and claim verification.

On the backend, the Spring Boot service:
- Verifies the token’s signature using public keys retrieved from Keycloak's JWK Set URI,
- Checks the token's validity, including expiry, issuer, subject, and more.

## Technologies Used
- **Frontend**: React 19.0.1 with authentication components for Keycloak integration (`keycloak-js` library)
- **Backend**: Spring Boot 3.4.4 with Spring Security OAuth2 Resource Server (Keycloak)
- **Token Verification**: Spring Security integration with `NimbusJwtDecoder` (Nimbus JOSE+JWT)

## Installation

### 1. Prerequisites
- Java 21.0.5 LTS: required for Spring Boot 3.4.4 (Spring Boot starters for `web`, `security`, `oauth2-client`, `oauth2-resource-server`, `oauth2-jose`, `keycloak-spring-boot-starter` and `jackson-databind`)
- Node.js 22.11.0: required for React 19.1.0
- Gradle 8.13
- Keycloak 26.1.4 server running with realm and client configured

### 2. Clone repository using the web URL:
- `git clone https://github.com/AndrzejSzelag/keycloak-jwt-validator-springboot-react.git`

### 3. Make sure you have the correct environment variables or application properties set to connect with your Keycloak instance.

### 4. Backend Configuration (*.env* file in backend root):
- `JWK_SET_URI=https://<your-keycloak-server>/realms/<your-realm>/protocol/openid-connect/certs`

> Replace `<your-keycloak-server>` and `<your-realm>` with your Keycloak setup details!

### 5. Frontend Configuration (*.env* file in frontend root):
- `REACT_APP_TITLE=Keycloak JWT Validator`
- `REACT_APP_API_BASE_URL=http://<your-keycloak-server>`
- `REACT_APP_API_PATH=/api/v1`

### 6. Run the Spring Boot Backend
- Navigate to the root project directory in your terminal and execute command: `gradle bootRun`

> The backend starts on port **8888**.

### 7. Run the React Frontend
- Navigate to the frontend project directory in your terminal: `cd frontend`
- Execute command: 
    - `npm install`
    - `npm start`

> The frontend starts on port **3000**.

## Usage
1. Obtain the JWT access token using curl (client credentials flow), and copy the token value from the response.

- `curl -X POST "http://<your-keycloak-server>/realms/<your-realm>/protocol/openid-connect/token" -H "Content-Type: application/x-www-form-urlencoded" -d "grant_type=client_credentials" -d "client_id=<your-client-id>" -d "client_secret=<your-client-secret>"`

    > Replace `<your-keycloak-server>`, `<your-realm>`, `<your-client-id>` and `<your-client-secret>` with your Keycloak setup details!

2. In the React application, paste the JWT token into the text area and click Validate JWT Token.
3. Review:
    - The token’s Header, Payload, and Signature,
    - The result of local validation (e.g. whether the token is expired),
    - The full backend validation result (e.g. issuer, subject, audience, etc.).
