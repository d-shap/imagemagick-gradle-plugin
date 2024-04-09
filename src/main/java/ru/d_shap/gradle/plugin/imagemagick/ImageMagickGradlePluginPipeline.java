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
package ru.d_shap.gradle.plugin.imagemagick;

import org.gradle.api.Project;
import org.gradle.api.file.FileTree;
import org.gradle.api.tasks.util.PatternSet;

import groovy.lang.Closure;

/**
 * ImageMagick gradle plugin pipeline.
 *
 * @author Dmitry Shapovalov
 */
public class ImageMagickGradlePluginPipeline {

    private final Project _project;

    private final String _name;

    private FileTree _source;

    /**
     * Create new object.
     *
     * @param name    the name.
     * @param project the project.
     */
    public ImageMagickGradlePluginPipeline(final String name, final Project project) {
        super();
        _project = project;
        _name = name;
        _source = null;
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
     * Get the source files.
     *
     * @return the source files.
     */
    public FileTree getSource() {
        return _source;
    }

    /**
     * Set the source files.
     *
     * @param baseDir    the base dir.
     * @param patternSet the pattern set.
     */
    public void src(final String baseDir, final PatternSet patternSet) {
        _source = _project.fileTree(baseDir).matching(patternSet);
    }

    /**
     * Set the source files.
     *
     * @param baseDir the base dir.
     * @param closure the closure.
     */
    public void src(final String baseDir, final Closure<?> closure) {
        PatternSet patternSet = (PatternSet) _project.configure(new PatternSet(), closure);
        src(baseDir, patternSet);
    }

}
