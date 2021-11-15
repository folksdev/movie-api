pipeline {
    agent {
        docker {
            image 'maven:3.8.1-adoptopenjdk-11'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Building our image') {
                    steps {
                        script {
                            dockerImage = docker.build "cagridursun/movie-api:$BUILD_NUMBER"
                        }
                    }
                }
                stage('Deploy our image') {
                    steps {
                        script {
                            // Assume the Docker Hub registry by passing an empty string as the first parameter
                            docker.withRegistry('' , 'dockerhub') {
                                dockerImage.push()
                            }
                        }
                    }
                }
    }
}