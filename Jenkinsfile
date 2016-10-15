node {
    def mvnHome
    def dockerHome
    stage('Preparation') {
        mvnHome = tool 'M3'
    }
    stage('Database Start') {
        dockerHome = tool 'DOCKER'
        sh "docker ps | grep dvdstore-db | awk {'print \$1'} | xargs docker rm -f"
        sh "${dockerHome}/bin/docker run --name dvdstore-db -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=password -e POSTGRES_DB=dellstore2 -d scottseo/dvdstore-db"
    }
    stage('Build') {
        docker.image('maven:3.3.3-jdk-8').inside('--link dvdstore-db:db') {

          git '/data/git/dvdstore-api.git'

          writeFile file: 'settings.xml', text: "<settings><localRepository>${pwd()}/.m2repo</localRepository></settings>"

          withEnv(["com.scottwseo.api.CONFIG_URL=https://s3.amazonaws.com/config.scottwseo.com/junit/local.config.properties"]) {
            sh 'mvn -B -s settings.xml clean install'
          }

        }
    }
    stage('Results') {
        junit '**/target/surefire-reports/TEST-*.xml'
        archive 'target/*.jar'
    }
    stage('Database Stop') {
        sh "${dockerHome}/bin/docker rm -f dvdstore-db"
    }
    step([$class: 'hudson.plugins.jacoco.JacocoPublisher'])

    stage('Docker Push') {
        docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-login') {
            docker.build('scottseo/dvdstore-api').push('latest')
        }
    }
}