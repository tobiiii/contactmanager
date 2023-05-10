pipeline {
  agent any

  stages {
    stage('Build') {
      steps {
        sh 'docker build -t my-app .'
      }
    }

    stage('Deploy') {
      steps {
        sh 'docker run -p 8080:8080 -d my-app'
      }
    }
  }
}
