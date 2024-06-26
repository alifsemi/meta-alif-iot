inherit cmake

DEPENDS = "\
    azure-c-shared-utility \
    parson \
"

SRC_URI += "\
    file://Fix-cmake-build-issues.patch \
    file://Use-provided-parson-lib.patch \
"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

PACKAGECONFIG ??= "mqtt"
PACKAGECONFIG[amqp] = "-Duse_amqp:BOOL=ON, -Duse_amqp:BOOL=OFF, azure-uamqp-c"
PACKAGECONFIG[edge] = "-Duse_edge_modules:BOOL=ON, -Duse_edge_modules:BOOL=OFF, azure-uhttp-c"
PACKAGECONFIG[http] = "-Duse_http:BOOL=ON, -Duse_http:BOOL=OFF, azure-uhttp-c"
PACKAGECONFIG[mqtt] = "-Duse_mqtt:BOOL=ON, -Duse_mqtt:BOOL=OFF, azure-umqtt-c"

EXTRA_OECMAKE = "\
    -Dbuild_as_dynamic:BOOL=OFF \
    -Dskip_samples:BOOL=OFF \
    -Duse_installed_dependencies:BOOL=ON \
    -Dbuild_service_client:BOOL=OFF \
    -Dbuild_provisioning_service_client:BOOL=OFF \
    -Duse_openssl:BOOL=OFF \
    -Duse_mbedtls:BOOL=ON \
"

sysroot_stage_all_append () {
    sysroot_stage_dir ${D}${exec_prefix}/cmake ${SYSROOT_DESTDIR}${exec_prefix}/cmake
}

FILES_${PN}-dev += "\
    ${exec_prefix}/cmake \
"

