package org.hsmith.jira_api_client.data.input.builder

import org.hsmith.jira_api_client.data.input.JiraFieldInput

class JiraRestFieldDataFactory : JiraFieldDataFactory {
    override fun <TValueType : Any> newSimpleField(fieldId: String, value: TValueType): JiraFieldInput {
        return JiraFieldInput(fieldId, value)
    }

    override fun <TValueType : Any> newIdMapField(fieldId: String, value: TValueType): JiraFieldInput {
        return JiraFieldInput(fieldId, buildMap("id", value))
    }

    override fun <TValueType : Any> newKeyMapField(fieldId: String, value: TValueType): JiraFieldInput {
        return JiraFieldInput(fieldId, buildMap("key", value))
    }

    override fun <TValueType : Any> newNameMapField(fieldId: String, value: TValueType): JiraFieldInput {
        return JiraFieldInput(fieldId, buildMap("name", value))
    }

    override fun <TValueType : Any> newValueMapField(fieldId: String, value: TValueType): JiraFieldInput {
        return JiraFieldInput(fieldId, buildMap("value", value))
    }

    override fun newDocumentField(fieldId: String, value: String): JiraFieldInput {
        val document = HashMap<String, Any>()
        document["type"] = "doc"
        document["version"] = 1

        val content = HashMap<String, Any>()
        content["type"] = "paragraph"

        val innerContent = HashMap<String, Any>()
        innerContent["type"] = "text"
        innerContent["text"] = value

        content["content"] = arrayOf(innerContent as Map<String, Any>)
        document["content"] = arrayOf(content as Map<String, Any>)

        return JiraFieldInput(fieldId, document)
    }

    override fun <TValueType : Any> newMultiValueNameField(fieldId: String, valueSet: Set<TValueType>): JiraFieldInput {
        val valueMapSet: Set<Map<String, TValueType>> = valueSet.map { value -> buildMap("name", value) }.toSet()
        return newSimpleField(fieldId, valueMapSet)
    }

    private fun <TValueType : Any> buildMap(key: String, value: TValueType): Map<String, TValueType> {
        return mapOf(Pair(key, value))
    }
}
