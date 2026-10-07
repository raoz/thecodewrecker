# Archived build environment for thecodewrecker: toolchain only, no source.
# Every input is pinned so a rebuild gives the same toolchain:
#  - base image by digest
#  - Debian packages from a fixed snapshot.debian.org date
#  - Zulu JDK 8 + JavaFX from its official tarball, checked by sha256
# The refresh-build-env workflow downloads ZULU_URL, checks ZULU_SHA256 and
# puts the tarball in this directory before `docker build`.
FROM debian:trixie-slim@sha256:a29215f6a35e51e22adffa17f89e9d2ef06214e64a2bad10d765c46aea49f11f

ARG DEBIAN_SNAPSHOT=20261006T000000Z
ARG ZULU_URL=https://cdn.azul.com/zulu/bin/zulu8.96.0.205-ca-fx-jdk8.0.504-linux_x64.tar.gz
ARG ZULU_SHA256=c5dcd67eac70e56154e06fa1dca88d27b5a66114835303155f3388e130bb430a
ARG ZULU_DIR=zulu8.96.0.205-ca-fx-jdk8.0.504-linux_x64

LABEL org.opencontainers.image.source="https://github.com/raoz/thecodewrecker" \
      org.opencontainers.image.description="thecodewrecker build environment: Debian trixie + Zulu JDK 8.0.504 with JavaFX (toolchain only)" \
      org.opencontainers.image.licenses="GPL-2.0-with-classpath-exception"

RUN rm -f /etc/apt/sources.list.d/debian.sources \
 && echo "deb [check-valid-until=no] http://snapshot.debian.org/archive/debian/${DEBIAN_SNAPSHOT} trixie main" > /etc/apt/sources.list \
 && apt-get -o Acquire::Retries=5 update \
 && apt-get -o Acquire::Retries=5 install -y --no-install-recommends zip unzip \
 && rm -rf /var/lib/apt/lists/*

COPY zulu.tar.gz /tmp/zulu.tar.gz
RUN echo "${ZULU_SHA256}  /tmp/zulu.tar.gz" | sha256sum -c - \
 && mkdir -p /opt \
 && tar -xzf /tmp/zulu.tar.gz -C /opt \
 && ln -s "/opt/${ZULU_DIR}" /opt/zulu8-fx \
 && rm /tmp/zulu.tar.gz

ENV JAVA_HOME=/opt/zulu8-fx \
    PATH=/opt/zulu8-fx/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin \
    LANG=C.UTF-8 \
    HOME=/tmp

RUN java -version && javac -version && test -f "$JAVA_HOME/jre/lib/ext/jfxrt.jar" \
 && dpkg-query -W -f='${Package}=${Version}\n' > /buildenv-packages.txt
