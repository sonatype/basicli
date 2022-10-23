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
  public static final String LEVEL_PROPERTY_NAME = "logging.tool.level";

  private Level level = Level.INFO;

  void setLevel(final Level level) {
    this.level = level;

    // re-initialize if already ready
    if (ready) {
      try {
        init();
      }
      catch (JoranException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private volatile boolean ready = false;

  synchronized void init() throws JoranException {
    SystemPropertyHelper.set(LEVEL_PROPERTY_NAME, String.valueOf(level));


    LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
    context.reset();
    JoranConfigurator configurator = new JoranConfigurator();
    configurator.setContext(context);
    configurator.doConfigure(Resources.getResource("logback.xml"));
    ready = true;

    Logger log = getLogger(getClass());
    log.debug("Initialized");
  }

  public Logger getLogger(final Class<?> type) {
    if (!ready) {
      throw new IllegalStateException("Logging not ready");
    }
    return LoggerFactory.getLogger(type);
  }
}
