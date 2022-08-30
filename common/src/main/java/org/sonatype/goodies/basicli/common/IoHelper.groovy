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

import java.nio.charset.StandardCharsets

import groovy.util.logging.Slf4j

/**
 * IO helpers.
 *
 * @since ???
 */
@Slf4j
class IoHelper
{
  private static final String UTF_8 = StandardCharsets.UTF_8.toString()

  /**
   * Open given argument as STDIN (if '-') or as a file reference.
   */
  static Reader openSource(final String source) {
    def stdin = source == '-'
    log.debug("Open source: ${stdin ? 'STDIN' : source}")
    if (stdin) {
      return System.in.newReader()
    }
    else {
      def file = new File(source).canonicalFile
      log.debug("Reading file: $file")
      return file.newReader(UTF_8)
    }
  }

  /**
   * Open given argument as STDOUT (if '-') or as a file reference.
   */
  static Writer openTarget(final String target) {
    def stdout = target == '-'
    log.debug("Open target: ${stdout ? 'STDOUT' : target}")
    if (stdout) {
      return System.out.newWriter(UTF_8)
    }
    else {
      def file = new File(target).canonicalFile
      log.debug("Writing file: $file")
      FileHelper.createDirectories(file.parentFile.toPath())
      return file.newWriter(UTF_8)
    }
  }
}
