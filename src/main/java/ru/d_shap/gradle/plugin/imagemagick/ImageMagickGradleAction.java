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
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.gradle.api.Action;
import org.gradle.api.Task;
import org.gradle.api.file.FileTree;

import ru.d_shap.gradle.plugin.imagemagick.configuration.ExtensionConfiguration;
import ru.d_shap.gradle.plugin.imagemagick.configuration.ParametersConfiguration;
import ru.d_shap.gradle.plugin.imagemagick.configuration.PipelineConfiguration;
import ru.d_shap.gradle.plugin.imagemagick.parameters.Context;
import ru.d_shap.gradle.plugin.imagemagick.parameters.Parameter;

/**
 * ImageMagick gradle action.
 *
 * @author Dmitry Shapovalov
 */
public class ImageMagickGradleAction implements Action<Task> {

    private static final String COMMAND = "magick";

    private static final String DEFAULT_FILE_NAME = "fileName.ext";

    private final ExtensionConfiguration _extensionConfiguration;

    /**
     * Create new object.
     *
     * @param extensionConfiguration ImageMagick gradle plugin extension.
     */
    public ImageMagickGradleAction(final ExtensionConfiguration extensionConfiguration) {
        super();
        _extensionConfiguration = extensionConfiguration;
    }

    @Override
    public void execute(final Task task) {
        if (Logger.isWarnEnabled()) {
            Logger.warn("Start processing images with ImageMagick");
        }
        List<PipelineConfiguration> pipelineConfigurations = _extensionConfiguration.getPipelineConfigurations();
        for (PipelineConfiguration pipelineConfiguration : pipelineConfigurations) {
            ParametersConfiguration parametersConfiguration = pipelineConfiguration.getParameterConfiguration();
            File sourceBaseDir = pipelineConfiguration.getSourceBaseDir();
            FileTree sourceFiles = pipelineConfiguration.getSourceFiles();
            File destinationDir = pipelineConfiguration.getDestinationDir();
            if (sourceFiles == null) {
                processFile(parametersConfiguration, null, null, destinationDir);
            } else {
                for (File sourceFile : sourceFiles) {
                    processFile(parametersConfiguration, sourceBaseDir, sourceFile, destinationDir);
                }
            }
        }
        if (Logger.isWarnEnabled()) {
            Logger.warn("Finish processing images with ImageMagick");
        }
    }

    private void processFile(final ParametersConfiguration parametersConfiguration, final File sourceBaseDir, final File sourceFile, final File destinationDir) {
        Path sourceFilePath = getSourceFilePath(sourceFile);
        Path destinationFilePath = getDestinationFilePath(sourceBaseDir, sourceFile, destinationDir);
        ensureDestinationExists(destinationFilePath);
        Context context = new Context(sourceFilePath, destinationFilePath);

        List<Parameter> parameters = parametersConfiguration.getParameters();
        List<String> strs = new ArrayList<>();
        for (Parameter parameter : parameters) {
            String str = parameter.invoke(context);
            if (str != null) {
                strs.add(str);
            }
        }

        StringBuilder command = new StringBuilder();
        command.append(COMMAND);
        for (String str : strs) {
            command.append(' ').append(str);
        }

        if (Logger.isInfoEnabled()) {
            Logger.info(command.toString());
        }
    }

    private Path getSourceFilePath(final File sourceFile) {
        return sourceFile.toPath();
    }

    private Path getDestinationFilePath(final File sourceBaseDir, final File sourceFile, final File destinationDir) {
        Path destinationFilePath = destinationDir.toPath();
        if (sourceBaseDir == null || sourceFile == null) {
            return destinationFilePath.resolve(DEFAULT_FILE_NAME);
        } else {
            Path sourceBasePath = sourceBaseDir.toPath();
            Path sourceFilePath = sourceFile.toPath();
            Path sourceRelativePath = sourceBasePath.relativize(sourceFilePath);
            return destinationFilePath.resolve(sourceRelativePath);
        }
    }

    private void ensureDestinationExists(final Path destinationFilePath) {
        Path parentPath = destinationFilePath.getParent();
        if (parentPath != null) {
            File file = parentPath.toFile();
            file.mkdirs();
        }
    }

}
