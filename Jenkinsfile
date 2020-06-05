#!/usr/bin/env groovy

pipeline {
  agent any
  options {
    buildDiscarder(logRotator(numToKeepStr:'20'))
  }
  stages {
    stage('Info') {
      steps {
        sh "pwd"
      }
    }
    stage('Build') {
      steps {
        sh "make build-deployment-images"
      }
    }
    stage('Push') {
      steps {
        sh "make push-deployment-images"
      }
    }
    stage('Deploy') {
      steps {
        sh "docker stack rm holidays"
        sh "make deploy"
      }
    }
  }
}
