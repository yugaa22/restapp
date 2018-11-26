pipeline {
  agent any
  environment {
  mvnHome = tool 'M3'
  }
  stages {
    stage('build') {
      steps {
        sh 'echo Building ${BRANCH_NAME}...'
        sh 'sh env.mvnHome/bin/mvn clean package' 
      }
    }
  }
}
