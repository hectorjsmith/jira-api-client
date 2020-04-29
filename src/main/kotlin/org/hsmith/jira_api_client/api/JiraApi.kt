package org.hsmith.jira_api_client.api

interface JiraApi {
    val issueTypesApi: JiraIssueTypesApi
    val issueApi: JiraIssueApi
}
