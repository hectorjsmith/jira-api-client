package org.hsmith.jira_api_client.data.output

data class JiraCreatedIssueData(
    /** The ID of the created issue or subtask. */
    val id: String,
    /** The key of the created issue or subtask. */
    val key: String,
    /** The URL of the created issue or subtask. */
    val self: String
)
