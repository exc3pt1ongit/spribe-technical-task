package spribe.listeners;

import lombok.extern.log4j.Log4j2;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.xml.XmlSuite;
import spribe.config.EnvironmentConfig;
import spribe.helpers.ValidationHelper;

import java.util.Collections;
import java.util.stream.Collectors;

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
        String includedGroups = EnvironmentConfig.ENV_INCLUDED_GROUPS.getValue().trim();
        String excludedGroups = EnvironmentConfig.ENV_EXCLUDED_GROUPS.getValue().trim();

        xmlSuite.setName("Spribe Test Suite");
        xmlSuite.addListener(ResponsiveSuiteListener.class.getName());
        xmlSuite.addListener(ResponsiveExecutionListener.class.getName());
        xmlSuite.addListener(ResponsiveMethodInterceptor.class.getName());
        xmlSuite.setParallel(parallelMode);
        xmlSuite.setThreadCount(threadCount);

        if (!includedGroups.isEmpty() && ValidationHelper.isNotNullAndNotBlank(includedGroups)) {
            xmlSuite.setIncludedGroups(Collections.singletonList(includedGroups));
        }

        if (!excludedGroups.isEmpty() && ValidationHelper.isNotNullAndNotBlank(includedGroups)) {
            xmlSuite.setExcludedGroups(Collections.singletonList(excludedGroups));
        }

        log.info("Dynamic configuration applied");
        log.info("Test suite: {}", xmlSuite.getName());
        log.info("Parallel mode: {}", xmlSuite.getParallel());
        log.info("Thread count: {}", xmlSuite.getThreadCount());
        log.info("Included groups: {}", xmlSuite.getIncludedGroups());
        log.info("Excluded groups: {}", xmlSuite.getExcludedGroups());
        log.info("Connected Listeners: {}", xmlSuite.getListeners().stream()
                .map(String::valueOf).collect(Collectors.joining(",")));
    }
}