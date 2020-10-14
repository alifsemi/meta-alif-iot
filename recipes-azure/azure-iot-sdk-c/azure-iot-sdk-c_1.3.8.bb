DESCRIPTION = "Microsoft Azure IoT SDKs and libraries for C"
AUTHOR = "Microsoft Corporation"
HOMEPAGE = "https://github.com/Azure/azure-iot-sdk-c"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4283671594edec4c13aeb073c219237a"

SRC_URI = "\
    git://github.com/Azure/azure-iot-sdk-c.git;rev=a8a71c2d120c571a2d2ab6149863c2b075e7bea9 \
"

PR = "r0"

include ${BPN}.inc
AZURE_DEV_CONNECTION_STRING ?= "fixme"

do_compile_prepend () {
	find ${S}/iothub_client/samples/ -type f | xargs sed -i -e "s:static const char\* connectionString =.*:static const char\* connectionString = \"${AZURE_DEV_CONNECTION_STRING}\";:g"
}

