package org.hsmith.jira_api_client.api

import org.hsmith.jira_api_client.data.output.JiraIssueTypeData
import org.openapitools.client.apis.IssueTypesApi
import org.openapitools.client.models.IssueTypeDetails

class JiraRestIssueTypesApi internal constructor(private val jiraUri: String) : JiraIssueTypesApi {
    override fun getAllIssueTypes(): Set<JiraIssueTypeData> {
        return IssueTypesApi(jiraUri)
            .comAtlassianJiraRestV2IssueIssueTypeResourceGetIssueAllTypesGet()
            .map { issueType -> translateIssueTypeDetailToJiraIssueTypeData(issueType) }
            .toSet()
    }

    override fun getIssueTypeNameToIdMap(): Map<String, String> {
        val issueTypeNameToIdMap = HashMap<String, String>()
        getAllIssueTypes()
            .forEach { issue -> issueTypeNameToIdMap[issue.name] = issue.id }
        return issueTypeNameToIdMap
    }

    private fun translateIssueTypeDetailToJiraIssueTypeData(rawData: IssueTypeDetails): JiraIssueTypeData {
        return JiraIssueTypeData(
            rawData.self!!,
            rawData.id!!,
            rawData.description!!,
            rawData.iconUrl!!,
            rawData.name!!,
            rawData.subtask!!,
            rawData.avatarId ?: -1
        )
    }
}
