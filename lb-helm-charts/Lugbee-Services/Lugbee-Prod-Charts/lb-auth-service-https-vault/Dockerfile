FROM openjdk:8

# Define working directory.
ENV WORKING_DIR /opt/lugbee
# Install curl for use with Kubernetes readiness probe.
RUN mkdir -p "$WORKING_DIR" \
    && apt-get update \
    && apt-get install -y curl \
    && rm -rf /var/lib/apt/lists/*
WORKDIR $WORKING_DIR

# Copy the .jar file into the Docker image
COPY lats.jar $WORKING_DIR/lats.jar

CMD ["java", "-jar", "lats.jar"]
