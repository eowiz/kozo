ARG GRAALVM_BASE="/opt/graalvm"
ARG GRAALVM_VERSION="22.3.0"
ARG ARCHITECTURE="aarch64"

FROM alpine:20230208 AS download

ARG GRAALVM_VERSION
ARG ARCHITECTURE

WORKDIR /workspace

RUN apk update && apk add curl

RUN curl -o graalvm-ce-java17-linux.tar.gz \
         -L https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-$GRAALVM_VERSION/graalvm-ce-java17-linux-$ARCHITECTURE-$GRAALVM_VERSION.tar.gz && \
    tar -xzf graalvm-ce-java17-linux.tar.gz

FROM debian:11-slim

ARG GRAALVM_BASE
ARG GRAALVM_VERSION

RUN apt-get update -qq

RUN mkdir -p $GRAALVM_BASE
COPY --from=download /workspace/graalvm-ce-java17-$GRAALVM_VERSION $GRAALVM_BASE

ENV JAVA_HOME=$GRAALVM_BASE
ENV PATH="$JAVA_HOME/bin:$PATH"

RUN gu install native-image

RUN mkdir app
WORKDIR app

CMD ["/bin/bash"]

