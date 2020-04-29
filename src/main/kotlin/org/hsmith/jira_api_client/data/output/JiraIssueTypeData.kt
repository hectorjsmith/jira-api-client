package org.hsmith.jira_api_client.data.output

data class JiraIssueTypeData(
    /** The URL of these issue type details. */
    val self: String,
    /** The ID of the issue type. */
    val id: String,
    /** The description of the issue type. */
    val description: String,
    /** The URL of the issue type's avatar. */
    val iconUrl: String,
    /** The name of the issue type. */
    val name: String,
    /** Whether this issue type is used to create subtasks. */
    val subTask: Boolean,
    /** The ID of the issue type's avatar. */
    val avatarId: Long
)
