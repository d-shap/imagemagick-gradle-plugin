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
    public String invoke(final Context context) {
        return "-" + _name + getArgs();
    }

    private String getArgs() {
        if (_args instanceof Object[]) {
            StringBuilder result = new StringBuilder();
            for (Object arg : (Object[]) _args) {
                String argStr = toString(arg);
                argStr = getProcessedArgStr(argStr);
                result.append(argStr);
            }
            return result.toString();
        } else {
            return "";
        }
    }

    private String getProcessedArgStr(final String arg) {
        if (arg.startsWith(":")) {
            return arg;
        } else {
            return " " + arg;
        }
    }

}
