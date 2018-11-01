package org.ddg.taskExamples;


import lombok.Data;

@Data
public class SimpleWorker {
    private String name;
    private int age;

    public SimpleWorker(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public SimpleWorker() {
    }
}
