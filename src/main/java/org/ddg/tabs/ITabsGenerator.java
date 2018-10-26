package org.ddg.tabs;

import javafx.scene.control.Tab;

import java.util.List;

public interface ITabsGenerator {
    Tab getResultTab();
    Tab getSourceCodeTab();
    Tab getCSSTab();
    Tab getJavaDocTab();
    List<Tab> getAllTabs();
}
