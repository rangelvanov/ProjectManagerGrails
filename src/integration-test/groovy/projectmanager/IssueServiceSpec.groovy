package projectmanager

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class IssueServiceSpec extends Specification {

    IssueService taskService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Issue(...).save(flush: true, failOnError: true)
        //new Issue(...).save(flush: true, failOnError: true)
        //Issue issue = new Issue(...).save(flush: true, failOnError: true)
        //new Issue(...).save(flush: true, failOnError: true)
        //new Issue(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //issue.id
    }

    void "test get"() {
        setupData()

        expect:
        taskService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Issue> taskList = taskService.list(max: 2, offset: 2)

        then:
        taskList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        taskService.count() == 5
    }

    void "test delete"() {
        Long taskId = setupData()

        expect:
        taskService.count() == 5

        when:
        taskService.delete(taskId)
        sessionFactory.currentSession.flush()

        then:
        taskService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Issue task = new Issue()
        taskService.save(task)

        then:
        task.id != null
    }
}
