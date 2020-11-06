package com.teinelund.jproject_info.common;

import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

class ContextImpl implements Context {

    private Set<Path> projectPaths = new LinkedHashSet<>();

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
}
