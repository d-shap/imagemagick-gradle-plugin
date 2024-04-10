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

import org.slf4j.LoggerFactory;

/**
 * The logger.
 *
 * @author Dmitry Shapovalov
 */
public final class Logger {

    private static final String LOGGER_NAME = "imagemagick";

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LOGGER_NAME);

    private Logger() {
        super();
    }

    /**
     * Log the message with debug level.
     *
     * @param message the message.
     */
    public static void debug(final String message) {
        LOGGER.debug(message);
    }

    /**
     * Log the message with info level.
     *
     * @param message the message.
     */
    public static void info(final String message) {
        LOGGER.info(message);
    }

}
