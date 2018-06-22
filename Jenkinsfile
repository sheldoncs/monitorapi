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
                /*bat 'cd monitorapi && chmod +x mvnw  && ./mvnw clean compile package && ./mvnw -DskipTests dockerfile:build' */ 
				/*bat 'cd monitorapi && mvnw  && ./mvnw clean compile package && ./mvnw -DskipTests dockerfile:build' */
				bat 'cd monitorapi && mvnw clean compile package && mvnw -DskipTests dockerfile:build' 
				
            }
        }
        stage('Test') {
            steps {
               /* bat 'cd monitorapi && chmod +x mvnw  && ./mvnw test'*/
			   bat 'cd monitorapi && mvnw test'
            }
        }
		stage("Deploy	to	staging")	{
		  steps	{								
		         /* bat "cd monitorapi && docker-compose	up	-d"	*/
                 bat "cd monitorapi && docker-compose	up	-d"				 
				} 
		}

        
    }
     
}
