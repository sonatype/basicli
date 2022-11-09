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

import org.slf4j.Logger;

import static java.util.Objects.requireNonNull;

/**
 * Helper to log a block of text in a marked section of the log.
 *
 * @since ???
 */
public class LogMarker
{
  private LogMarker() {
    // empty
  }

  public static void mark(final Logger logger, Level level, final String prefix, final String text) {
    requireNonNull(logger);
    requireNonNull(level);
    requireNonNull(prefix);
    requireNonNull(text);
    level.log(logger, "{}\n----8<----\n{}\n---->8----", prefix, text);
  }
}
