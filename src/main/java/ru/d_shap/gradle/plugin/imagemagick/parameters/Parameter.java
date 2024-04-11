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

/**
 * The parameter.
 *
 * @author Dmitry Shapovalov
 */
public abstract class Parameter {

    Parameter() {
        super();
    }

    /**
     * Invoke the parameter.
     *
     * @param context the context.
     *
     * @return the result of invocation.
     */
    public abstract String invoke(Context context);

    final String getPath(final Path path) {
        File file = path.normalize().toFile();
        String absolutePath = file.getAbsolutePath();
        return "\"" + absolutePath + "\"";
    }

    final String toString(final Object object) {
        if (object instanceof String) {
            return (String) object;
        } else {
            return object.toString();
        }
    }

}
