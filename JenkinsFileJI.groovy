pipeline {
    agent any
    stages {
        stage('Source') {
            steps {
                git 'https://github.com/Jonathani/unir-cicd'
            }
        }
        stage('Build') {
            steps {
                echo 'Building stage!'
                sh 'make build'
            }
        }
        stage('Unit tests') {
            steps {
                sh 'make test-unit'
                archiveArtifacts artifacts: 'results/*.xml'
            }
        }
    }
    post {
        always {
            junit 'results/*_result.xml'
            cleanWs()
        }
        failure {  
             echo 'Se envia el correo en caso de fallar'             
             echo "${env.JOB_NAME}"
             echo "${env.BUILD_NUMBER}"
        }
    }
}
