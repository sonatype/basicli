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
package org.sonatype.goodies.basicli.common;

import java.io.PrintStream;

import groovy.ant.AntBuilder;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;

/**
 * {@link AntBuilder} factory.
 *
 * @since ???
 */
public class AntBuilderFactory
{
  public static AntBuilder create(final Class<?> owner) {
    requireNonNull(owner);

    Project project = new Project();
    Logger logger = LoggerFactory.getLogger(owner);

    project.addBuildListener(new Slf4jLoggerAdapter(logger));
    project.init();
    return new AntBuilder(project);
  }

  private static class Slf4jLoggerAdapter
      extends DefaultLogger
  {
    private final Logger logger;

    Slf4jLoggerAdapter(final Logger logger) {
      this.logger = requireNonNull(logger);
      emacsMode = true;

      int level = Project.MSG_INFO;
      if (logger.isDebugEnabled()) {
        level = Project.MSG_DEBUG;
      }
      else if (!logger.isWarnEnabled()) {
        level = Project.MSG_ERR;
      }
      setMessageOutputLevel(level);
    }

    @Override
    protected void printMessage(final String message, final PrintStream stream, final int priority) {
      switch (priority) {
        case Project.MSG_ERR:
          logger.error(message);
          break;

        case Project.MSG_WARN:
          logger.warn(message);
          break;

        case Project.MSG_INFO:
          logger.info(message);
          break;

        case Project.MSG_VERBOSE:
          logger.debug(message);
          break;

        case Project.MSG_DEBUG:
          logger.trace(message);
          break;
      }
    }
  }
}