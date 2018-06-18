pipeline {
    agent { docker { image 'maven:3.3.9' } }
    stages {
        stage('build') {
            steps {
                sh 'mvn compile'
                sh 'echo "abolfazl"'
            }
        }
    }
}
