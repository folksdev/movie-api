pipeline {

    agent {
        docker {
            image 'maven:3.3.3'
        }
    }

    stages {
        stage('Clone repository') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Test image') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('Push image') {
            steps {
                sh 'mvn spring-boot:run'
            }
        }
    }
}