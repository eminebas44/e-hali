pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    stages {
        stage('1- Checkout') {
            steps {
                checkout scm
            }
        }

        stage('2- Build') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('3- Unit Tests') {
            steps {
                bat 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('4- Integration Tests') {
            steps {
                bat 'mvn verify -DskipUnitTests'
            }
            post {
                always {
                    junit '**/target/failsafe-reports/*.xml'
                }
            }
        }

        stage('5- Docker Run') {
            steps {
                bat 'docker-compose down'
                bat 'docker-compose up --build -d'
            }
        }

        stage('6- Selenium System Tests') {
            steps {
                echo 'Selenium testleri bu asamada kosulacak'
            }
        }
    }
}