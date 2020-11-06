package org.hsmith.jira_api_client.data.input.builder

import org.hsmith.jira_api_client.data.input.JiraInputIssueData
import java.time.LocalDateTime

interface JiraInputIssueDataBuilder {
    fun setAffectedVersionsNames(affectedVersionNames: Set<String>): JiraInputIssueDataBuilder
    fun setComponentsNames(componentNames: Set<String>): JiraInputIssueDataBuilder
    fun setDescription(description: String): JiraInputIssueDataBuilder
    fun setDueDate(dueDate: LocalDateTime): JiraInputIssueDataBuilder
    fun setFixVersionsNames(fixVersionNames: Set<String>): JiraInputIssueDataBuilder
    fun setPriorityId(priorityId: String): JiraInputIssueDataBuilder

    fun build(): JiraInputIssueData
}
