@Library(['private-pipeline-library', 'jenkins-shared']) _

def branchName = env.BRANCH_NAME ?: gitBranch(env)
def deployBranch = 'main'

mavenSnapshotPipeline(
  javaVersion: 'OpenJDK 11',
  mavenVersion: 'Maven 3.6.x',
  usePublicSettingsXmlFile: false,
  useEventSpy: false,
  mavenOptions: [
    "-Dit",
    "-Dbuild.notes='b:${BRANCH_NAME}, j:${JOB_NAME}, n:#${BUILD_NUMBER}'"
  ].join(' '),
  testResults: [ '**/target/*-reports/*.xml' ],
  iqPolicyEvaluation: { stage ->
    nexusPolicyEvaluation iqStage: stage, iqApplication: 'basicli',
      iqScanPatterns: [[ scanPattern: 'scan_nothing' ]]
  },
  deployCondition: {
    return deployBranch
  }
)
