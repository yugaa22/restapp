pipeline {
  agent any
    triggers {
     pollSCM('*/2 * * * *')
  }
  stages {
    stage('restapp build') {
      steps {
        'echo Building ${BRANCH_NAME} ....'
         sh 'sh /var/lib/jenkins/tools/hudson.tasks.Maven_MavenInstallation/M3/bin/mvn -e clean install'
         'echo completed build ..'
      }
    }
	stage('Build Docker Image'){
         sh docker.withServer('tcp://localhost:4342'){
         echo "Baking jar to docker image ..."
		 def imgname="sim-1.0"
         def Img = docker.build("opsmx11/restapp:$imgname")
         echo "Image id: $Img.id";
         echo "Build no: $BUILD_NUMBER";
         sh "echo \"build\": \"1.0\" > restapp.txt";
         archiveArtifacts artifacts: 'restapp.txt'
         echo "Launching container using this image.."
        }
    }
    stage('Push Image') {
         sh "sudo docker login --username opsmx11 --password Networks123!";
         sh "sudo docker push opsmx11/restapp:sim-1.0";
    }
  }
}
