package projectmanager

class WorkLog {

    Long userId
    Date created
    Integer timeSpent
    String comment

    static belongsTo = [issue: Issue]
}
