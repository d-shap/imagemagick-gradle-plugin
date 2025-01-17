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
package ru.d_shap.gradle.plugin.imagemagick.configuration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.d_shap.gradle.plugin.imagemagick.parameters.DestinationFileParameter;
import ru.d_shap.gradle.plugin.imagemagick.parameters.OptionParameter;
import ru.d_shap.gradle.plugin.imagemagick.parameters.Parameter;
import ru.d_shap.gradle.plugin.imagemagick.parameters.SourceFileParameter;

import groovy.lang.Closure;
import groovy.util.Eval;

/**
 * The parameters configuration.
 *
 * @author Dmitry Shapovalov
 */
public class ParametersConfiguration {

    private final List<Parameter> _parameters;

    /**
     * Create new object.
     */
    @Inject
    public ParametersConfiguration() {
        super();
        _parameters = new ArrayList<>();
    }

    /**
     * Get the parameters.
     *
     * @return the parameters.
     */
    public List<Parameter> getParameters() {
        return _parameters;
    }

    /**
     * Add the source file parameter.
     */
    public void sourceFile() {
        Parameter parameter = new SourceFileParameter();
        _parameters.add(parameter);
    }

    /**
     * Add the destination file parameter.
     */
    public void destinationFile() {
        Parameter parameter = new DestinationFileParameter(null);
        _parameters.add(parameter);
    }

    /**
     * Add the destination file parameter.
     *
     * @param fileName the file name.
     */
    public void destinationFile(final String fileName) {
        Closure<?> closure = (Closure<?>) Eval.me("{ name, extension -> '" + fileName + "' }");
        destinationFile(closure);
    }

    /**
     * Add the destination file parameter.
     *
     * @param closure the closure.
     */
    public void destinationFile(final Closure<?> closure) {
        Parameter parameter = new DestinationFileParameter(closure);
        _parameters.add(parameter);
    }

    /**
     * Add an arbitrary parameter.
     *
     * @param name the name.
     * @param args the args.
     */
    public void methodMissing(final String name, final Object args) {
        Parameter parameter = new OptionParameter(name, args);
        _parameters.add(parameter);
    }

}
