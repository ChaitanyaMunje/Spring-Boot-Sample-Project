# ---------- builder stage ----------
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /build

# copy only dependency info first for layer caching
COPY APIProject/pom.xml ./pom.xml
# if you have multi-module adjust accordingly
RUN mvn -B -f pom.xml dependency:go-offline -DskipTests

# copy sources
COPY APIProject/src ./src
# copy other necessary files (if any)
COPY APIProject/pom.xml ./pom.xml

# build the jar
RUN mvn -B -f pom.xml clean package -DskipTests

# ---------- runtime stage ----------
FROM eclipse-temurin:21-jre-alpine

# create user for security (non-root)
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

# copy jar from builder (use correct final artifact name)
# change the jar name if different
COPY --from=builder /build/target/APIProject-0.0.1-SNAPSHOT.jar ./app.jar

# expose port (matches server.port)
EXPOSE 9191

USER appuser

ENTRYPOINT ["java","-jar","/app/app.jar"]

