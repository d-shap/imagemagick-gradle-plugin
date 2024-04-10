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
package ru.d_shap.gradle.plugin.imagemagick.configuration;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.Action;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Project;

/**
 * The extension configuration.
 *
 * @author Dmitry Shapovalov
 */
public class ExtensionConfiguration {

    private final NamedDomainObjectContainer<PipelineConfiguration> _pipelineContainer;

    private final List<PipelineConfiguration> _pipelineConfigurations;

    /**
     * Create new object.
     *
     * @param project the project.
     */
    public ExtensionConfiguration(final Project project) {
        super();
        _pipelineContainer = project.container(PipelineConfiguration.class);
        _pipelineConfigurations = new ArrayList<>();
    }

    /**
     * Get the pipeline configurations.
     *
     * @return the pipeline configurations.
     */
    public List<PipelineConfiguration> getPipelineConfigurations() {
        return _pipelineConfigurations;
    }

    /**
     * Set the pipeline configurations.
     *
     * @param action the action.
     */
    public void pipelines(final Action<? super NamedDomainObjectContainer<PipelineConfiguration>> action) {
        action.execute(_pipelineContainer);
        _pipelineConfigurations.clear();
        _pipelineConfigurations.addAll(_pipelineContainer);
    }

}
