package org.hsmith.jira_api_client.data.input

data class JiraInputIssueData(
    val fieldMap: Map<String, Any>,
    val summary: String,
    val issueTypeId: String,
    val projectKey: String
)
