package com.teinelund.jproject_info.context;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;

class ContextImpl implements Context {

    private Set<Path> projectPaths = new LinkedHashSet<>();
    private Collection<Project> projects = new LinkedList<>();
    private Collection<Path> unknownJavaProjectPaths = new LinkedList<>();

    @Override
    public void setProjectPaths(Set<Path> projectPaths) {
        if (Objects.nonNull(projectPaths)) {
            this.projectPaths = projectPaths;
        }
    }

    @Override
    public Set<Path> getProjectPaths() {
        return this.projectPaths;
    }

    @Override
    public void setProjects(Collection<Project> projects) {
        this.projects.addAll(projects);
    }

    @Override
    public Collection<Project> getProjects() {
        return Collections.unmodifiableCollection(this.projects);
    }

    @Override
    public void addUnknownJavaProject(Path path) {
        unknownJavaProjectPaths.add(path);
    }

    @Override
    public Collection<Path> getUnknownJavaProjectPaths() {
        return Collections.unmodifiableCollection(this.unknownJavaProjectPaths);
    }
}