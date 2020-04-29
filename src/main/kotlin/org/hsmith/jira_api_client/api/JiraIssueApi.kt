package org.hsmith.jira_api_client.api

import org.hsmith.jira_api_client.data.input.JiraInputIssueData
import org.hsmith.jira_api_client.data.output.JiraCreatedIssueData

interface JiraIssueApi {
    fun createIssue(inputData: JiraInputIssueData, updateHistory: Boolean = false): JiraCreatedIssueData
    fun createIssuesInBulk(inputData: Set<JiraInputIssueData>): Set<JiraCreatedIssueData>
}
