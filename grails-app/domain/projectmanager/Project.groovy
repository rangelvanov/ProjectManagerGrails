package projectmanager

class Project {

    String title
    String description
    Integer userId
    Date startDate
    Date dateLastChange
    StatusP status

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof Project)) return false

        Project project = (Project) o

        if (org_grails_datastore_gorm_GormValidateable__skipValidate != project.org_grails_datastore_gorm_GormValidateable__skipValidate) return false
        if (dateLastChange != project.dateLastChange) return false
        if (description != project.description) return false
        if (id != project.id) return false
        if (org_grails_datastore_gorm_GormValidateable__errors != project.org_grails_datastore_gorm_GormValidateable__errors) return false
        if (org_grails_datastore_mapping_dirty_checking_DirtyCheckable__$changedProperties != project.org_grails_datastore_mapping_dirty_checking_DirtyCheckable__$changedProperties) return false
        if (startDate != project.startDate) return false
        if (status != project.status) return false
        if (tasks != project.tasks) return false
        if (title != project.title) return false
        if (userId != project.userId) return false
        if (version != project.version) return false

        return true
    }

    int hashCode() {
        int result
        result = (title != null ? title.hashCode() : 0)
        result = 31 * result + (description != null ? description.hashCode() : 0)
        result = 31 * result + (userId != null ? userId.hashCode() : 0)
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0)
        result = 31 * result + (dateLastChange != null ? dateLastChange.hashCode() : 0)
        result = 31 * result + (status != null ? status.hashCode() : 0)
        result = 31 * result + (id != null ? id.hashCode() : 0)
        result = 31 * result + (version != null ? version.hashCode() : 0)
        result = 31 * result + (tasks != null ? tasks.hashCode() : 0)
        result = 31 * result + (org_grails_datastore_mapping_dirty_checking_DirtyCheckable__$changedProperties != null ? org_grails_datastore_mapping_dirty_checking_DirtyCheckable__$changedProperties.hashCode() : 0)
        result = 31 * result + (org_grails_datastore_gorm_GormValidateable__skipValidate ? 1 : 0)
        result = 31 * result + (org_grails_datastore_gorm_GormValidateable__errors != null ? org_grails_datastore_gorm_GormValidateable__errors.hashCode() : 0)
        return result
    }

    static  def getAllByTRU(final long idParam){
        HashSet<Project> result = new HashSet<Project>()
        def allProjects = Project.findAll()
        for(Project project : allProjects){
            if(idParam == project.userId){
                result.add(project)
            }
            else{
                for(Task task : project.tasks){
                    if(idParam == task.userId || idParam == task.responsibleUserId){
                        result.add(project)
                    }
                }
            }
        }
        return result
    }
    static hasMany = [tasks: Task]

    static constraints = {
    }
}
