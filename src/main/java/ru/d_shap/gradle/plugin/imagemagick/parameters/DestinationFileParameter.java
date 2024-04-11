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

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import groovy.lang.Closure;

/**
 * The destination file parameter.
 *
 * @author Dmitry Shapovalov
 */
public class DestinationFileParameter implements Parameter {

    private final Closure<String> _closure;

    /**
     * Create new object.
     *
     * @param closure the closure.
     */
    public DestinationFileParameter(final Closure<String> closure) {
        super();
        _closure = closure;
    }

    @Override
    public String invoke(final Context context) {
        if (_closure == null) {
            Path destinationFilePath = context.getDestinationFilePath();
            return getPath(destinationFilePath);
        } else {
            Map<String, String> delegate = new HashMap<>();
            delegate.put("name", context.getDestinationFileName());
            delegate.put("extension", context.getDestinationFileExtension());
            _closure.setDelegate(delegate);
            _closure.setResolveStrategy(Closure.DELEGATE_ONLY);
            String destinationFileNameFull = _closure.call();
            Path destinationFileParentPath = context.getDestinationFileParentPath();
            Path destinationFilePath = destinationFileParentPath.resolve(destinationFileNameFull);
            return getPath(destinationFilePath);
        }
    }

    private String getPath(final Path path) {
        File file = path.normalize().toFile();
        String absolutePath = file.getAbsolutePath();
        return "\"" + absolutePath + "\"";
    }

}
