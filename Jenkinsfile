pipeline {
    agent { docker { image 'maven:3.0.0' } }
    stages {
        stage('build') {
            steps {
                sh 'mvn --version'
                sh 'echo "abolfazl"'
                sh 'mvn compile'
            }
        }
    }
}
