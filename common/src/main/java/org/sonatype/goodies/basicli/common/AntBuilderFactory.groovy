/*
 * Copyright (c) 2022-present Sonatype, Inc. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package org.sonatype.goodies.basicli.common

import groovy.ant.AntBuilder
import groovy.util.logging.Slf4j
import org.apache.tools.ant.DefaultLogger
import org.apache.tools.ant.Project
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static java.util.Objects.requireNonNull

// maybe see: https://github.com/sonatype/ci-job-config/blob/a5bc6ee2fbf990aeef4ce1c970fce7cab4c923f8/neo/src/neo/internal/AntBuilderFactory.groovy

/**
 * {@link AntBuilder} factory.
 *
 * @since ???
 */
@Slf4j
class AntBuilderFactory
{
  static AntBuilder create(final Class owner) {
    requireNonNull(owner)

    def project = new Project()
    def logger = LoggerFactory.getLogger(owner)

    project.addBuildListener(new Slf4jLoggerAdapter(logger))
    project.init()
    return new AntBuilder(project)
  }

  private static class Slf4jLoggerAdapter
      extends DefaultLogger
  {
    private final Logger logger

    Slf4jLoggerAdapter(final Logger logger) {
      this.logger = requireNonNull(logger)
      emacsMode = true

      int level = Project.MSG_INFO
      if (logger.debugEnabled) {
        level = Project.MSG_DEBUG
      }
      else if (!logger.warnEnabled) {
        level = Project.MSG_ERR
      }

      messageOutputLevel = level
    }

    @Override
    protected void printMessage(final String message, final PrintStream stream, final int priority) {
      switch (priority) {
        case Project.MSG_ERR:
          logger.error(message)
          break

        case Project.MSG_WARN:
          logger.warn(message)
          break

        case Project.MSG_INFO:
          logger.info(message)
          break

        case Project.MSG_VERBOSE:
          logger.debug(message)
          break

        case Project.MSG_DEBUG:
          logger.trace(message)
          break
      }
    }
  }
}