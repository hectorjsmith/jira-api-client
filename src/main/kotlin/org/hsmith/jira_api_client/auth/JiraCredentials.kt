package org.hsmith.jira_api_client.auth

interface JiraCredentials {
    val userName: String
    val apiToken: String
}
