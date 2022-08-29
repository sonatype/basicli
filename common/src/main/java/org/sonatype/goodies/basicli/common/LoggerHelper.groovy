/*
 * Copyright (c) 2022-present Sonatype, Inc. All rights reserved.
 * "Sonatype" is a trademark of Sonatype, Inc.
 */
package org.sonatype.goodies.basicli.common

import org.slf4j.Logger

/**
 * Logger helpers.
 */
class LoggerHelper
{
  private LoggerHelper() {
    // empty
  }

  /**
   * Maybe log verbose throwable details.
   */
  static void maybeLogVerbose(final Logger logger,
                              final Level level,
                              final CharSequence message,
                              final Throwable cause)
  {
    if (logger.traceEnabled) {
      // when TRACE enabled, include cause stack
      level.log(logger, message as String, cause)
    }
    else if (logger.debugEnabled) {
      // when DEBUG enabled, summarize cause
      level.log(logger, "$message; $cause")
    }
    else {
      // otherwise omit cause
      level.log(logger, message as String)
    }
  }
}
