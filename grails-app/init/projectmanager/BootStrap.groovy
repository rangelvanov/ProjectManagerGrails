package projectmanager

import com.ProjectManager.auth.Role
import com.ProjectManager.auth.User
import com.ProjectManager.auth.UserRole

class BootStrap {
    def init = { servletContext ->
        def adminRole = Role.findOrSaveWhere(authority:'ROLE_ADMIN')
        def userRole = Role.findOrSaveWhere(authority: 'ROLE_USER')
        def superUserRole = Role.findOrSaveWhere(authority: 'ROLE_SUPERUSER')


        def user1 = User.findOrSaveWhere(username:'admin',password:'pass')       // admin
        def user2 = User.findOrSaveWhere(username:'superuser',password:'pass')   // superuser
        def user3 = User.findOrSaveWhere(username:'kiko',password:'pass')        // user
        def user4 = User.findOrSaveWhere(username:'rangel',password:'pass')      // user
//        def user5 = User.findOrSaveWhere(username:'empty',password:'pass')      // user empty

        if(!user1.authorities.contains(adminRole)){
            UserRole.create(user1,adminRole,true)   // setting admin
        }
        if(!user2.authorities.contains(superUserRole)){
            UserRole.create(user2,superUserRole,true) // setting superUser
        }

        if(!user3.authorities.contains(userRole)){
            UserRole.create(user3,userRole,true) // setting User
        }

        if(!user4.authorities.contains(userRole)){
            UserRole.create(user4,userRole,true) // setting User
        }


        def project1 = Project.findOrSaveWhere(title: 'Grails Project',  description: 'desc1', userId: 2,startDate: new Date(),dateLastChange: new Date(), status: StatusP.Development)
        def project2 = Project.findOrSaveWhere(title: 'Java Project'   , description: 'desc1', userId: 2,startDate: new Date(),dateLastChange: new Date(), status: StatusP.Development)
        def project3 = Project.findOrSaveWhere(title: 'Groovy Project',  description: 'desc1', userId: 2,startDate: new Date(),dateLastChange: new Date(), status: StatusP.Development)
        def project4 = Project.findOrSaveWhere(title: 'Eclipse Project',  description: 'desc1', userId: 2,startDate: new Date(),dateLastChange: new Date(), status: StatusP.Development)


        def task1 = Task.findOrSaveWhere(title:'task1', description:'desc1',responsibleUserId: 2, userId: 2, startDate: new Date(), dateLastChange: new Date(), status: StatusT.Pending, project:project1 )
        def task2 = Task.findOrSaveWhere(title:'task2', description:'desc1',responsibleUserId: 2, userId: 2, startDate: new Date(), dateLastChange: new Date(), status: StatusT.Pending, project:project1 )
        def task3 = Task.findOrSaveWhere(title:'task3', description:'desc1',responsibleUserId: 3, userId: 2, startDate: new Date(), dateLastChange: new Date(), status: StatusT.Pending, project:project2 )
        def task4 = Task.findOrSaveWhere(title:'task4', description:'desc1',responsibleUserId: 3, userId: 2, startDate: new Date(), dateLastChange: new Date(), status: StatusT.Pending, project:project4 )
    }
    def destroy = {
    }
}
