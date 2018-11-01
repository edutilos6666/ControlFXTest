package org.ddg.tabs;

import javafx.scene.control.Tab;
import org.ddg.tabs.content.LineChartResultTabContent;
import org.ddg.tabs.content.PieChartResultTabContent;
import org.ddg.utils.JavafxUtils;

import java.util.Arrays;
import java.util.List;

import static org.ddg.tabs.TabNamesEnum.RESULT;

public class PieChartTabs implements ITabsGenerator{
    private Tab tabResult, tabSourceCode, tabCss, tabJavaDoc;

    public PieChartTabs() {
        getResultTab();
        getSourceCodeTab();
        getCSSTab();
        getJavaDocTab();
    }

    @Override
    public Tab getResultTab() {
        tabResult = new Tab(RESULT);
        tabResult.setContent(new PieChartResultTabContent().buildContent());
        return tabResult;
    }

    @Override
    public Tab getSourceCodeTab() {
        String fullPath = System.getProperty("user.dir") +"/src/main/java/org/ddg/tabs/content/PieChartResultTabContent.java";
        tabSourceCode = JavafxUtils.generateSourceCodeTab(fullPath);
        return tabSourceCode;
    }

    @Override
    public Tab getCSSTab() {
//        String fullPath = System.getProperty("user.dir") +"/src/main/resources/org/ddg/tabs/content/gridPaneResultTabContent.css";
        tabCss = JavafxUtils.generateCssTab(null);
        return tabCss;
    }

    @Override
    public Tab getJavaDocTab() {
        tabJavaDoc = JavafxUtils.generateJavaDocTab("https://docs.oracle.com/javase/8/javafx/api/javafx/scene/chart/PieChart.html");
        return tabJavaDoc;
    }

    @Override
    public List<Tab> getAllTabs() {
        return Arrays.asList(tabResult, tabSourceCode, tabCss, tabJavaDoc);
    }
}
