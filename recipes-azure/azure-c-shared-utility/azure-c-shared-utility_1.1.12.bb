DESCRIPTION = "Azure C Shared Utility"
AUTHOR = "Microsoft Corporation"
HOMEPAGE = "https://github.com/Azure/azure-c-shared-utility"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4283671594edec4c13aeb073c219237a"

SRC_URI = "\
    git://github.com/Azure/azure-c-shared-utility.git \
    file://error-header-file-path-fix.patch \
"

SRCREV = "48f7a556865731f0e96c47eb5e9537361f24647c"

PR = "r0"

require ${BPN}.inc
