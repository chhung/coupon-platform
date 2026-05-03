# coupon-platform

A RESTful coupon management platform built with **Spring Boot** and the **official MongoDB driver** (no Spring Data).

---

## Prerequisites

| Tool | Version |
|------|---------|
| Java | 21+ |
| Maven | 3.8+ |
| MongoDB | 6.0+ |

---

## Configuration

Profile-specific MongoDB settings live under:

```
src/main/resources/database/mongo/
├── application-prod.properties   ← production DB connection
└── application-uat.properties    ← UAT DB connection
```

`spring.config.additional-location` is **not** baked into the JAR. It must be supplied at startup so that operations teams can fully control where config files are loaded from (e.g. an external path in version-controlled ops repos).

> **Why `additional-location` instead of `config.import`?**  
> `spring.config.import` only imports the literal file/directory — it does **not** apply Spring Boot's profile-specific file resolution (`application-{profile}.properties`).  
> `spring.config.additional-location` adds the directory to Spring Boot's standard search path, so `application-prod.properties` / `application-uat.properties` are automatically picked up based on the active profile.

The default active profile is **prod**.

---

## Build

DB config files (`database/mongo/application-*.properties`) are **excluded from the JAR only when the `package` Maven profile is activated**.  
During development they stay in `target/classes` so IntelliJ / Maven can read them via `classpath:`.

```bash
# Production JAR (database/** excluded)
mvn clean package -Ppackage -DskipTests

# Verify database config is NOT in the JAR
jar tf target/coupon-platform-0.0.1.jar | grep database   # should return nothing
```

---

## Run

### IntelliJ IDEA

**Run > Edit Configurations… > Program arguments:**

```
# PROD (default)
--spring.config.additional-location=optional:classpath:/database/mongo/

# UAT — add profile (or set SPRING_PROFILES_ACTIVE in Environment variables)
--spring.config.additional-location=optional:classpath:/database/mongo/ --spring.profiles.active=uat
```

### Development (Maven)

```powershell
# PROD (default)
mvn spring-boot:run `
  "-Dspring-boot.run.arguments=--spring.config.additional-location=optional:classpath:/database/mongo/"

# UAT  (PowerShell: set profile via env var)
$env:SPRING_PROFILES_ACTIVE = "uat"
mvn spring-boot:run `
  "-Dspring-boot.run.arguments=--spring.config.additional-location=optional:classpath:/database/mongo/"
```

> **Bash / Linux / macOS:**
> ```bash
> # PROD
> mvn spring-boot:run \
>   -Dspring-boot.run.arguments="--spring.config.additional-location=optional:classpath:/database/mongo/"
>
> # UAT
> SPRING_PROFILES_ACTIVE=uat mvn spring-boot:run \
>   -Dspring-boot.run.arguments="--spring.config.additional-location=optional:classpath:/database/mongo/"
> ```

### Production (JAR)

DB config files are **not inside the JAR**. Point `--spring.config.additional-location` to the external directory managed by operations:

```powershell
# PROD (default)
java -jar coupon-platform-0.0.1.jar `
  --spring.config.additional-location="optional:file:/etc/coupon-platform/"

# UAT
java -jar coupon-platform-0.0.1.jar `
  --spring.profiles.active=uat `
  --spring.config.additional-location="optional:file:/etc/coupon-platform/"
```

> The external directory must contain `application-prod.properties` / `application-uat.properties`  
> with keys `app.mongodb.primary.*` and `app.mongodb.secondary.*`.

---

## Health Check

Once the application is running, verify both MongoDB connections:

```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/health" -UseBasicParsing | Select-Object -ExpandProperty Content
```

Expected response:

```json
{
  "status": "UP",
  "primary": "UP",
  "secondary": "UP"
}
```

---

## License

This project is licensed under the [MIT License](LICENSE).

