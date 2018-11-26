pipeline {
  agent any
  environment {
  mvnHome = tool 'M3'
  }
  stages {
    stage('build') {
      steps {
        sh 'echo Building ${BRANCH_NAME}...'
        sh 'sh /var/lib/jenkins/tools/hudson.tasks.Maven_MavenInstallation/M3/bin/mvn clean package' 
      }
    }
  }
}
