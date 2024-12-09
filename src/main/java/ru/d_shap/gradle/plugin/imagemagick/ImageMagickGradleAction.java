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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteStreamHandler;
import org.apache.commons.exec.PumpStreamHandler;
import org.gradle.api.Action;
import org.gradle.api.InvalidUserDataException;
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
final class ImageMagickGradleAction implements Action<Task> {

    private static final String COMMAND = "magick";

    private static final String DEFAULT_FILE_NAME = "fileName.ext";

    private final ExtensionConfiguration _extensionConfiguration;

    ImageMagickGradleAction(final ExtensionConfiguration extensionConfiguration) {
        super();
        _extensionConfiguration = extensionConfiguration;
    }

    @Override
    public void execute(final Task task) {
        if (Logger.isInfoEnabled()) {
            Logger.info("Start processing images with ImageMagick");
        }
        List<PipelineConfiguration> pipelineConfigurations = _extensionConfiguration.getPipelineConfigurations();
        for (PipelineConfiguration pipelineConfiguration : pipelineConfigurations) {
            File sourceBaseDir = pipelineConfiguration.getSourceBaseDir();
            FileTree sourceFiles = pipelineConfiguration.getSourceFiles();
            File destinationDir = pipelineConfiguration.getDestinationDir();
            if (destinationDir == null) {
                throw new InvalidUserDataException("Property dst is udefined");
            }
            ParametersConfiguration parametersConfiguration = pipelineConfiguration.getParameterConfiguration();
            if (sourceFiles == null) {
                processFile(parametersConfiguration, null, null, destinationDir);
            } else {
                for (File sourceFile : sourceFiles) {
                    processFile(parametersConfiguration, sourceBaseDir, sourceFile, destinationDir);
                }
            }
        }
        if (Logger.isInfoEnabled()) {
            Logger.info("Finish processing images with ImageMagick");
        }
    }

    private void processFile(final ParametersConfiguration parametersConfiguration, final File sourceBaseDir, final File sourceFile, final File destinationDir) {
        Path sourceFilePath = getSourceFilePath(sourceFile);
        Path destinationFilePath = getDestinationFilePath(sourceBaseDir, sourceFile, destinationDir);
        ensureDestinationExists(destinationFilePath);

        Context context = new Context(sourceFilePath, destinationFilePath);
        CommandLine commandLine = createCommandLine(context, parametersConfiguration);
        Path processedDestinationFilePath = context.getProcessedDestinationFilePath();
        executeCommandLine(commandLine, sourceFilePath, processedDestinationFilePath);
    }

    private Path getSourceFilePath(final File sourceFile) {
        if (sourceFile == null) {
            return null;
        } else {
            return sourceFile.toPath();
        }
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

    private CommandLine createCommandLine(final Context context, final ParametersConfiguration parametersConfiguration) {
        CommandLine commandLine = new CommandLine(COMMAND);

        List<Parameter> parameters = parametersConfiguration.getParameters();
        for (Parameter parameter : parameters) {
            List<String> arguments = parameter.invoke(context);
            for (String argument : arguments) {
                commandLine.addArgument(argument);
            }
        }

        if (Logger.isDebugEnabled()) {
            StringBuilder builder = new StringBuilder();
            builder.append(commandLine.getExecutable());
            for (String argument : commandLine.getArguments()) {
                builder.append(' ').append(argument);
            }
            Logger.debug(builder.toString());
        }

        return commandLine;
    }

    private void executeCommandLine(final CommandLine commandLine, final Path sourceFilePath, final Path destinationFilePath) {
        try {
            DefaultExecutor executor = DefaultExecutor.builder().get();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ByteArrayOutputStream errorOutputStream = new ByteArrayOutputStream();
            ExecuteStreamHandler streamHandler = new PumpStreamHandler(outputStream, errorOutputStream);
            executor.setStreamHandler(streamHandler);
            executor.execute(commandLine);

            String outputStr = new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
            if (outputStr.length() > 0 && Logger.isDebugEnabled()) {
                Logger.debug(outputStr);
            }
            String errorOutputStr = new String(errorOutputStream.toByteArray(), StandardCharsets.UTF_8);
            if (errorOutputStr.length() > 0 && Logger.isErrorEnabled()) {
                Logger.error(errorOutputStr);
            }
            if (sourceFilePath != null && Logger.isInfoEnabled()) {
                Logger.info("File " + sourceFilePath + " is processed");
            }
            if (Logger.isInfoEnabled()) {
                Logger.info("File " + destinationFilePath + " is created");
            }
        } catch (IOException ex) {
            if (Logger.isErrorEnabled()) {
                Logger.error("Exception in ImageMagick execution", ex);
            }
        }
    }

}
