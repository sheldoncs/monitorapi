pipeline {
    agent any
    stages {
        stage('Clone repository') {
            steps {
              checkout scm
            }
        }
        stage('Build') { 
		
		    
            steps {
			
			    withEnv([
				    "DOCKER_TLS_VERIFY=1",
					"DOCKER_HOST=tcp://172.17.0.2:2376",
					"DOCKER_CERT_PATH=C:/Users/administrator/.docker/machine/machines/default"
                ]) {
                /*bat 'cd monitorapi && chmod +x mvnw  && ./mvnw clean compile package && ./mvnw -DskipTests dockerfile:build' */ 
				/*bat 'cd monitorapi && mvnw  && ./mvnw clean compile package && ./mvnw -DskipTests dockerfile:build' */
				bat 'cd monitorapi && mvnw clean compile package && mvnw -DskipTests dockerfile:build' 
				}
				
            }
        }
        stage('Test') {
            steps {
               /* bat 'cd monitorapi && chmod +x mvnw  && ./mvnw test'*/
			   bat 'cd monitorapi && mvnw test'
            }
        }
		

        
    }
     
}
