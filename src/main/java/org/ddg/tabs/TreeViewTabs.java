package org.ddg.tabs;

import javafx.scene.control.Tab;
import org.ddg.tabs.content.GridPaneResultTabContent;
import org.ddg.tabs.content.TreeViewResultTabContent;
import org.ddg.utils.JavafxUtils;

import java.util.Arrays;
import java.util.List;

import static org.ddg.tabs.TabNamesEnum.RESULT;

public class TreeViewTabs implements ITabsGenerator{
    @Override
    public Tab getResultTab() {
        Tab tab = new Tab(RESULT);
        tab.setContent(new TreeViewResultTabContent().buildContent());
        return tab;
    }

    @Override
    public Tab getSourceCodeTab() {
        String fullPath = System.getProperty("user.dir") +"/src/main/java/org/ddg/tabs/content/TreeViewResultTabContent.java";
        return JavafxUtils.generateSourceCodeTab(fullPath);
    }

    @Override
    public Tab getCSSTab() {
//        String fullPath = System.getProperty("user.dir") +"/src/main/resources/org/ddg/tabs/content/gridPaneResultTabContent.css";
        return JavafxUtils.generateCssTab(null);
    }

    @Override
    public Tab getJavaDocTab() {
      return JavafxUtils.generateJavaDocTab("https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TreeView.html");
    }

    @Override
    public List<Tab> getAllTabs() {
        return Arrays.asList(getResultTab(), getSourceCodeTab(), getCSSTab(), getJavaDocTab());
    }
}
