///////////////////////////////////////////////////////////////////////////////////////////////////
// ImageMagick Gradle Plugin is a plugin to call ImageMagick CLI.
// Copyright (C) 2024 Dmitry Shapovalov.
//
// This file is part of ImageMagick Gradle Plugin.
//
// ImageMagick Gradle Plugin is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// ImageMagick Gradle Plugin is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.
///////////////////////////////////////////////////////////////////////////////////////////////////
package ru.d_shap.gradle.plugin.imagemagick.extension;

import java.io.File;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.file.ConfigurableFileTree;
import org.gradle.api.file.FileTree;
import org.gradle.api.tasks.util.PatternSet;

import groovy.lang.Closure;

/**
 * The pipeline configuration.
 *
 * @author Dmitry Shapovalov
 */
public class PipelineConfiguration {

    private final Project _project;

    private final String _name;

    private File _sourceBaseDir;

    private FileTree _sourceFiles;

    private File _destinationDir;

    private final ParameterConfiguration _parameterConfiguration;

    /**
     * Create new object.
     *
     * @param project the project.
     * @param name    the name.
     */
    public PipelineConfiguration(final Project project, final String name) {
        super();
        _project = project;
        _name = name;
        _sourceBaseDir = null;
        _sourceFiles = null;
        _destinationDir = null;
        _parameterConfiguration = _project.getObjects().newInstance(ParameterConfiguration.class);
    }

    /**
     * Get the name.
     *
     * @return the name.
     */
    public String getName() {
        return _name;
    }

    /**
     * Get the source base directory.
     *
     * @return the source base directory.
     */
    public File getSourceBaseDir() {
        return _sourceBaseDir;
    }

    /**
     * Get the source files.
     *
     * @return the source files.
     */
    public FileTree getSourceFiles() {
        return _sourceFiles;
    }

    /**
     * Set the source files.
     *
     * @param sourceBaseDir the source base directory.
     * @param patternSet    the pattern set.
     */
    public void src(final String sourceBaseDir, final PatternSet patternSet) {
        ConfigurableFileTree fileTree = _project.fileTree(sourceBaseDir);
        _sourceBaseDir = fileTree.getDir().getAbsoluteFile();
        _sourceFiles = fileTree.matching(patternSet);
    }

    /**
     * Set the source files.
     *
     * @param sourceBaseDir the source base directory.
     * @param closure       the closure.
     */
    public void src(final String sourceBaseDir, final Closure<?> closure) {
        PatternSet patternSet = (PatternSet) _project.configure(new PatternSet(), closure);
        src(sourceBaseDir, patternSet);
    }

    /**
     * Get the destination directory.
     *
     * @return the destination directory.
     */
    public File getDestinationDir() {
        return _destinationDir;
    }

    /**
     * Set the destination directory.
     *
     * @param destinationDir the destination directory.
     */
    public void dst(final String destinationDir) {
        File buildDir = _project.getBuildDir().getAbsoluteFile();
        _destinationDir = new File(buildDir, destinationDir);
    }

    /**
     * Get the parameter configuration.
     *
     * @return the parameter configuration.
     */
    public ParameterConfiguration getParameterConfiguration() {
        return _parameterConfiguration;
    }

    /**
     * Set the parameter configuration.
     *
     * @param action the action.
     */
    public void params(final Action<? super ParameterConfiguration> action) {
        action.execute(_parameterConfiguration);
    }

}
