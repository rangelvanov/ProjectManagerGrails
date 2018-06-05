package projectmanager

class Comment {

    Long userId
    String content
    Date created

    static belongsTo = [issue: Issue]
}
