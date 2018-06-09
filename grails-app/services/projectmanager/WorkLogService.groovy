package projectmanager

import grails.gorm.services.Service

@Service(WorkLog)
interface WorkLogService {

    WorkLog get(Serializable id)

    List<WorkLog> list(Map args)

    Long count()

    void delete(Serializable id)

    WorkLog save(WorkLog workLog)

}