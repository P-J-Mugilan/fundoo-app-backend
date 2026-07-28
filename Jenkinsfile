pipeline {
    agent any

    tools {
        maven 'Maven 3' // Assumes Maven is configured in Jenkins Global Tool Configuration
        jdk 'JDK 21'    // Assumes JDK 21 is configured in Jenkins Global Tool Configuration
    }

    environment {
        DOCKER_IMAGE_NAME = "mugilanjagadeesan/fundoo-app"
        DOCKER_CREDENTIALS_ID = "dockerhub-credentials" // Assumes Jenkins credentials ID configured with Docker Hub username/password
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    sh "docker build -t ${DOCKER_IMAGE_NAME}:latest -t ${DOCKER_IMAGE_NAME}:${BUILD_NUMBER} ."
                }
            }
        }

        stage('Docker Push') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS_ID}", usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh "echo \$DOCKER_PASSWORD | docker login -u \$DOCKER_USER --password-stdin"
                        sh "docker push ${DOCKER_IMAGE_NAME}:latest"
                        sh "docker push ${DOCKER_IMAGE_NAME}:${BUILD_NUMBER}"
                    }
                }
            }
        }
    }

    post {
        always {
            // Clean up workspace
            cleanWs()
        }
        success {
            echo "CI/CD Pipeline finished successfully!"
        }
        failure {
            echo "Pipeline failed! Please check logs."
        }
    }
}
