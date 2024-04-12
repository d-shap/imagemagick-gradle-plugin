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
package ru.d_shap.gradle.plugin.imagemagick.parameters;

import java.nio.file.Path;

import org.gradle.api.Project;

/**
 * The context.
 *
 * @author Dmitry Shapovalov
 */
public class Context {

    private final Path _sourceFilePath;

    private final Path _destinationFilePath;

    private final Path _destinationFileParentPath;

    private final String _destinationFileName;

    private final String _destinationFileExtension;

    /**
     * Create new object.
     *
     * @param sourceFilePath      the source file path.
     * @param destinationFilePath the destination file path.
     */
    public Context(final Project project, final Path sourceFilePath, final Path destinationFilePath) {
        super();
        _sourceFilePath = sourceFilePath;
        _destinationFilePath = destinationFilePath;
        _destinationFileParentPath = _destinationFilePath.getParent();
        String destinationFileNameFull = _destinationFilePath.toFile().getName();
        int destinationFileExtensionIndex = destinationFileNameFull.lastIndexOf('.');
        if (destinationFileExtensionIndex >= 0) {
            _destinationFileName = destinationFileNameFull.substring(0, destinationFileExtensionIndex);
            _destinationFileExtension = destinationFileNameFull.substring(destinationFileExtensionIndex + 1);
        } else {
            _destinationFileName = destinationFileNameFull;
            _destinationFileExtension = null;
        }
    }

    /**
     * Get the source file path.
     *
     * @return the source file path.
     */
    public Path getSourceFilePath() {
        return _sourceFilePath;
    }

    /**
     * Get the destination file path.
     *
     * @return the destination file path.
     */
    public Path getDestinationFilePath() {
        return _destinationFilePath;
    }

    /**
     * Get the destination file parent path.
     *
     * @return the destination file parent path.
     */
    public Path getDestinationFileParentPath() {
        return _destinationFileParentPath;
    }

    /**
     * Get the destination file name.
     *
     * @return the destination file name.
     */
    public String getDestinationFileName() {
        return _destinationFileName;
    }

    /**
     * Get the destination file extension.
     *
     * @return the destination file extension.
     */
    public String getDestinationFileExtension() {
        return _destinationFileExtension;
    }

}
