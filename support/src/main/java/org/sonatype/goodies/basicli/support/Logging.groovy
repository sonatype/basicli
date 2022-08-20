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
package org.sonatype.goodies.basicli.support

import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.joran.JoranConfigurator
import com.google.common.io.Resources
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.event.Level

/**
 * Logging manager.
 *
 * @since ???
 */
@Singleton
class Logging
{
  public static final String LEVEL_PROPERTY_NAME = 'logging.tool.level'

  Level level = Level.INFO

  void setLevel(final Level level) {
    this.level = level

    // re-initialize if already ready
    ready && init()
  }

  private volatile boolean ready = false

  synchronized void init() {
    SystemPropertyHelper.set(LEVEL_PROPERTY_NAME, level as String)

    (LoggerFactory.ILoggerFactory as LoggerContext).with { context ->
      context.reset()
      new JoranConfigurator(context: context)
          .doConfigure(Resources.getResource('logback.xml'))
    }

    ready = true

    def log = getLogger(getClass())
    log.debug('Initialized')
  }

  Logger getLogger(final Class<?> type) {
    assert ready : 'Logging not ready'
    return LoggerFactory.getLogger(type);
  }
}
