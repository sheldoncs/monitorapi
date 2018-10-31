pipeline {
    agent any
    stages {
        stage('Clone repository') {
            steps {
              checkout scm
            }
        }
		stage('Test') {
            steps {
               /* bat 'cd monitorapi && chmod +x mvnw  && ./mvnw test'*/
			   bat 'cd monitorapi && mvnw test'
            }
        }
        stage('Build and deploy') { 
		
		    
            steps {
			
			    withEnv([
				    "DOCKER_TLS_VERIFY=1",
					"DOCKER_HOST=tcp://localhost:2375",
					"DOCKER_CERT_PATH=C:/Users/administrator/.docker/machine/machines/default",
					"DB_USERNAME=sheldoncs",
					"DB_PASSWORD=kentish",
					"DB_HOST=192.168.99.1",
					"DB_PORT=3305"
                ]) {
                /*bat 'cd monitorapi && chmod +x mvnw  && ./mvnw clean compile package && ./mvnw -DskipTests dockerfile:build' */ 
				/*bat 'cd monitorapi && mvnw  && ./mvnw clean compile package && ./mvnw -DskipTests dockerfile:build' */
				bat 'cd monitorapi && mvnw clean compile package && mvnw -DskipTests dockerfile:build' 
				}
				
            }
        }
        
		

        
    }
     
}
