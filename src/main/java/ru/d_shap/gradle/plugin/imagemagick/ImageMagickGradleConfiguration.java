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

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.TaskInputs;
import org.gradle.api.tasks.TaskOutputs;

import ru.d_shap.gradle.plugin.imagemagick.configuration.ExtensionConfiguration;
import ru.d_shap.gradle.plugin.imagemagick.configuration.PipelineConfiguration;

/**
 * ImageMagick gradle configuration.
 *
 * @author Dmitry Shapovalov
 */
final class ImageMagickGradleConfiguration implements Action<Project> {

    private final Task _task;

    private final ExtensionConfiguration _extensionConfiguration;

    ImageMagickGradleConfiguration(final Task task, final ExtensionConfiguration extensionConfiguration) {
        super();
        _task = task;
        _extensionConfiguration = extensionConfiguration;
    }

    @Override
    public void execute(final Project project) {
        addInputs();
        addOutputs();
    }

    private void addInputs() {
        TaskInputs taskInputs = _task.getInputs();
        Set<String> uniquePaths = new HashSet<>();
        List<PipelineConfiguration> pipelineConfigurations = _extensionConfiguration.getPipelineConfigurations();
        for (PipelineConfiguration pipelineConfiguration : pipelineConfigurations) {
            File file = pipelineConfiguration.getSourceBaseDir();
            if (file == null) {
                continue;
            }
            String path = file.getAbsolutePath();
            if (uniquePaths.contains(path)) {
                continue;
            }
            uniquePaths.add(path);
            taskInputs.dir(file);
            if (Logger.isDebugEnabled()) {
                Logger.debug("Input \"" + path + "\" is added");
            }
        }
    }

    private void addOutputs() {
        TaskOutputs taskOutputs = _task.getOutputs();
        Set<String> uniquePaths = new HashSet<>();
        List<PipelineConfiguration> pipelineConfigurations = _extensionConfiguration.getPipelineConfigurations();
        for (PipelineConfiguration pipelineConfiguration : pipelineConfigurations) {
            File file = pipelineConfiguration.getDestinationDir();
            if (file == null) {
                continue;
            }
            String path = file.getAbsolutePath();
            if (uniquePaths.contains(path)) {
                continue;
            }
            uniquePaths.add(path);
            taskOutputs.dir(file);
            if (Logger.isDebugEnabled()) {
                Logger.debug("Output \"" + path + "\" is added");
            }
        }
    }

}
