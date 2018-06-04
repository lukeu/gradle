/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.plugins.ide.tooling.r49

import org.gradle.integtests.tooling.fixture.TargetGradleVersion
import org.gradle.integtests.tooling.fixture.ToolingApiSpecification
import org.gradle.integtests.tooling.fixture.ToolingApiVersion
//import org.gradle.tooling.model.UnsupportedMethodException
import org.gradle.tooling.model.eclipse.EclipseProject
//import spock.lang.Issue

@ToolingApiVersion('>=4.9')
@TargetGradleVersion('>=4.9')
class ToolingApiEclipseModelProjectCrossVersionSpec extends ToolingApiSpecification {

    def setup() {
        settingsFile << 'rootProject.name = "root"'
    }

    def "Project has default project attributes"() {
        when:
        EclipseProject project = loadToolingModel(EclipseProject)

        then:
        println "PC: " + project.getClass()
        project.projectNatures.isEmpty()
    }

    def "Project has some project attributes specified"() {
        buildFile <<
        """apply plugin: 'java'
           apply plugin: 'eclipse'
           eclipse.project {
               natures = ['nature.a']
               linkedResource name: 'linkToDelete1', type: '2', locationUri: '../some-directory'
           }
        """

        when:
        EclipseProject project = loadToolingModel(EclipseProject)

        then:
        project.projectNatures.collect { it.id } == ['nature.a']
    }

}
