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
                bat 'mvn clean compile -DskipTests'
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
        }
        stage('5- Docker Run') {
            steps {
                bat 'docker-compose down'
                bat 'docker-compose up --build -d'
            }
        }
    }
}