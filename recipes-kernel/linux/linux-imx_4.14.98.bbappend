# Copyright (C) 2016 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

SRC_URI_append = " file://0002-murata-dts-3.3v.patch \
		   file://0008-kernel_change_for_fmac_log_string.patch \
		   file://0007.patch \
		   file://0009.patch \
		   file://0018.patch \
		   file://0024.patch \
		   file://0025.patch \
		   file://0029.patch \
		   file://0030.patch \
		   file://0031.patch \
		   file://0036.patch \
		   file://0042.patch \
		   file://0043.patch \
		   file://0044.patch \
		   file://0045.patch \
		   file://0046.patch \
		   file://0047.patch \
		   file://0048.patch \
		   file://0049.patch \
		   file://0050.patch \
		   file://0051.patch \
		   file://0052.patch \
		   file://0053.patch \
		   file://0054.patch \
		   file://0055.patch \
		   file://0056.patch \
		   file://0057.patch \
		   file://0058.patch \
		   file://0059.patch \
		   file://0060.patch \
		   file://0061.patch \
		   file://0062.patch \
		   file://0063.patch \
		   file://0064.patch \
		   file://0066.patch \
		   file://0067.patch \
		   file://0068.patch \
		   file://0070.patch \
		   file://0071.patch \
		   file://0073.patch \
		   file://0074.patch \
		   file://0076.patch \
		   file://0077.patch \
		   file://0079.patch \
		   file://0080.patch \
		   file://0082.patch \
		   file://0083.patch \
		   file://0084.patch \
		   file://0085.patch \
		   file://0086.patch \
		   file://0087.patch \
		   file://0088.patch \
		   file://0089.patch \
		   file://0091.patch \
		   file://0093.patch \
		   file://0094.patch \
		   file://0095.patch \
		   file://0096.patch \
		   file://0097.patch \
		   file://0098.patch \
		   file://0099.patch \
		   file://0100.patch \
		   file://0101.patch \
		   file://0102.patch \
		   file://0103.patch \
		   file://0104.patch \
		   file://0107.patch \
		   file://0108.patch \
		   file://0109.patch \
		   file://0110.patch \
		   file://0111.patch \
		   file://0112.patch \
		   file://0113.patch \
		   file://0114.patch \
		   file://0115.patch \
		   file://0116.patch \
		   file://0117.patch \
		   file://0118.patch \
		   file://0119.patch \
		   file://0120.patch \
		   file://0121.patch \
		   file://0122.patch \
		   file://0123.patch \
		   file://0124.patch \
		   file://0125.patch \
		   file://0126.patch \
		   file://0127.patch \
		   file://0128.patch \
		   file://0129.patch \
		   file://0130.patch \
		   file://0131.patch \
		   file://0132.patch \
		   file://0133.patch \
		   file://0134.patch \
		   file://0135.patch \
		   file://0136.patch \
		   file://0137.patch \
		   file://0138.patch \
		   file://0139.patch \
		   file://0140.patch \
		   file://0141.patch \
		   file://0142.patch \
		   file://0143.patch \
		   file://0144.patch \
		   file://0145.patch \
		   file://0146.patch \
		   file://0147.patch \
		   file://0148.patch \
		   file://0149.patch \
		   file://0150.patch \
		   file://0151.patch \
		   file://0152.patch \
		   file://0153.patch \
		   file://0154.patch "

addtask copy_defconfig_after_patch after do_patch before do_configure
do_copy_defconfig_after_patch () {
    # copy latest imx_v7_defconfig to use
    cp ${S}/arch/arm/configs/imx_v7_defconfig ${B}/.config
    cp ${S}/arch/arm/configs/imx_v7_defconfig ${B}/../defconfig
}