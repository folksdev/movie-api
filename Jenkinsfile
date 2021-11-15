pipeline {
    environment {
        registry = "cagridursun/movie-api"
        registryCredential = 'dockerhub'
        dockerImage = ''
        dockerHome = tool + 'myDocker'
    }
    agent {
        docker {
            image 'maven:3.8.1-adoptopenjdk-11'
            args '-v $HOME/.m2:/root/.m2'
        }
    }
    stages {
        stage('Cloning our Git') {
            steps {
                git 'https://github.com/folksdev/movie-api.git'
            }
        }
        stage('Initialize'){
            steps {
                env.PATH = dockerHome "/bin:" + $env.PATH
            }
        }
        stage('Building our image') {
            steps {
                script {
                    dockerImage = docker.build registry + ":$BUILD_NUMBER"
                }
            }
        }
        stage('Deploy our image') {
            steps {
                script {
                    docker.withRegistry( '', registryCredential ) {
                        dockerImage.push()
                    }
                }
            }
        }
        stage('Cleaning up') {
            steps {
                sh "docker rmi $registry:$BUILD_NUMBER"
            }
        }
    }
}