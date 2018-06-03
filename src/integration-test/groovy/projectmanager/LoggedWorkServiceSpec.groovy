package projectmanager

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class LoggedWorkServiceSpec extends Specification {

    LoggedWorkService loggedWorkService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new LoggedWork(...).save(flush: true, failOnError: true)
        //new LoggedWork(...).save(flush: true, failOnError: true)
        //LoggedWork loggedWork = new LoggedWork(...).save(flush: true, failOnError: true)
        //new LoggedWork(...).save(flush: true, failOnError: true)
        //new LoggedWork(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //loggedWork.id
    }

    void "test get"() {
        setupData()

        expect:
        loggedWorkService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<LoggedWork> loggedWorkList = loggedWorkService.list(max: 2, offset: 2)

        then:
        loggedWorkList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        loggedWorkService.count() == 5
    }

    void "test delete"() {
        Long loggedWorkId = setupData()

        expect:
        loggedWorkService.count() == 5

        when:
        loggedWorkService.delete(loggedWorkId)
        sessionFactory.currentSession.flush()

        then:
        loggedWorkService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        LoggedWork loggedWork = new LoggedWork()
        loggedWorkService.save(loggedWork)

        then:
        loggedWork.id != null
    }
}
