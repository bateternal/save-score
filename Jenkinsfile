pipeline {
    agent { docker { image 'maven:3.3.3' } }
    stages {
        stage('build') {
            steps {
                sh 'mvn dependency:resolve'
                sh 'echo "abolfazl"'
            }
        }
    }
}
