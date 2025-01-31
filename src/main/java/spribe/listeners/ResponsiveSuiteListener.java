package spribe.listeners;

import lombok.extern.log4j.Log4j2;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.xml.XmlSuite;
import spribe.config.EnvironmentConfig;

@Log4j2
public class ResponsiveSuiteListener implements ISuiteListener {

    @Override
    public void onStart(ISuite suite) {
        configureTestNGExecutionProperties(suite);
    }

    @Override
    public void onFinish(ISuite suite) {
        log.info("Test execution finished for suite: {}", suite.getName());
    }

    private void configureTestNGExecutionProperties(ISuite suite) {
        XmlSuite xmlSuite = suite.getXmlSuite();

        XmlSuite.ParallelMode parallelMode = XmlSuite.ParallelMode
                .getValidParallel(EnvironmentConfig.ENV_PARALLEL.getValue());

        int threadCount = Integer.parseInt(EnvironmentConfig.ENV_THREAD_COUNT.getValue());
        String includedGroups = EnvironmentConfig.ENV_INCLUDE_GROUPS.getValue();
        String excludedGroups = EnvironmentConfig.ENV_EXCLUDE_GROUPS.getValue();

        xmlSuite.setParallel(parallelMode);
        xmlSuite.setThreadCount(threadCount);
        xmlSuite.addIncludedGroup(includedGroups);
        xmlSuite.addExcludedGroup(excludedGroups);

        log.info("Dynamic configuration applied");
        log.info("Parallel mode: {}", xmlSuite.getParallel());
        log.info("Thread count: {}", xmlSuite.getThreadCount());
        log.info("Included groups: {}", xmlSuite.getIncludedGroups());
        log.info("Excluded groups: {}", xmlSuite.getExcludedGroups());
    }
}