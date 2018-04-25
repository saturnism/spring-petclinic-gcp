FROM openjdk:8u162-jre
MAINTAINER Ray Tsang

# Download Cloud Debugger and Cloud Profiler agents
RUN mkdir -p /opt/debugger && \
    wget -qO- https://storage.googleapis.com/cloud-debugger/compute-java/debian-wheezy/cdbg_java_agent_service_account.tar.gz | \
    tar xvz -C /opt/debugger
RUN mkdir -p /opt/profiler && \
    wget -qO- https://storage.googleapis.com/cloud-profiler/java/latest/profiler_java_agent.tar.gz | \
    tar xvz -C /opt/profiler

# Copy a startup script that helps executing entrypoint with environmental variable
COPY start.sh /app/start.sh
RUN chmod a+x /app/start.sh
ENTRYPOINT ["/app/start.sh"]

# Copy Application, separating dependencies from application code
ADD lib/* /app/lib/
ARG ARTIFACT_FILE
ADD ${ARTIFACT_FILE} /app/app.jar

ARG EXPOSED_PORT=8080
EXPOSE ${EXPOSED_PORT}

ENV SPRING_PROFILES_ACTIVE docker
ARG SERVICE_NAME
ENV SERVICE_NAME ${SERVICE_NAME}

ARG SERVICE_VERSION
ENV SERVICE_VERSION ${SERVICE_VERSION}

# Add Cloud Debugger and Cloud Profiler agents to startup command line
CMD ["java", \
     "$JAVA_OPTS", \
     "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", \
     "-agentpath:/opt/debugger/cdbg_java_agent.so", \
     "-agentpath:/opt/profiler/profiler_java_agent.so=-cprof_service=$SERVICE_NAME,-cprof_service_version=$SERVICE_VERSION,-cprof_project_id=$PROJECT_ID", \
     "-Dcom.google.cdbg.auth.serviceaccount.enable=true", \
     "-Dcom.google.cdbg.module=$SERVICE_NAME", \
     "-Dcom.google.cdbg.version=$SERVICE_VERSION", \
     "-Djava.security.egd=file:/dev/./urandom", \
      "-jar","/app/app.jar"]

