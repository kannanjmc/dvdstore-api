node {
    def mvnHome
    def dockerHome
    stage('Preparation') {
        git '/data/git/dvdstore-api.git'
        mvnHome = tool 'M3'
    }
    stage('Database Start') {
        dockerHome = tool 'DOCKER'
        sh "${dockerHome}/bin/docker run --name dvdstore-db -p 5432:5432 -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=dellstore2 -d scottseo/dvdstore-db"
    }
    stage('Build') {
        sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore -Dmaven.javadoc.skip=true clean package"
    }
    stage('Results') {
        junit '**/target/surefire-reports/TEST-*.xml'
        archive 'target/*.jar'
        input message: "Did all the tests pass?", ok: "Continue"
    }
    stage('Database Stop') {
        sh "${dockerHome}/bin/docker rm -f dvdstore-db"
    }
    stage('Docker') {
        docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-login') {
            docker.build('scottseo/dvdstore-api').push('latest')
        }
    }
}