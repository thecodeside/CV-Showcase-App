package com.thecodeside.cvshowcaseapp.common.utils

import android.content.Intent
import android.net.Uri

private const val MAILTO_PREFIX = "mailto:"

object IntentUtils {

    fun getForMail(email: String) = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse(MAILTO_PREFIX)
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
    }

    fun getForBrowser(uriString: String) =
        Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(uriString)
        }
}
