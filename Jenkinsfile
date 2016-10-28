node {
    def mvnHome
    def dockerHome
    stage('Preparation') {
        mvnHome = tool 'M3'
    }
    stage('Database Start') {
        dockerHome = tool 'DOCKER'
        sh "docker ps | grep dvdstore-db | awk {'print \$1'} | xargs docker rm -f"
        sh "docker ps | grep zipkin | awk {'print \$1'} | xargs docker rm -f"
        sh "${dockerHome}/bin/docker run --name dvdstore-db -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=password -e POSTGRES_DB=dellstore2 -d scottseo/dvdstore-db"
        sh "${dockerHome}/bin/docker run --name zipkin -d openzipkin/zipkin"
    }
    stage('Build') {
        docker.image('maven:3.3.3-jdk-8').inside('--link dvdstore-db:db --link zipkin:zipkin') {

          git '/data/git/dvdstore-api.git'

          writeFile file: 'settings.xml', text: "<settings><localRepository>${pwd()}/.m2repo</localRepository></settings>"

          withEnv(["SWS_API_CONFIG_URL=https://s3.amazonaws.com/config.scottwseo.com/junit/local.config.properties"]) {
            sh 'mvn -B -s settings.xml clean install'
          }

        }
    }
    stage('Results') {
        junit '**/target/surefire-reports/TEST-*.xml'
        archive 'target/*.jar'
        step([$class                    : 'hudson.plugins.jacoco.JacocoPublisher',
                      execPattern               : '**/**.exec',
                      classPattern              : '*/classes',
                      sourcePattern             : '**/src/main/java',
                      inclusionPattern          : '',
                      exclusionPattern          : '/com/scottwseo/commons/**/*',
                      minimumInstructionCoverage: '0',
                      minimumBranchCoverage     : '0',
                      minimumComplexityCoverage : '0',
                      minimumLineCoverage       : '1',
                      minimumMethodCoverage     : '0',
                      minimumClassCoverage      : '0',
                      maximumInstructionCoverage: '0',
                      maximumBranchCoverage     : '0',
                      maximumComplexityCoverage : '0',
                      maximumLineCoverage       : '0',
                      maximumMethodCoverage     : '0',
                      maximumClassCoverage      : '0',
                      changeBuildStatus         : false
                ])
    }
    stage('Database Stop') {
        sh "${dockerHome}/bin/docker rm -f dvdstore-db"
        sh "${dockerHome}/bin/docker rm -f zipkin"
    }
    stage('Docker Push') {
        docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-login') {
            docker.build('scottseo/dvdstore-api').push('latest')
        }
    }
}