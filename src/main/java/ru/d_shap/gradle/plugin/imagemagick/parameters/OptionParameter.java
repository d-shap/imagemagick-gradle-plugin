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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.gradle.api.InvalidUserDataException;

import groovy.lang.Closure;

/**
 * The option parameter.
 *
 * @author Dmitry Shapovalov
 */
public final class OptionParameter extends Parameter {

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
        Option option = createOption();
        list.add(option.getPrefix() + option.getName());

        List<Object> args = option.getArgs();
        if (args != null) {
            for (Object arg : args) {
                String argStr = toString(arg);
                if (concatArgWithPrevious(argStr)) {
                    int lastIndex = list.size() - 1;
                    String lastValue = list.get(lastIndex);
                    lastValue += argStr;
                    list.set(lastIndex, lastValue);
                } else {
                    list.add(argStr);
                }
            }
        }
    }

    private Option createOption() {
        String name = _name.replace('_', '-');
        Option option = new Option(name);
        if (_args instanceof Object[]) {
            if (((Object[]) _args).length == 1 && ((Object[]) _args)[0] instanceof Closure) {
                Closure<?> closure = (Closure<?>) ((Object[]) _args)[0];
                closure.setDelegate(option);
                closure.setResolveStrategy(Closure.DELEGATE_ONLY);
                closure.call();
            } else {
                option.setPrefix("-");
                List<Object> args = new ArrayList<>();
                for (Object arg : (Object[]) _args) {
                    if (isValidArgType(arg)) {
                        args.add(arg);
                    } else {
                        throw new InvalidUserDataException("Wrong parameter args configuration");
                    }
                }
                option.setArgs(args);
            }
        } else {
            throw new InvalidUserDataException("Wrong parameter configuration");
        }
        return option;
    }

    private boolean isValidArgType(final Object arg) {
        if (arg instanceof CharSequence) {
            return true;
        }
        if (arg instanceof Boolean) {
            return true;
        }
        if (arg instanceof Byte) {
            return true;
        }
        if (arg instanceof Short) {
            return true;
        }
        if (arg instanceof Integer) {
            return true;
        }
        if (arg instanceof Long) {
            return true;
        }
        if (arg instanceof Float) {
            return true;
        }
        if (arg instanceof Double) {
            return true;
        }
        if (arg instanceof Character) {
            return true;
        }
        if (arg instanceof BigInteger) {
            return true;
        }
        if (arg instanceof BigDecimal) {
            return true;
        }
        return false;
    }

    private boolean concatArgWithPrevious(final String arg) {
        if (arg.startsWith(":")) {
            return true;
        }
        if (arg.startsWith("_")) {
            return true;
        }
        if (arg.startsWith("-")) {
            return true;
        }
        if (arg.startsWith("+")) {
            return true;
        }
        if (arg.startsWith("@")) {
            return true;
        }
        if (arg.startsWith("[")) {
            return true;
        }
        if (arg.startsWith("(")) {
            return true;
        }
        return false;
    }

}
