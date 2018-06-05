package projectmanager

class Project {

    String name
    String key
    ProjectType type
    Integer ownerId
    Category category

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof Project)) return false

        Project project = (Project) o

        if (category != project.category) return false
        if (key != project.key) return false
        if (name != project.name) return false
        if (ownerId != project.ownerId) return false
        if (type != project.type) return false

        return true
    }

    int hashCode() {
        int result
        result = (name != null ? name.hashCode() : 0)
        result = 31 * result + (key != null ? key.hashCode() : 0)
        result = 31 * result + (type != null ? type.hashCode() : 0)
        result = 31 * result + (ownerId != null ? ownerId.hashCode() : 0)
        result = 31 * result + (category != null ? category.hashCode() : 0)
        return result
    }

    static def getAllByTRU(final long idParam) {
        HashSet<Project> result = new HashSet<Project>()
        def allProjects = Project.findAll()
        for (Project project : allProjects) {
            if (idParam == project.ownerId) {
                result.add(project)
            } else {
                for (Issue task : project.tasks) {
                    if (idParam == task.ownerId || idParam == task.assigneeId) {
                        result.add(project)
                    }
                }
            }
        }
        return result
    }
    static hasMany = [tasks: Issue]

    static constraints = {
    }
}
