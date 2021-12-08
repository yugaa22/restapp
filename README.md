# restapp
springboot debian app for spinnaker

For Debian run cmd restapp jar created  with deb file 
./gradlew build fatJar packDeb --stacktrace


For jar  
./mvn clean install -e 

in target folder resapp.jar created.by running jar 
java  -jar restapp.jar

access by this url  http://localhost:8080/greeting


