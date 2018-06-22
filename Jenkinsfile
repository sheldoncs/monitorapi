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
                sh 'cd monitorapi && chmod +x mvnw  && ./mvnw clean compile package && ./mvnw -DskipTests dockerfile:build'  
            }
        }
        stage('Test') {
            steps {
                sh 'cd monitorapi && chmod +x mvnw  && ./mvnw test'
            }
        }
		stage("Deploy	to	staging")	{
		  steps	{								
		          sh "cd monitorapi && docker-compose	up	-d"				
				} 
		}

        
    }
     
}
