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