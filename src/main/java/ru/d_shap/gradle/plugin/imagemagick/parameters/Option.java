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

import java.util.List;

/**
 * The option.
 *
 * @author Dmitry Shapovalov
 */
public final class Option {

    private final String _name;

    private String _prefix;

    private List<Object> _args;

    /**
     * Create new object.
     *
     * @param name the name.
     */
    public Option(final String name) {
        super();
        _name = name;
        _prefix = null;
        _args = null;
    }

    /**
     * Get the name.
     *
     * @return the name.
     */
    public String getName() {
        return _name;
    }

    /**
     * Get the prefix.
     *
     * @return the prefix.
     */
    public String getPrefix() {
        return _prefix;
    }

    /**
     * Set the prefix.
     *
     * @param prefix the prefix.
     */
    public void setPrefix(final String prefix) {
        _prefix = prefix;
    }

    /**
     * Get the args.
     *
     * @return the args.
     */
    public List<Object> getArgs() {
        return _args;
    }

    /**
     * Set the args.
     *
     * @param args the args.
     */
    public void setArgs(final List<Object> args) {
        _args = args;
    }

}
