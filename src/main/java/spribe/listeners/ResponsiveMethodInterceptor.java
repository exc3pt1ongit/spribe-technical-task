package spribe.listeners;

import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static spribe.config.EnvironmentConfig.ENV_EXCLUDED_GROUPS;
import static spribe.config.EnvironmentConfig.ENV_INCLUDED_GROUPS;

public class ResponsiveMethodInterceptor implements IMethodInterceptor {

    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        String includedGroups = ENV_INCLUDED_GROUPS.getValue();
        String excludedGroups = ENV_EXCLUDED_GROUPS.getValue();
        List<IMethodInstance> filteredMethods = new ArrayList<>();
        for (IMethodInstance methodInstance : methods) {
            boolean methodIsNotExcluded = !Arrays.stream(methodInstance.getMethod().getGroups())
                    .collect(Collectors.toList())
                    .contains(excludedGroups);
            boolean methodIsIncluded = Arrays.stream(methodInstance.getMethod().getGroups())
                    .collect(Collectors.toList())
                    .contains(includedGroups);
            if (methodIsNotExcluded && methodIsIncluded) {
                filteredMethods.add(methodInstance);
            }
        }
        return filteredMethods;
    }
}
