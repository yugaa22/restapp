pipeline {
  agent any
  triggers { 
     pollSCM('*/2 * * * *')
  }
  stages {
    stage('restapp builds') {
      steps {
        sh 'echo Building ${BRANCH_NAME}....'
        sh 'sh /var/lib/jenkins/tools/hudson.tasks.Maven_MavenInstallation/M3/bin/mvn -e clean install' 
        sh 'echo  completed build ....'
      }
    }
  }
}
