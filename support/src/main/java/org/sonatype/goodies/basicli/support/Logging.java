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
package org.sonatype.goodies.basicli.support;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

/**
 * Logging manager.
 *
 * @since ???
 */
public class Logging
{
  private Logging() {
    // empty
  }

  public static final String LEVEL_PROPERTY_NAME = "logging.tool.level";

  private static Level _level = Level.INFO;

  public static void setLevel(final Level level) {
    Logging._level = level;

    // re-initialize if already ready
    if (ready) {
      init();
    }
  }

  private static volatile boolean ready = false;

  public static synchronized void init() {
    SystemPropertyHelper.set(LEVEL_PROPERTY_NAME, String.valueOf(_level));


    LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
    context.reset();
    JoranConfigurator configurator = new JoranConfigurator();
    configurator.setContext(context);

    try {
      configurator.doConfigure(Resources.getResource("logback.xml"));
    }
    catch (JoranException e) {
      throw new RuntimeException(e);
    }

    ready = true;

    Logger log = getLogger(Logging.class);
    log.debug("Initialized");
  }

  public static Logger getLogger(final Class<?> type) {
    if (!ready) {
      throw new IllegalStateException("Logging not ready");
    }
    return LoggerFactory.getLogger(type);
  }
}