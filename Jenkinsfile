pipeline {
        agent docker: 'maven:3.3.9-alpine'

        jobProperties {
            buildDiscarder(logRotator(numToKeepStr:'10'))
        }

        stages {
            stage('maven-build') {
                steps {
                    withMaven(mavenSettingsConfig: 'vantage') {
                        sh 'mvn clean install'
                    }
                }
            }
        }
}
