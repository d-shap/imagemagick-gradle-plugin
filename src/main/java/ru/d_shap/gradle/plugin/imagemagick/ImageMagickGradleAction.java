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

import java.io.PrintStream;

import org.gradle.api.Action;
import org.gradle.api.Task;

/**
 * ImageMagick gradle action.
 *
 * @author Dmitry Shapovalov
 */
public final class ImageMagickGradleAction implements Action<Task> {

    private final ImageMagickGradlePluginExtension _imageMagickGradlePluginExtension;

    private final PrintStream _printStream;

    /**
     * Create new object.
     *
     * @param imageMagickGradlePluginExtension ImageMagick gradle plugin extension.
     */
    public ImageMagickGradleAction(final ImageMagickGradlePluginExtension imageMagickGradlePluginExtension) {
        super();
        _imageMagickGradlePluginExtension = imageMagickGradlePluginExtension;
        _printStream = System.out;
    }

    @Override
    public void execute(final Task task) {
        _printStream.println("ImageMagick 2!");
        _printStream.println(_imageMagickGradlePluginExtension.getMessage());
    }

}
