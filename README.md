# Keycloak JWT Validator 

[<img alt="keycloack" src="https://img.shields.io/badge/Keycloak-26.1.4-0071C1.svg?logo=keycloack">](https://www.keycloak.org/) [<img alt="springboot" src="https://img.shields.io/badge/Spring Boot-3.4.4-COLOR.svg?logo=LOGO">](<https://spring.io/projects/spring-boot>) 

[<img alt="React" src="https://img.shields.io/badge/React-19.1.0-61DAFB?logo=react&logoColor=white">](https://react.dev/) 


[<img alt="gradle" src="https://img.shields.io/badge/Gradle-8.13-COLOR.svg?logo=LOGO">](https://gradle.org/)






👉 **Keycloak JWT Validator** is a lightweight tool designed to validate **JSON Web Tokens (JWTs)** issued by a **Keycloak** server. It consists of a **React** frontend and a **Spring Boot** backend, working together to ensure token authenticity and integrity. The backend is configured as a Resource Server, leveraging Spring Security’s OAuth2 capabilities and the `NimbusJwtDecoder` to verify tokens against the public keys provided via Keycloak’s JWK Set URI.

![Keycloak JWT Validator - success](/src/main/resources/static/images/url1.png "Keycloak JWT Validator - success")

![Keycloak JWT Validator - error](/src/main/resources/static/images/url2.png "Keycloak JWT Validator - error")

## Features
**Keycloak JWT Validator** application:
- Decodes the JWT token,
- Validates its structure (ensures it contains three parts and valid JSON),
- Checks the expiry date,
- Sends the token to the backend for full verification.

On the backend, the Spring Boot service:
- Verifies the token’s signature using public keys retrieved from Keycloak's JWK Set URI,
- Checks the token's validity, including expiry, issuer, subject, and more.

## Technologies Used
- **Frontend**: React 19.0.1 with authentication components for Keycloak integration (`keycloak-js` library)
- **Backend**: Spring Boot 3.4.4 with Spring Security OAuth2 Resource Server (Keycloak)
- **Token Verification**: Spring Security integration with `Nimbus JWT Decoder` (Nimbus JOSE+JWT)

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

> The backend start on port **8888**.

### 7. Run the React Frontend
- Navigate to the frontend project directory in your terminal: `cd frontend`
- Execute command: 
    - `npm install`
    - `npm start`

> The frontend start on port **3000**.

## Usage
1. In the terminal, obtain the JWT access token using the cURL tools and copy it.

- `curl -X POST "http://<your-keycloak-server>/realms/<your-realm>/protocol/openid-connect/token" -H "Content-Type: application/x-www-form-urlencoded" -d "grant_type=client_credentials" -d "client_id=<your-client-id>" -d "client_secret=<your-client-secret>"`

    > Replace `<your-keycloak-server>`, `<your-realm>`, `<your-client-id>` and `<your-client-secret>` with your Keycloak setup details!

2. In the React application, paste the JWT token into the text area and click Validate JWT Token.
3. Review:
    - The token’s Header, Payload, and Signature,
    - The result of local validation (e.g. whether the token is expired),
    - The full backend validation result (e.g. issuer, subject, audience, etc.).
