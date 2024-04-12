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
 * The option parameter.
 *
 * @author Dmitry Shapovalov
 */
public class OptionParameter extends Parameter {

    private final String _name;

    private final Object _args;

    /**
     * Create new object.
     *
     * @param name the name.
     * @param args the args.
     */
    public OptionParameter(final String name, final Object args) {
        super();
        _name = name;
        _args = args;
    }

    @Override
    void invoke(final Context context, final List<String> list) {
        list.add("-" + _name);
        if (_args instanceof Object[]) {
            for (Object arg : (Object[]) _args) {
                String argStr = toString(arg);
                if (concatArgWithPrevious(argStr)) {
                    int idx = list.size() - 1;
                    list.set(idx, list.get(idx) + argStr);
                } else {
                    list.add(argStr);
                }
            }
        }
    }

    private boolean concatArgWithPrevious(final String arg) {
        if (arg.startsWith(":")) {
            return true;
        }
        if (arg.startsWith("@")) {
            return true;
        }
        if (arg.startsWith("[")) {
            return true;
        }
        return false;
    }

}
