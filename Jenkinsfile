library('private-pipeline-library')
library('jenkins-shared')

mavenSnapshotPipeline(
  javaVersion: 'OpenJDK 11',
  useMvnw: true,
  usePublicSettingsXmlFile: true,
  useEventSpy: false,
  deployBranch: 'main',
  mavenOptions: [
    "-Dit",
    "-Dbuild.notes='b:${BRANCH_NAME}, j:${JOB_NAME}, n:#${BUILD_NUMBER}'"
  ].join(' '),
  testResults: [ '**/target/*-reports/*.xml' ],
  iqPolicyEvaluation: { stage ->
    nexusPolicyEvaluation iqStage: stage, iqApplication: 'basicli',
      iqScanPatterns: [
        [ scanPattern: '**/target/module.xml' ]
      ]
  }
)
