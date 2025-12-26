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

        stage('6.1- Selenium: Kullanıcı Kayıt') {
            steps {
                bat 'mvn test -Dtest=KullaniciKayitTest'
            }
            post {
                always {
                    junit '**/target/surefire-reports/TEST-org.example.ehali.selenium.KullaniciKayitTest.xml'
                }
            }
        }

        stage('6.2- Selenium: Sistem Hazırlık') {
            steps {
                bat 'mvn test -Dtest=SistemHazirlikTest'
            }
            post {
                always {
                    junit '**/target/surefire-reports/TEST-org.example.ehali.selenium.SistemHazirlikTest.xml'
                }
            }
        }

        stage('6.3- Selenium: Sayfa Gezinti') {
            steps {
                bat 'mvn test -Dtest=SayfaGezintiTest'
            }
            post {
                always {
                    junit '**/target/surefire-reports/TEST-org.example.ehali.selenium.SayfaGezintiTest.xml'
                }
            }
        }

        stage('6.4- Selenium: İpek Koleksiyonu') {
            steps {
                bat 'mvn test -Dtest=IpekKoleksiyonuDogrulama'
            }
            post {
                always {
                    junit '**/target/surefire-reports/TEST-org.example.ehali.selenium.IpekKoleksiyonuDogrulama.xml'
                }
            }
        }

        stage('6.5- Selenium: Navigasyon Kontrolü') {
            steps {
                bat 'mvn test -Dtest=KullaniciNavigasyonTest'
            }
            post {
                always {
                    junit '**/target/surefire-reports/TEST-org.example.ehali.selenium.KullaniciNavigasyonTest.xml'
                }
            }
        }

        stage('6.6- Selenium: Kayıt Navigasyon') {
            steps {
                bat 'mvn test -Dtest=KayitNavigasyonTest'
            }
            post {
                always {
                    junit '**/target/surefire-reports/TEST-org.example.ehali.selenium.KayitNavigasyonTest.xml'
                }
            }
        }

        stage('6.7- Selenium: Kategori Filtreleme') {
            steps {
                bat 'mvn test -Dtest=KategoriFiltrelemeTest'
            }
            post {
                always {
                    junit '**/target/surefire-reports/TEST-org.example.ehali.selenium.KategoriFiltrelemeTest.xml'
                }
            }
        }

        stage('6.8- Selenium: Kategori Veri Bütünlüğü') {
            steps {
                bat 'mvn test -Dtest=KategoriVeriTest'
            }
            post {
                always {
                    junit '**/target/surefire-reports/TEST-org.example.ehali.selenium.KategoriVeriTest.xml'
                }
            }
        }

        stage('6.9- Selenium: Giriş Hata Mesajı') {
            steps {
                bat 'mvn test -Dtest=GirisHataTest'
            }
            post {
                always {
                    junit '**/target/surefire-reports/TEST-org.example.ehali.selenium.GirisHataTest.xml'
                }
            }
        }

        stage('6.10- Selenium: Anasayfa Logo') {
            steps {
                bat 'mvn test -Dtest=AnasayfaLogoTest'
            }
            post {
                always {
                    junit '**/target/surefire-reports/TEST-org.example.ehali.selenium.AnasayfaLogoTest.xml'
                }
            }
        }
    }
}