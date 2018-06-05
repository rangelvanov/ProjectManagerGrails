package projectmanager

class Comment {

    Integer userId
    String content
    Date created

    static belongsTo = [task: Issue]
    static constraints = {
    }
}
