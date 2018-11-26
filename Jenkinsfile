pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        sh 'echo Building ${BRANCH_NAME}...'
        sh '-DskipTests clean package' 
      }
    }
  }
}
