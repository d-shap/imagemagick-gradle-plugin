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

import org.gradle.api.Action;
import org.gradle.api.model.ObjectFactory;

/**
 * ImageMagick gradle plugin extension.
 *
 * @author Dmitry Shapovalov
 */
public class ImageMagickGradlePluginExtension {

    private final ImageMagickGradlePluginPipeline _pipeline;

    /**
     * Create new object.
     *
     * @param objectFactory the object factory.
     */
    public ImageMagickGradlePluginExtension(final ObjectFactory objectFactory) {
        super();
        _pipeline = objectFactory.newInstance(ImageMagickGradlePluginPipeline.class);
    }

    /**
     * Get the pipeline.
     *
     * @return the pipeline.
     */
    public ImageMagickGradlePluginPipeline getPipeline() {
        return _pipeline;
    }

    /**
     * aaa.
     *
     * @param action aaa.
     */
    public void pipeline(final Action<? super ImageMagickGradlePluginPipeline> action) {
        action.execute(_pipeline);
    }

}
