pipeline {
    agent { docker { image 'maven:3.5.3' } }
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
