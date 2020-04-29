# Jira API Client

Library to integrate with Atlassian Jira using REST APIs.

## How to use

### Credentials

This library always uses a username & api token combination. It should **not** be used with a password.

To create the credentials object:

```kotlin
val jiraCredentials = JiraBasicAuthCredentials("username", "api-token")
```

### Main API

Create a new instance of the `JiraApi` interface and provide the URI to the jira instance and the credentials object.

```kotlin
val jiraApi: JiraApi = JiraRestApi("https://jira.com/", jiraCredentials)
```

From the `JiraApi` interface it is possible to get the `JiraIssueApi` to handle issues and the `JiraIssueTypesApi` to handle issue types.

### Create a new issue

To create a new issue the following is required:
- A `JiraApi` instance (see above)
- A `JiraInputIssueDataBuilder` instance
- The ID of the issue type

To get the ID of the issue type the `JiraIssueTypesApi` can be used. This allows fetching all the available issue types along with their IDs.

```kotlin
// Option 1 - Fetch raw jira issue type data (more flexible)
val allIssueTypes = jiraApi.issueTypesApi.getAllIssueTypes()
val myIssueType = allIssueTypes.first { it.name == "my custom issue" }
val myIssueTypeId = myIssueType.id

// Option 2 - Get a map from issue type name to ID
val issueTypeNameToIdMap = jiraApi.issueTypesApi.getIssueTypeNameToIdMap()
val myIssueTypeId = issueTypeNameToIdMap["my custom issue"]
```

The `JiraInputIssueDataBuilder` should be used to build the data for the issue that is going to get imported into Jira.

```kotlin
// Create builder with required fields
val builder = JiraRestInputIssueDataBuilder(
        projectKey = "PROJECT-KEY",
        summary = "My new issue",
        issueTypeId = "10101"
    )

// Add values for other fields
builder.setDescription("description")
builder.setDueDate(LocalDateTime.now().plusDays(10))

// Build the data object
val jiraInputIssueData = builder.build()
```

Now that the issue data object has been constructed it can be pushed into Jira. To do this, use the `JiraIssueApi`.

```kotlin
val response = jiraApi.issueApi.createIssue(jiraInputIssueData)
```

The response object includes the Key of the newly created issue.

### Create issues in bulk

Very similar setup to creating a single issue, but use the `createIssuesInBulk` method on the `JiraIssueApi` interface.
This method accepts a set of input data and returns a set of output data.

## Credits
- Wraps around the [KotlinJiraCloudClient](https://github.com/ColmBhandal/KotlinJiraCloudClient) library
