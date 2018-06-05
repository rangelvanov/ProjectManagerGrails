package projectmanager

import grails.gorm.services.Service

@Service(Issue)
interface IssueService {

    Issue get(Serializable id)

    List<Issue> list(Map args)

    Long count()

    void delete(Serializable id)

    Issue save(Issue task)

}