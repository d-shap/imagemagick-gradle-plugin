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
import java.util.List;

import groovy.lang.Closure;

/**
 * The destination file parameter.
 *
 * @author Dmitry Shapovalov
 */
public class DestinationFileParameter extends Parameter {

    private final Closure<?> _closure;

    /**
     * Create new object.
     *
     * @param closure the closure.
     */
    public DestinationFileParameter(final Closure<?> closure) {
        super();
        _closure = closure;
    }

    @Override
    void invoke(final Context context, final List<String> list) {
        Path processedDestinationFilePath;
        if (_closure == null) {
            processedDestinationFilePath = context.getDestinationFilePath();
        } else {
            String destinationFileNameFull = renameDestinationFile(context);
            Path destinationFileParentPath = context.getDestinationFileParentPath();
            processedDestinationFilePath = destinationFileParentPath.resolve(destinationFileNameFull);
        }
        context.setProcessedDestinationFilePath(processedDestinationFilePath);
        String absolutePath = getAbsolutePath(processedDestinationFilePath);
        list.add(absolutePath);
    }

    private String renameDestinationFile(final Context context) {
        String fileName = context.getDestinationFileName();
        String fileExtension = context.getDestinationFileExtension();
        Object callResult = _closure.call(fileName, fileExtension);
        if (callResult instanceof String) {
            return (String) callResult;
        } else {
            return callResult.toString();
        }
    }

}
