package org.hsmith.jira_api_client.auth

data class JiraBasicAuthCredentials(override val userName: String, override val apiToken: String) : JiraCredentials
