package org.hsmith.jira_api_client.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.net.URI

class UriAdapter {
    @ToJson
    fun toJson(data: URI): String = data.toASCIIString()

    @FromJson
    fun fromJson(data: String): URI = URI(data)
}
