package com.teinelund.jproject_info.commands;

import com.teinelund.jproject_info.common.Printer;
import com.teinelund.jproject_info.context.Context;
import com.teinelund.jproject_info.context.Project;
import com.teinelund.jproject_info.context.ProjectFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.NameFileFilter;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

class ProjectInformationCommandImpl extends AbstractCommand implements ProjectInformationCommand {

    @Inject
    public ProjectInformationCommandImpl(Context context, Printer printer) {
        super(context, printer);
    }

    @Override
    public void execute() {
        this.printer.verbose("ProjectInformationCommand.execute()");

        Set<Path> normalizedPaths = normalizePaths(context.getParameters().getJavaProjectPaths());

        verifyNoSubPaths(normalizedPaths);

        Collection<Project> projects = new LinkedList<>();
        for (Path path : normalizedPaths) {
            try {
                fetchProject(path, path, projects);
            }
            catch(IOException e) {
                //DO SOMETHING GOOD !!!
            }
        }
        context.setProjects(projects);
    }

    Set<Path> normalizePaths(Set<Path> paths) {
        this.printer.verbose("Input paths in set: " + paths);
        Set<Path> normalizedPaths = new LinkedHashSet<>();
        for (Path path : paths) {
            Path normalizedPath = path.toAbsolutePath().normalize();
            normalizedPaths.add(normalizedPath);
        }
        this.printer.verbose("Normalized paths in set: " + normalizedPaths.toString());
        return normalizedPaths;
    }

    /**
     * All paths must be unique and must not be a sub path of another path.
     * @param normalizedPaths is a set of Path objects, where paths should be absolut and normalized.
     * @throws SubPathException if sub path is found.
     */
    void verifyNoSubPaths(Set<Path> normalizedPaths) {
        if (normalizedPaths.size() < 2) {
            return;
        }
        Path[] paths = normalizedPaths.toArray(Path[]::new);
        for (int i = 0; i < paths.length; i++) {
            Path p1 = paths[i];
            for (int j = 0; j < paths.length; j++) {
                if (i == j) {
                    continue;
                }
                Path p2 = paths[j];
                if (p2.toString().startsWith(p1.toString())) {
                    throw new SubPathException("'" + p2.toString() + "' is a sub path of '" + p1.toString() + "'. Path must be unique and without sub paths.");
                }
            }
        }
    }


    void fetchProject(Path rootPath, Path path, Collection<Project> projects) throws IOException {
        if (isMavenProject(path)) {
            projects.add(ProjectFactory.createMavenProject(rootPath, path));
        }
        else if (isJavaSourceProject(path)) {
            projects.add(ProjectFactory.createJavaSourceProject(rootPath, path));
        }
        else {
            File[] files = path.toFile().listFiles();
            for (File file : files) {
                if (Files.isDirectory(file.toPath())) {
                    fetchProject(rootPath, file.toPath(), projects);
                }
            }
        }
    }

    boolean isMavenProject(Path path) throws IOException {
        Collection<File> files = FileUtils.listFiles(path.toFile(), new NameFileFilter("pom.xml"), null);
        return !files.isEmpty();
    }

    boolean isJavaSourceProject(Path path) {
        String[] filteExtenstionToLookFoor = {"java"};
        Collection<File> files = FileUtils.listFiles(path.toFile(), filteExtenstionToLookFoor, false);
        return !files.isEmpty();
    }
}
