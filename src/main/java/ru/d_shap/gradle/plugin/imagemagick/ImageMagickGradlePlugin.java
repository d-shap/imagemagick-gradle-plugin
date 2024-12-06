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
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.UnknownTaskException;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.TaskContainer;

import ru.d_shap.gradle.plugin.imagemagick.configuration.ExtensionConfiguration;

/**
 * ImageMagick gradle plugin.
 *
 * @author Dmitry Shapovalov
 */
public class ImageMagickGradlePlugin implements Plugin<Project> {

    static final String TASK_NAME = "imageMagick";

    static final String EXTENSION_NAME = "imagemagick";

    /**
     * Create new object.
     */
    public ImageMagickGradlePlugin() {
        super();
    }

    @Override
    public void apply(final Project project) {
        Task task = project.task(TASK_NAME);
        addDependencies(project, task);

        ExtensionConfiguration extensionConfiguration = getExtensionConfiguration(project);

        addTaskAction(task, extensionConfiguration);
        addProjectAction(project, task, extensionConfiguration);
    }

    private void addDependencies(final Project project, final Task task) {
        TaskContainer tasks = project.getTasks();
        addDependency(tasks, "processResources", task);
        addDependency(tasks, "compileJava", task);
    }

    private void addDependency(final TaskContainer tasks, final String otherTaskName, final Task task) {
        try {
            Task otherTask = tasks.getByName(otherTaskName);
            otherTask.dependsOn(task);
        } catch (UnknownTaskException ex) {
            // Ignore
        }
    }

    private ExtensionConfiguration getExtensionConfiguration(final Project project) {
        ExtensionContainer extensions = project.getExtensions();
        return extensions.create(EXTENSION_NAME, ExtensionConfiguration.class, project);
    }

    private void addTaskAction(final Task task, final ExtensionConfiguration extensionConfiguration) {
        Action<Task> action = new ImageMagickGradleAction(extensionConfiguration);
        task.doLast(action);
    }

    private void addProjectAction(final Project project, final Task task, final ExtensionConfiguration extensionConfiguration) {
        Action<Project> action = new ImageMagickGradleConfiguration(task, extensionConfiguration);
        project.afterEvaluate(action);
    }

}
