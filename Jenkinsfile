pipeline {
  agent any
    triggers {
     pollSCM('*/2 * * * *')
  }
  stages {
    stage('restapp build') {
      steps {
        'sh echo Building ${BRANCH_NAME} ....'
         sh 'sh /var/lib/jenkins/tools/hudson.tasks.Maven_MavenInstallation/M3/bin/mvn -e clean install'
        'sh echo completed build ..'
      }
    }
	stage('Build Docker Image'){
         sh docker.withServer('tcp://localhost:4342'){
         sh echo "Baking jar to docker image ..."
		 sh def imgname="sim-1.0"
         sh def Img = docker.build("opsmx11/restapp:$imgname")
         sh echo "Image id: $Img.id";
         sh echo "Build no: $BUILD_NUMBER";
         sh "echo \"build\": \"1.0\" > restapp.txt";
         sh archiveArtifacts artifacts: 'restapp.txt'
         sh echo "Launching container using this image.."
        }
    }
    stage('Push Image') {
         sh "sudo docker login --username opsmx11 --password Networks123!";
         sh "sudo docker push opsmx11/restapp:sim-1.0";
    }
  }
}
