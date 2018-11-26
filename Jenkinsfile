pipeline {
  agent any
  def mvnHome = tool 'M3'
  stages {
    stage('build') {
      steps {
        sh 'echo Building ${BRANCH_NAME}...'
        sh 'sh "${mvnHome}/bin/mvn clean package' 
      }
    }
  }
}
