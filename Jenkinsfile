node {
    def mvnHome
    def dockerHome
    stage('Preparation') {
        git '/data/git/dvdstore-api.git'
        mvnHome = tool 'M3'
    }
    stage('Database Start') {
        dockerHome = tool 'DOCKER'
        sh "${dockerHome}/bin/docker run --name dvdstore-db -p 5432:5432 -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=password -e POSTGRES_DB=dellstore2 -d scottseo/dvdstore-db"
    }
    stage('Build') {
        env.com.scottwseo.api.CONFIG_URL="http://localhost:8000/localhost.config.properties"
        sh "'${mvnHome}/bin/mvn' clean package"
    }
    stage('Results') {
        junit '**/target/surefire-reports/TEST-*.xml'
        archive 'target/*.jar'
    }
    stage('Database Stop') {
        sh "${dockerHome}/bin/docker rm -f dvdstore-db"
    }
    step([$class: 'JacocoPublisher'])
/*    stage('Docker Push') {
        docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-login') {
            docker.build('scottseo/dvdstore-api').push('latest')
        }
    }*/
}