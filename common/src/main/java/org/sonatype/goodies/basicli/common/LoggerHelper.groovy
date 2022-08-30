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
