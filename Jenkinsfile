pipeline {
    agent any

    environment {
        MAVEN_OPTS = '-Dmaven.test.failure.ignore=true'
        SONAR_TOKEN = credentials('SONAR_TOKEN_ID')
    }

    tools {
        jdk 'Java_JDK'
        maven 'Maven-Home'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'master', url: 'https://github.com/davidpaul551/TestingRestAPI.git'
            }
        }

        stage('Build & Install') {
            steps {
                bat 'mvn clean install'
            }
        }

        stage('Verify, Generate JaCoCo Report') {
            steps {
                bat 'mvn verify jacoco:report'
            }
        }

        stage('Generate Surefire & Failsafe HTML Reports') {
            steps {
                bat 'mvn surefire-report:report surefire-report:failsafe-report-only'
            }
        }

        stage('Generate Allure Report') {
            steps {
                bat 'allure generate target/allure-results --clean -o target/allure-report'
            }
        }

        stage('SonarCloud Analysis') {
            steps {
                withSonarQubeEnv('MySonarServer') {
                    bat """
                        mvn sonar:sonar ^
                          -Dsonar.projectKey=testing551_junit ^
                          -Dsonar.organization=testing551 ^
                          -Dsonar.host.url=https://sonarcloud.io ^
                          -Dsonar.login=%SONAR_TOKEN% ^
                          -Dsonar.coverage.jacoco.reportPaths=target\\site\\jacoco\\jacoco.xml
                    """
                }
                echo ' SonarCloud results here: https://sonarcloud.io/dashboard?id=testing551_junit'
            }
        }

        stage('Publish Reports') {
            steps {
                publishHTML([
                    reportName: 'Surefire Report',
                    reportDir: 'target/reports',
                    reportFiles: 'surefire.html',
                    keepAll: true,
                    allowMissing: true,
                    alwaysLinkToLastBuild: true
                ])

                publishHTML([
                    reportName: 'Failsafe Report',
                    reportDir: 'target/reports',
                    reportFiles: 'failsafe.html',
                    keepAll: true,
                    allowMissing: true,
                    alwaysLinkToLastBuild: true
                ])

                publishHTML([
                    reportName: 'JaCoCo Coverage',
                    reportDir: 'target/site/jacoco',
                    reportFiles: 'index.html',
                    keepAll: true,
                    allowMissing: true,
                    alwaysLinkToLastBuild: true
                ])

                junit 'target/surefire-reports/*.xml'
                junit 'target/failsafe-reports/*.xml'

                archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: true
                allure includeProperties: false, results: [[path: 'target/allure-results']]
            }
        }
    }
}
