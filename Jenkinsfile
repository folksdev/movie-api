pipeline {
    stage('Clone repository') {
        checkout scm
    }

    stage('Build') {
        sh 'mvn clean install -DskipTests'
    }

    stage('Test image') {
        sh 'mvn clean test'
    }

    stage('Push image') {
        sh 'mvn spring-boot:run'
    }
}