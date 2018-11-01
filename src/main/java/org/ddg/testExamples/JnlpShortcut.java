package org.ddg.testExamples;

import javax.jnlp.IntegrationService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;

/**
 * Created by edutilos on 01.11.18.
 */
public class JnlpShortcut {
    public static void main(String[] args) {
        criaAtalhosWebStart();
    }
    public static final String JAVAXJNLP_INTEGRATION_SERVICE = "javax.jnlp.IntegrationService";

    public static void criaAtalhosWebStart() {
        IntegrationService integ;
        try {
            integ = (IntegrationService) ServiceManager.lookup(JAVAXJNLP_INTEGRATION_SERVICE);
            if (integ != null) {
                if (!integ.hasDesktopShortcut() || !integ.hasMenuShortcut()) {
                    integ.requestShortcut(true, true, "Shorcut Label");
                }
            }
        } catch (UnavailableServiceException ex) {
            ex.printStackTrace();
            System.out.println("Error in creating shorcut: " + ex.getMessage());
        }
    }
}
