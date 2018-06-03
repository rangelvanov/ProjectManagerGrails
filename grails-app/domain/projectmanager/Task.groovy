package projectmanager

import com.ProjectManager.auth.User

class Task {

    String title
    String description
    Integer userId
    Integer responsibleUserId
    Date startDate
    Date dateLastChange
    StatusT status

    static belongsTo = [project: Project]
    static hasMany = [comments: Comment]

    static constraints = {
    }
}
