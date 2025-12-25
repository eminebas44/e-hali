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
                bat 'mvn test -Dtest=!org.example.ehali.selenium.**'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('4- Integration Tests') {
            steps {
                bat 'mvn verify -DskipUnitTests -Dtest=!org.example.ehali.selenium.**'
            }
        }

        stage('5- Docker Run') {
            steps {
                bat 'docker-compose down'
                bat 'docker-compose up --build -d'
                bat 'ping -n 45 127.0.0.1 > nul'
            }
        }

        stage('6.1- Selenium: Kayit Testi') {
            steps {
                bat 'mvn test -Dtest=KullaniciKayitTest'
            }
            post {
                always {
                    junit '**/target/surefire-reports/TEST-org.example.ehali.selenium.KullaniciKayitTest.xml'
                }
            }
        }

        stage('6.2- Selenium: Hazirlik Testi') {
            steps {
                bat 'mvn test -Dtest=SistemHazirlikTest'
            }
            post {
                always {
                    junit '**/target/surefire-reports/TEST-org.example.ehali.selenium.SistemHazirlikTest.xml'
                }
            }
        }

        stage('6.3- Selenium: Sayfa Icerik Testi') {
            steps {
                bat 'mvn test -Dtest=SayfaGezintiTest'
            }
            post {
                always {
                    junit '**/target/surefire-reports/TEST-org.example.ehali.selenium.SayfaGezintiTest.xml'
                }
            }
        }
    }
}