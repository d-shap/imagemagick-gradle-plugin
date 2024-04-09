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

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.Action;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Project;

/**
 * ImageMagick gradle plugin extension.
 *
 * @author Dmitry Shapovalov
 */
public class ImageMagickGradlePluginExtension {

    private final NamedDomainObjectContainer<ImageMagickGradlePluginPipeline> _container;

    private final List<ImageMagickGradlePluginPipeline> _pipelines;

    /**
     * Create new object.
     *
     * @param project the project.
     */
    public ImageMagickGradlePluginExtension(final Project project) {
        super();
        _container = project.container(ImageMagickGradlePluginPipeline.class);
        _pipelines = new ArrayList<>();
    }

    /**
     * Get the pipelines.
     *
     * @return the pipelines.
     */
    public List<ImageMagickGradlePluginPipeline> getPipelines() {
        return _pipelines;
    }

    /**
     * Set the pipelines with the action.
     *
     * @param action the action.
     */
    public void pipelines(final Action<? super NamedDomainObjectContainer<ImageMagickGradlePluginPipeline>> action) {
        action.execute(_container);
        _pipelines.clear();
        _pipelines.addAll(_container);
    }

}
