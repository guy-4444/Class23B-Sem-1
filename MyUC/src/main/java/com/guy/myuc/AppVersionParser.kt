package com.guy.myuc

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*

class AppVersionParser {

    private val client = HttpClient {
        install(HttpTimeout.Feature)
    }

    suspend fun versionForIdentifier(bundleID: String, privacyUrl: String): String? {
        val userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 12_3_1) AppleWebKit/605.1.15 (KHTML, like Gecko) " +
                "Version/15.3 Safari/605.1.15"
        val referrer = "https://www.google.com"

        val url = "https://play.google.com/store/apps/details?id=$bundleID&hl=en"

        return try {
            val response: String = client.get(url) {
                headers {
                    this.append(HttpHeaders.UserAgent, userAgent)
                    this.append(HttpHeaders.Referrer, referrer)
                }

                timeout {
                    this.requestTimeoutMillis = 10000
                }
            }

            parseDocument(response, privacyUrl)
        } catch (e: Exception) {
            return null
        }
    }

    private fun parseDocument(html: String, privacyUrl: String): String {
        val policyMarker = "\"${privacyUrl}\""
        val policyUrlIndex = html.lastIndexOf(policyMarker)
        val versionStart = html.indexOf("\"", startIndex = policyUrlIndex + policyMarker.length + 1) + 1 // "
        val versionEnd = html.indexOf("\"", startIndex = versionStart + 1)
        return html.substring(startIndex = versionStart, endIndex = versionEnd)
    }
}