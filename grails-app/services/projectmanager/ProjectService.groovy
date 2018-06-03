package projectmanager

import grails.gorm.services.Service

@Service(Project)
interface ProjectService {

    Project get(Serializable id)

    List<Project> list(Map args)

    Long count()

    void delete(Serializable id)

    Project save(Project project)

}