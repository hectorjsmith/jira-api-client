package org.hsmith.jira_api_client.api

import org.hsmith.jira_api_client.data.output.JiraIssueTypeData

interface JiraIssueTypesApi {
    fun getAllIssueTypes(): Set<JiraIssueTypeData>

    fun getIssueTypeNameToIdMap(): Map<String, String>
}
