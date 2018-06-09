package projectmanager

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class WorkLogServiceSpec extends Specification {

    WorkLogService workLogService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new WorkLog(...).save(flush: true, failOnError: true)
        //new WorkLog(...).save(flush: true, failOnError: true)
        //WorkLog workLog = new WorkLog(...).save(flush: true, failOnError: true)
        //new WorkLog(...).save(flush: true, failOnError: true)
        //new WorkLog(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //workLog.id
    }

    void "test get"() {
        setupData()

        expect:
        workLogService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<WorkLog> workLogList = workLogService.list(max: 2, offset: 2)

        then:
        workLogList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        workLogService.count() == 5
    }

    void "test delete"() {
        Long workLogId = setupData()

        expect:
        workLogService.count() == 5

        when:
        workLogService.delete(workLogId)
        sessionFactory.currentSession.flush()

        then:
        workLogService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        WorkLog workLog = new WorkLog()
        workLogService.save(workLog)

        then:
        workLog.id != null
    }
}
