pipeline {
  agent any
  stages {
    stage('restapp build v4') {
      steps {
        sh 'echo Building ${BRANCH_NAME} ....'
        sh 'sh /var/lib/jenkins/tools/hudson.tasks.Maven_MavenInstallation/M3/bin/mvn -e clean install' 
        sh 'echo completed build for test ....'
      }
    }
  }
}
