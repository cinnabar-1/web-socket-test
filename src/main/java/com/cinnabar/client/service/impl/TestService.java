package com.cinnabar.client.service.impl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cinnabar-1
 * @version 1.0.0
 * @ClassName TestService.java
 * @Description
 * @createTime 2021-08-31  14:10:00
 */
public class TestService {

    static Map<String, Space> spaceMap = new HashMap<>(32);

    static void traverse() {
        Space space = new Space();
        space.id = "mockId1";
        space.name = "mockName1";
        Space space2 = new Space();
        space2.id = "mockId2";
        space2.name = "mockName2";
        Project project = new Project();
        project.id = "mockId11";
        project.name = "mockName11";
        Project project1 = new Project();
        project1.id = "mockId12";
        project1.name = "mockName12";
        Map<String, Project> projectMap = new HashMap<>();
        projectMap.put(project.id, project);
        projectMap.put(project1.id, project1);
        space.projectMap = projectMap;
        spaceMap.put(space.id, space);

        spaceMap.values().removeIf(space1 -> {
            if (!space.name.equals("mockName2"))
                space1.projectMap.values().removeIf(projectItem ->
                        projectItem.name.equals("mockName12"));
            return space.name.equals("mockName2");
        });
    }

    public static void main(String[] args) {
//        traverse();
        String string = "张一凡Test";
        System.out.println(string);
    }


    static class Space {
        String id;
        String name;
        Map<String, Project> projectMap;
    }

    static class Project {
        String id;
        String name;
    }
}
