package projectmanager


class Issue {

    String name
    IssueType type
    IssuePriority priority
    String description
    StatusT status
    Long ownerId

    Long assigneeId
    Date created
    Date updated


    static belongsTo = [project: Project]
    static hasMany = [comments: Comment,workLogs: WorkLog]



}
