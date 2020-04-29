package org.hsmith.jira_api_client.api

import java.lang.Exception
import org.hsmith.jira_api_client.data.input.JiraInputIssueData
import org.hsmith.jira_api_client.data.output.JiraCreatedIssueData
import org.openapitools.client.apis.IssuesApi
import org.openapitools.client.models.CreatedIssue
import org.slf4j.LoggerFactory

class JiraRestIssueApi internal constructor(jiraUri: String) : JiraIssueApi {
    private val logger by lazy { LoggerFactory.getLogger(this::class.java) }
    private val rawApiClient = IssuesApi(jiraUri)

    override fun createIssue(inputData: JiraInputIssueData, updateHistory: Boolean): JiraCreatedIssueData {
        logger.debug("Creating issue: ${inputData.summary}")
        val mapForJira = wrapIssueDataInMapForJira(inputData)
        try {
            val createdIssue =
                rawApiClient.comAtlassianJiraRestV2IssueIssueResourceCreateIssuePost(mapForJira, updateHistory)
            return mapCreatedIssueToJiraCreatedIssueData(createdIssue)
        } catch (ex: Exception) {
            logger.error("Failed to create Jira issue: ${inputData.summary}. Raw data:\n%s"
                .format(mapForJira.toString()))
            throw ex
        }
    }

    override fun createIssuesInBulk(inputData: Set<JiraInputIssueData>): Set<JiraCreatedIssueData> {
        logger.debug("Creating %d issues: [%s]"
            .format(inputData.size, inputData.joinToString(", ") { it.summary }))

        val dataMapsForJira = inputData.map { wrapIssueDataInMapForJira(it) }.toSet()
        val mapForJira = mapOf(Pair("issueUpdates", dataMapsForJira))
        try {
            val rawCreatedIssues = rawApiClient.comAtlassianJiraRestV2IssueIssueResourceCreateIssuesPost(mapForJira)
            return rawCreatedIssues.issues!!.map { mapCreatedIssueToJiraCreatedIssueData(it) }.toSet()
        } catch (ex: Exception) {
            logger.error("Failed to create Jira issues. Raw data:\n%s".format(mapForJira.toString()))
            throw ex
        }
    }

    private fun wrapIssueDataInMapForJira(inputData: JiraInputIssueData): Map<String, Any> {
        return mapOf(Pair("fields", inputData.fieldMap))
    }

    private fun mapCreatedIssueToJiraCreatedIssueData(rawData: CreatedIssue): JiraCreatedIssueData {
        return JiraCreatedIssueData(
            rawData.id!!,
            rawData.key!!,
            rawData.self!!
        )
    }
}
