pipeline {
  agent any
  stages {
    stage('restapp builds stage') {
      steps {
        sh 'echo Building ${BRANCH_NAME}....'
        sh 'sh /var/lib/jenkins/tools/hudson.tasks.Maven_MavenInstallation/M3/bin/mvn -e clean install' 
      }
    }
  }
}
