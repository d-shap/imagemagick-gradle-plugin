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
package ru.d_shap.plugin.imagemagick;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * ImageMagick Gradle Plugin.
 *
 * @author Dmitry Shapovalov
 */
public final class ImageMagickGradlePlugin implements Plugin<Project> {

    public ImageMagickGradlePlugin() {
        super();
    }

    @Override
    public void apply(final Project project) {
        project.task("hello")
                .doLast(task -> System.out.println("Hello Gradle!"));
    }

}
