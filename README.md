# Keycloak JWT Validator 

[<img alt="Keycloak" src="https://img.shields.io/badge/Keycloak-26.1.4-0071C1.svg?logo=keycloak">](https://www.keycloak.org/) 
[<img alt="Spring Boot" src="https://img.shields.io/badge/Spring Boot-3.4.4-6DB33F.svg?logo=springboot">](<https://spring.io/projects/spring-boot>) 
[<img alt="React" src="https://img.shields.io/badge/React-19.1.0-61DAFB?logo=react&logoColor=white">](https://react.dev/) 
[<img alt="Gradle" src="https://img.shields.io/badge/Gradle-8.13-02303A.svg?logo=gradle">](https://gradle.org/)



### 🧰 Tech & Tools
[<img alt="Keycloak" src="https://img.shields.io/badge/Keycloak-26.1.4-0071C1.svg?logo=keycloak">](https://www.keycloak.org/) 
[<img alt="Spring Boot" src="https://img.shields.io/badge/Spring Boot-3.4.4-6DB33F.svg?logo=springboot">](https://spring.io/projects/spring-boot)
[<img alt="React" src="https://img.shields.io/badge/React-19.1.0-61DAFB?logo=react&logoColor=white">](https://react.dev/)
[<img alt="Gradle" src="https://img.shields.io/badge/Gradle-8.13-02303A.svg?logo=gradle">](https://gradle.org/)
[<img alt="Lombok" src="https://img.shields.io/badge/Lombok-1.18.30-DA525D.svg?logo=java">](https://projectlombok.org/)
[<img alt="MongoDB" src="https://img.shields.io/badge/MongoDB-7.0-47A248.svg?logo=mongodb">](https://www.mongodb.com/)
[<img alt="Apache Kafka" src="https://img.shields.io/badge/Apache Kafka-3.7.0-231F20.svg?logo=apachekafka">](https://kafka.apache.org/)
[<img alt="OAuth 2.0" src="https://img.shields.io/badge/OAuth 2.0-Protocol-2C2255.svg?logo=oauth">](https://oauth.net/2/)
[<img alt="Maven" src="https://img.shields.io/badge/Maven-3.9.6-C71A36.svg?logo=apachemaven">](https://maven.apache.org/)
[<img alt="Java EE" src="https://img.shields.io/badge/Java EE-8-007396.svg?logo=java">](https://jakarta.ee/)
[<img alt="Java 17" src="https://img.shields.io/badge/Java-17-ED8B00.svg?logo=openjdk">](https://openjdk.org/projects/jdk/17/)
[<img alt="Java 21" src="https://img.shields.io/badge/Java-21-ED8B00.svg?logo=openjdk">](https://openjdk.org/projects/jdk/21/)
[<img alt="JPA" src="https://img.shields.io/badge/JPA-Hibernate-59666C.svg?logo=hibernate">](https://hibernate.org/)
[<img alt="JSF" src="https://img.shields.io/badge/JSF-3.0-2C2255.svg?logo=java">](https://jakarta.ee/specifications/faces/)
[<img alt="CDI" src="https://img.shields.io/badge/CDI-4.0-2C2255.svg?logo=java">](https://jakarta.ee/specifications/cdi/)
[<img alt="JTA" src="https://img.shields.io/badge/JTA-2.0-007396.svg?logo=java">](https://jakarta.ee/specifications/transactions/)
[<img alt="Docker" src="https://img.shields.io/badge/Docker-24.0.7-2496ED.svg?logo=docker">](https://www.docker.com/)
[<img alt="Kubernetes" src="https://img.shields.io/badge/Kubernetes-1.30-326CE5.svg?logo=kubernetes">](https://kubernetes.io/)
[<img alt="Boot Faces" src="https://img.shields.io/badge/Boot Faces-3.0.1-7952B3.svg?logo=bootstrap">](https://github.com/TheCoder4eu/BootsFaces-OSP)
[<img alt="PrimeFaces" src="https://img.shields.io/badge/PrimeFaces-13.0.2-2C2255.svg?logo=primefaces">](https://www.primefaces.org/)
[<img alt="Payara Server" src="https://img.shields.io/badge/Payara Server-6.2024.1-FF7300.svg?logo=payara">](https://www.payara.fish/)
[<img alt="Visual Studio Code" src="https://img.shields.io/badge/VS Code-1.88.1-007ACC.svg?logo=visualstudiocode">](https://code.visualstudio.com/)
[<img alt="IntelliJ IDEA" src="https://img.shields.io/badge/IntelliJ IDEA-2024.1-000000.svg?logo=intellijidea">](https://www.jetbrains.com/idea/)
[<img alt="Python" src="https://img.shields.io/badge/Python-3.12-3776AB.svg?logo=python">](https://www.python.org/)
[<img alt="Django" src="https://img.shields.io/badge/Django-5.0.3-092E20.svg?logo=django">](https://www.djangoproject.com/)
[<img alt="Thymeleaf" src="https://img.shields.io/badge/Thymeleaf-3.1.1-005F0F.svg?logo=thymeleaf">](https://www.thymeleaf.org/)
[<img alt="Mockito" src="https://img.shields.io/badge/Mockito-5.11.0-4184F3.svg?logo=java">](https://site.mockito.org/)
[<img alt="JUnit" src="https://img.shields.io/badge/JUnit 5-5.10.1-25A162.svg?logo=junit5">](https://junit.org/)
[<img alt="AssertJ" src="https://img.shields.io/badge/AssertJ-3.25.1-5A32A3.svg?logo=java">](https://assertj.github.io/doc/)
[<img alt="SonarQube" src="https://img.shields.io/badge/SonarQube-10.4-4E9BCD.svg?logo=sonarqube">](https://www.sonarsource.com/products/sonarqube/)





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
