pipeline {
  agent any
    triggers { 
      pollSCM('H */4 * * 1-5')
  }
  stages {
    stage('restapp build-v1') {
      steps {
        sh 'echo Building ${BRANCH_NAME} ....'
        sh 'sh /var/lib/jenkins/tools/hudson.tasks.Maven_MavenInstallation/M3/bin/mvn -e clean install' 
        sh 'echo completed build for poll scm...'
      }
    }
  }
}
