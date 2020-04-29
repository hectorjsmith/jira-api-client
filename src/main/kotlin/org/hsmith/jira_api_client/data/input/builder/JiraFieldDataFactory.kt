package org.hsmith.jira_api_client.data.input.builder

import org.hsmith.jira_api_client.data.input.JiraFieldInput

interface JiraFieldDataFactory {
    fun <TValueType : Any> newSimpleField(fieldId: String, value: TValueType): JiraFieldInput
    fun <TValueType : Any> newIdMapField(fieldId: String, value: TValueType): JiraFieldInput
    fun <TValueType : Any> newKeyMapField(fieldId: String, value: TValueType): JiraFieldInput
    fun <TValueType : Any> newNameMapField(fieldId: String, value: TValueType): JiraFieldInput
    fun <TValueType : Any> newValueMapField(fieldId: String, value: TValueType): JiraFieldInput
    fun newDocumentField(fieldId: String, value: String): JiraFieldInput
    fun <TValueType : Any> newMultiValueNameField(fieldId: String, valueSet: Set<TValueType>): JiraFieldInput
}
