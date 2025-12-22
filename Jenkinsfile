pipeline {
    agent any
    stages {
        stage('1- Checkout') {
            steps {
                checkout scm
            }
        }
        stage('2- Build') {
            steps {
                sh './mvnw clean compile -DskipTests'
            }
        }
        stage('3- Unit Tests') {
            steps {
                sh './mvnw test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        stage('4- Integration Tests') {
            steps {
                sh './mvnw verify -DskipUnitTests'
            }
        }
        stage('5- Docker Run') {
            steps {
                sh 'docker-compose down'
                sh 'docker-compose up --build -d'
            }
        }
    }
}