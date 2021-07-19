SUMMARY = "AWS IoT device SDK for embedded C"
DESCRIPTION = "The AWS IoT device SDK for embedded C is a \
collection of C source files which can be used in embedded \
applications to securely connect to the AWS IoT platform."
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=acc7a1bf87c055789657b148939e4b40"

SRC_URI = "https://github.com/aws/aws-iot-device-sdk-embedded-C/archive/v${PV}.tar.gz;downloadfilename=${BP}.tar.gz;name=aws-sdk \
           https://tls.mbed.org/download/mbedtls-2.16.6-apache.tgz;name=mbedtls \
	   file://configuration-certificate-updates.patch;apply=yes \
	   file://*pem* \
	   file://resolv.conf \
"

S = "${WORKDIR}/aws-iot-device-sdk-embedded-C-${PV}"

LDFLAGS = "${TARGET_CC_ARCH}"

AWS_IOT_MQTT_HOST ?= "a2g7twmqo7hg82-ats.iot.ap-south-1.amazonaws.com"
AWS_IOT_MQTT_CLIENT_ID ?= "MyThing"
AWS_IOT_MY_THING_NAME ?= "MyThing"
AWS_IOT_ROOT_CA_FILENAME ?= "AmazonRootCA1.pem"
AWS_IOT_CERTIFICATE_FILENAME ?= "4960bd2f6b-certificate.pem.crt"
AWS_IOT_PRIVATE_KEY_FILENAME ?= "4960bd2f6b-private.pem.key"

do_compile () {
    cp -a ${WORKDIR}/mbedtls-2.16.6/* ${S}/external_libs/mbedTLS/
    for samples in `ls -1 ${S}/samples/linux/` ; do
        pushd ${S}/samples/linux/${samples} ; \
        sed -i -e "s:define AWS_IOT_MQTT_HOST.*:define AWS_IOT_MQTT_HOST \"${AWS_IOT_MQTT_HOST}\":g" \
        -e "s:define AWS_IOT_MQTT_CLIENT_ID.*:define AWS_IOT_MQTT_CLIENT_ID \"${AWS_IOT_MQTT_CLIENT_ID}\":g" \
        -e "s:define AWS_IOT_MY_THING_NAME.*:define AWS_IOT_MY_THING_NAME \"${AWS_IOT_MY_THING_NAME}\":g" \
        -e "s:define AWS_IOT_ROOT_CA_FILENAME.*:define AWS_IOT_ROOT_CA_FILENAME \"${AWS_IOT_ROOT_CA_FILENAME}\":g" \
        -e "s:define AWS_IOT_CERTIFICATE_FILENAME.*:define AWS_IOT_CERTIFICATE_FILENAME \"${AWS_IOT_CERTIFICATE_FILENAME}\":g" \
        -e "s:define AWS_IOT_PRIVATE_KEY_FILENAME.*:define AWS_IOT_PRIVATE_KEY_FILENAME \"${AWS_IOT_PRIVATE_KEY_FILENAME}\":g" aws_iot_config.h ; make -f Makefile CC="${CC}" ; popd
    done
}

do_install () {
    install -D -m 0755 ${S}/samples/linux/subscribe_publish_sample/subscribe_publish_sample ${D}${bindir}/subscribe_publish_sample
    install -d -p ${D}${sysconfdir}/certs
    install -m -r ${WORKDIR}/*pem* ${D}${sysconfdir}/certs
    install -m 0555 ${WORKDIR}/resolv.conf ${D}${sysconfdir}
}

INSANE_SKIP += "src-uri-bad"
SRC_URI[aws-sdk.md5sum] = "ae57a7b0a23c81a7cff5364717d79187"
SRC_URI[aws-sdk.sha256sum] = "13fc9816af047c28ddf2c78a913821e05ddae8f212ff1848c9982986f1b3f5a5"
SRC_URI[mbedtls.md5sum] = "1f629a43c166de2eca808f3e30aa961d"
SRC_URI[mbedtls.sha256sum] = "66455e23a6190a30142cdc1113f7418158839331a9d8e6b0778631d077281770"
