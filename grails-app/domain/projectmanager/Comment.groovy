package projectmanager

class Comment {

    Integer userId
    String commentContent
    Date date

    static belongsTo = [task: Task]
    static constraints = {
    }
}
