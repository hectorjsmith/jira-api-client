package org.hsmith.jira_api_client.data.input.builder

import org.hsmith.jira_api_client.data.input.JiraFieldInput
import org.hsmith.jira_api_client.data.input.JiraInputIssueData
import org.hsmith.jira_api_client.data.input.JiraIssueFieldId
import java.time.LocalDateTime

open class JiraRestInputIssueDataBuilder(
    private val projectKey: String,
    private val issueTypeId: String,
    private val summary: String
) : JiraInputIssueDataBuilder {
    private var description: String? = null
    private var dueDate: LocalDateTime? = null
    private var priorityId: String? = null
    private val affectedVersionNames: MutableSet<String> = HashSet()
    private val fixVersionNames: MutableSet<String> = HashSet()
    private val componentNames: MutableSet<String> = HashSet()

    private val fields: HashMap<String, Any> = HashMap()
    protected val fieldFactory: JiraFieldDataFactory = JiraRestFieldDataFactory()

    init {
        assertStringFieldIsNotBlank(projectKey, this::projectKey.name)
        assertStringFieldIsNotBlank(issueTypeId, this::issueTypeId.name)
        assertStringFieldIsNotBlank(summary, this::summary.name)

        setFieldInput(fieldFactory.newKeyMapField(JiraIssueFieldId.PROJECT_FIELD.id, projectKey))
        setFieldInput(fieldFactory.newIdMapField(JiraIssueFieldId.ISSUE_TYPE_FIELD.id, issueTypeId))
        setFieldInput(fieldFactory.newSimpleField(JiraIssueFieldId.SUMMARY_FIELD.id, summary))
    }

    final override fun setDescription(description: String): JiraInputIssueDataBuilder {
        if (description.isNotBlank()) {
            this.description = description
            setFieldInput(fieldFactory.newDocumentField(JiraIssueFieldId.DESCRIPTION_FIELD.id, description))
        }
        return this
    }

    final override fun setAffectedVersionsNames(affectedVersionNames: Set<String>): JiraInputIssueDataBuilder {
        if (affectedVersionNames.any { it.isNotBlank() }) {
            this.affectedVersionNames.addAll(affectedVersionNames)
            setFieldInput(
                fieldFactory.newMultiValueNameField(
                    JiraIssueFieldId.AFFECTS_VERSIONS_FIELD.id,
                    affectedVersionNames
                )
            )
        }
        return this
    }

    final override fun setFixVersionsNames(fixVersionNames: Set<String>): JiraInputIssueDataBuilder {
        if (fixVersionNames.any { str -> str.isNotBlank() }) {
            this.fixVersionNames.addAll(fixVersionNames)
            setFieldInput(fieldFactory.newMultiValueNameField(JiraIssueFieldId.FIX_VERSIONS_FIELD.id, fixVersionNames))
        }
        return this
    }

    final override fun setComponentsNames(componentNames: Set<String>): JiraInputIssueDataBuilder {
        if (componentNames.any { str -> str.isNotBlank() }) {
            this.componentNames.addAll(componentNames)
            setFieldInput(fieldFactory.newMultiValueNameField(JiraIssueFieldId.COMPONENTS_FIELD.id, componentNames))
        }
        return this
    }

    final override fun setDueDate(dueDate: LocalDateTime): JiraInputIssueDataBuilder {
        this.dueDate = dueDate
        setFieldInput(fieldFactory.newSimpleField(JiraIssueFieldId.DUE_DATE_FIELD.id, dueDate.toString()))
        return this
    }

    final override fun setPriorityId(priorityId: String): JiraInputIssueDataBuilder {
        if (priorityId.isNotBlank()) {
            this.priorityId = priorityId
            setFieldInput(fieldFactory.newIdMapField(JiraIssueFieldId.PRIORITY_FIELD.id, priorityId))
        }
        return this
    }

    protected fun setFieldInput(field: JiraFieldInput) {
        fields[field.id] = field.value
    }

    final override fun build(): JiraInputIssueData {
        validate()
        return JiraInputIssueData(
            fieldMap = fields,
            projectKey = projectKey,
            issueTypeId = issueTypeId,
            summary = summary
        )
    }

    protected open fun getRequiredFieldsFromExtensionClass(): Set<String> {
        return HashSet()
    }

    protected open fun validateExtensionClass() {
    }

    private fun validate() {
        validateExtensionClass()
        val missingFields = findMissingRequiredFields()
        if (missingFields.isNotEmpty()) {
            throw IllegalArgumentException(
                "Cannot build story data, fields missing: %s"
                    .format(missingFields.joinToString(", ") { it })
            )
        }
    }

    private fun findMissingRequiredFields(): Set<String> {
        val extraRequiredFields = getRequiredFieldsFromExtensionClass()
        val baseRequiredFields = setOf(
            JiraIssueFieldId.SUMMARY_FIELD.id,
            JiraIssueFieldId.ISSUE_TYPE_FIELD.id
        )
        return baseRequiredFields.plus(extraRequiredFields)
            .filter { f -> !fields.containsKey(f) }
            .toSet()
    }

    private fun assertStringFieldIsNotBlank(field: String, fieldName: String) {
        if (field.isBlank()) {
            error("$fieldName must not be blank")
        }
    }
}
