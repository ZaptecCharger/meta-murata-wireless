SUMMARY = "User space daemon for extended IEEE 802.11 management"
HOMEPAGE = "http://w1.fi/hostapd/"
SECTION = "kernel/userland"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://hostapd/README;md5=c905478466c90f1cefc0df987c40e172"

DEPENDS = "libnl openssl"

SRC_URI = " \
	   http://w1.fi/releases/hostapd-${PV}.tar.gz \
	   file://defconfig \
	   file://init \
	   file://hostapd.service \
	   file://udhcpd.conf \
	   file://0003-nl80211-Report-connection-authorized-in-EVENT_ASSOC.patch;apply=yes \
	   file://0005-OpenSSL-Fix-build-with-OpenSSL-1.0.1.patch;apply=yes \
	   file://0006-nl80211-Check-SAE-authentication-offload-support.patch;apply=yes \
	   file://0007-SAE-Pass-SAE-password-on-connect-for-SAE-authenticat.murata.patch;apply=yes \
	   file://0008-nl80211-Support-4-way-handshake-offload-for-WPA-WPA2.patch;apply=yes \
	   file://0009-AP-Support-4-way-handshake-offload-for-WPA-WPA2-PSK.patch;apply=yes \
	   file://0010-nl80211-Support-SAE-authentication-offload-in-AP-mod.patch;apply=yes \
	   file://0011-SAE-Support-SAE-authentication-offload-in-AP-mode.patch;apply=yes \
	   file://0013-non-upstream-defconfig_base-Add-Infineon-default-con.patch;apply=yes \
	   file://0014-CVE_2019_9501-Fix-to-check-Invalid-GTK-IE-length-in-.patch;apply=yes \
	   file://0015-Add-CONFIG_WPA3_SAE_AUTH_EARLY_SET-flags-and-codes.murata.patch;apply=yes \
	   file://0016-SAE-Set-the-right-WPA-Versions-for-FT-SAE-key-manage.patch;apply=yes \
	   file://0017-wpa_supplicant-Support-WPA_KEY_MGMT_FT-for-eapol-off.murata.patch;apply=yes \
	   file://0018-wpa_supplicant-suppress-deauth-for-PMKSA-caching-dis.murata.patch;apply=yes \
	   file://0019-Fix-for-PMK-expiration-issue-through-supplicant.murata.patch;apply=yes \
	   file://0021-Avoid-deauthenticating-STA-if-the-reason-for-freeing.patch;apply=yes \
	   file://0022-wpa_supplicant-support-bgscan.patch;apply=yes \
	   file://0023-non-upstream-wl-cmd-create-interface-to-support-driv.murata.patch;apply=yes \
	   file://0024-non-upstream-wl-cmd-create-wl_do_cmd-as-an-entry-doi.patch;apply=yes \
	   file://0025-non-upstream-wl-cmd-create-ops-table-to-do-wl-comman.patch;apply=yes \
	   file://0026-non-upstream-wl-cmd-add-more-compile-flag.murata.patch;apply=yes \
	   file://0027-Fix-dpp-config-parameter-setting.patch;apply=yes \
	   file://0028-DPP-Resolving-failure-of-dpp-configurator-exchange-f.patch;apply=yes \
	   file://0029-Enabling-SUITEB192-and-SUITEB-compile-options.patch;apply=yes \
	   file://0030-DPP-Enabling-CLI_EDIT-option-for-enrollee-plus-respo.patch;apply=yes \
	   file://0032-non-upstream-SAE-disconnect-after-PMKSA-cache-expire.patch;apply=yes \
	   file://0034-wpa_supplicant-Set-PMKSA-to-driver-while-key-mgmt-is.patch;apply=yes \
	   file://0035-nl80211-Set-NL80211_SCAN_FLAG_COLOCATED_6GHZ-in-scan.murata.patch;apply=yes \
	   file://0037-Enabling-OWE-in-wpa_supplicant.patch;apply=yes \
	   file://0039-FT-Sync-nl80211-ext-feature-index.patch;apply=yes \
	   file://0040-nl80211-Introduce-a-vendor-header-for-vendor-NL-ifac.patch;apply=yes \
	   file://0041-add-support-to-offload-TWT-setup-request-handling-to.murata.patch;apply=yes \
	   file://0042-add-support-to-offload-TWT-Teardown-request-handling.murata.patch;apply=yes \
	   file://0043-Add-support-to-configure-TWT-of-a-session-using-offs.murata.patch;apply=yes \
	   file://0048-Fix-associating-failed-when-PMK-lifetime-is-set-to-1.patch;apply=yes \
	   file://0050-non-upstream-MBO-wpa_cli-mbo-command-by-IFX-vendorID.murata.patch;apply=yes \
"


SRC_URI[sha256sum] = "206e7c799b678572c2e3d12030238784bc4a9f82323b0156b4c9466f1498915d"

S = "${WORKDIR}/hostapd-${PV}"
B = "${WORKDIR}/hostapd-${PV}/hostapd"

inherit update-rc.d systemd pkgconfig features_check

CONFLICT_DISTRO_FEATURES = "openssl-no-weak-ciphers"

INITSCRIPT_NAME = "hostapd"

SYSTEMD_SERVICE:${PN} = "hostapd.service"
SYSTEMD_AUTO_ENABLE:${PN} = "disable"

do_configure:append() {
    install -m 0644 ${WORKDIR}/defconfig ${B}/.config
}

do_compile() {
    export CFLAGS="-MMD -O2 -Wall -g"
    export EXTRA_CFLAGS="${CFLAGS}"
    make V=1
}

do_install() {
    install -d ${D}${sbindir} ${D}${sysconfdir}/init.d ${D}${systemd_unitdir}/system/
    install -m 0644 ${B}/hostapd.conf ${D}${sysconfdir}
#   Adding udhcdp.conf
    install -m 0644 ${WORKDIR}/udhcpd.conf ${D}${sysconfdir}

    install -m 0755 ${B}/hostapd ${D}${sbindir}/hostapd.cyw
    install -m 0755 ${B}/hostapd_cli ${D}${sbindir}/hostapd_cli.cyw
    install -m 755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/hostapd
    install -m 0644 ${WORKDIR}/hostapd.service ${D}${systemd_unitdir}/system/
    sed -i -e 's,@SBINDIR@,${sbindir},g' -e 's,@SYSCONFDIR@,${sysconfdir},g' ${D}${systemd_unitdir}/system/hostapd.service
}

CONFFILES:${PN} += "${sysconfdir}/hostapd.conf"
