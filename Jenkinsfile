node('android') {
    stage('Checkout') {
        checkout scm
    }

    stage('Clean') {
        sh "./gradlew clean"
    }

    stage('Static Analysis') {
        checkLintAndPublishResults()
    }

    stage('Unit Test') {
        runUnitTestAndPublishResults()
    }

    stage('Assemble') {
        generateAndArchiveAPK()
    }
}

def checkLintAndPublishResults() {
    try {
        sh "./gradlew :library:lint"
    } catch(err) {
    }
    String file = "library/build/outputs/lint-results-debug.xml"
    step([$class: 'LintPublisher', pattern: file])
}

def runUnitTestAndPublishResults() {
    failure = false
    try {
        sh "./gradlew :library:testDebugUnitTest"
    } catch(err) {
        failure = true
    } finally {
        String results = "library/build/test-results/testDebugUnitTest/*.xml"
        step([$class: 'JUnitResultArchiver', testResults: results])
    }
    if (failure) {
        error('Unit Test failed')
    }
}

def generateAndArchiveAPK() {
    sh "./gradlew :demo:assembleDebug"
    archive "demo/build/outputs/apk/*.apk"
}
