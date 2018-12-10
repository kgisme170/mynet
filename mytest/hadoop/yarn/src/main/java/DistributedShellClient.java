import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.yarn.api.ApplicationConstants;
import org.apache.hadoop.yarn.api.records.*;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.client.api.YarnClientApplication;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.exceptions.YarnException;
import org.apache.hadoop.yarn.util.Apps;
import org.apache.hadoop.yarn.util.ConverterUtils;
import org.apache.hadoop.yarn.util.Records;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DistributedShellClient {
    private Configuration conf = new YarnConfiguration();

    public void run(String[] args) throws YarnException, IOException, InterruptedException {
        YarnConfiguration yarnConfiguration = new YarnConfiguration();
        YarnClient yarnClient = YarnClient.createYarnClient();
        //基于传入的配置信息，YarnClient对象获得了RM的信息，并在初始化中创建了一个RM的代理对象。所有于RM的通信是通过这个RM代理进行的
        yarnClient.init(yarnConfiguration);
        //客户端开始执行
        yarnClient.start();
        //yarnClient.

        YarnClientApplication yarnClientApplication = yarnClient.createApplication();

        //container launch context for application master
        ContainerLaunchContext applicationMasterContainer = Records.newRecord(ContainerLaunchContext.class);
        //设定启动容器时需要的命令行。这个例子中，用于启动AM的命令行在DistributedShellApplicationMaster类中。
        applicationMasterContainer.setCommands(
                Collections.singletonList("$JAVA_HOME/bin/java MasteringYarn.DistributedShellApplicationMaster " +
                        args[2] +
                        " " +
                        args[3] +
                        " " +
                        "1>" +
                        ApplicationConstants.LOG_DIR_EXPANSION_VAR + "/stdout " +
                        "2>" +
                        ApplicationConstants.LOG_DIR_EXPANSION_VAR + "/stderr")
        );

        LocalResource applicationMasterJar = Records.newRecord(LocalResource.class);
        setupJarFileForApplicationMaster(new Path(args[1]), applicationMasterJar);
        //指定一个包含AM运行逻辑的JAR文件。这个例子中，获取JAR文件的HDFS路径将设置为本地资源
        applicationMasterContainer.setLocalResources(
                Collections.singletonMap("MasteringYarn.jar", applicationMasterJar)
        );

        Map<String, String> appMasterEnv = new HashMap<String, String>();
        setupEnvironmentForApplicationMaster(appMasterEnv);
        applicationMasterContainer.setEnvironment(appMasterEnv);

        Resource resources = Records.newRecord(Resource.class);
        resources.setVirtualCores(1);
        resources.setMemory(100);

        ApplicationSubmissionContext submissionContext = yarnClientApplication.getApplicationSubmissionContext();
        submissionContext.setAMContainerSpec(applicationMasterContainer);
        submissionContext.setQueue("default");
        submissionContext.setApplicationName("MasteringYarn");
        submissionContext.setResource(resources);

        ApplicationId applicationId = submissionContext.getApplicationId();
        System.out.println("Submitting " + applicationId);
        yarnClient.submitApplication(submissionContext);
        System.out.println("Post submission " + applicationId);

        ApplicationReport applicationReport;
        YarnApplicationState applicationState;

        do {
            Thread.sleep(1000);
            applicationReport = yarnClient.getApplicationReport(applicationId);
            applicationState = applicationReport.getYarnApplicationState();

            System.out.println("Diagnostics " + applicationReport.getDiagnostics());

        } while (applicationState != YarnApplicationState.FAILED &&
                applicationState != YarnApplicationState.FINISHED &&
                applicationState != YarnApplicationState.KILLED);

        System.out.println("Application finished with " + applicationState + " state and id " + applicationId);
    }

    private void setupJarFileForApplicationMaster(Path jarPath, LocalResource localResource) throws IOException {
        FileStatus jarStat = FileSystem.get(conf).getFileStatus(jarPath);
        localResource.setResource(ConverterUtils.getYarnUrlFromPath(jarPath));
        localResource.setSize(jarStat.getLen());
        localResource.setTimestamp(jarStat.getModificationTime());
        localResource.setType(LocalResourceType.FILE);
        localResource.setVisibility(LocalResourceVisibility.PUBLIC);
    }

    private void setupEnvironmentForApplicationMaster(Map<String, String> environmentMap) {
        for (String c : conf.getStrings(
                YarnConfiguration.YARN_APPLICATION_CLASSPATH,
                YarnConfiguration.DEFAULT_YARN_APPLICATION_CLASSPATH)) {
            Apps.addToEnvironment(environmentMap, ApplicationConstants.Environment.CLASSPATH.name(),
                    c.trim());
        }
        Apps.addToEnvironment(environmentMap,
                ApplicationConstants.Environment.CLASSPATH.name(),
                ApplicationConstants.Environment.PWD.$() + File.separator + "*");
    }

    public static void main(String[] args) throws Exception {
        DistributedShellClient shellClient = new DistributedShellClient();
        shellClient.run(args);
    }
}