package org.hsmith.jira_api_client.api

import java.util.concurrent.TimeUnit
import org.hsmith.jira_api_client.adapters.UriAdapter
import org.hsmith.jira_api_client.auth.JiraCredentials
import org.openapitools.client.infrastructure.ApiClient
import org.openapitools.client.infrastructure.Serializer

class JiraRestApi(private val jiraUri: String, jiraCredentials: JiraCredentials) : JiraApi {
    init {
        Serializer.moshiBuilder.add(UriAdapter())

        ApiClient.builder.writeTimeout(30, TimeUnit.SECONDS)
        ApiClient.builder.readTimeout(30, TimeUnit.SECONDS)

        ApiClient.username = jiraCredentials.userName
        ApiClient.password = jiraCredentials.apiToken
    }

    override val issueTypesApi: JiraIssueTypesApi by lazy { JiraRestIssueTypesApi(jiraUri) }

    override val issueApi: JiraIssueApi by lazy { JiraRestIssueApi(jiraUri) }
}
