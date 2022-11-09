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

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IO helpers.
 *
 * @since ???
 */
public class IoHelper
{
  private static final Logger log = LoggerFactory.getLogger(IoHelper.class);

  /**
   * Open given argument as STDIN (if '-') or as a file reference.
   */
  public static Reader openSource(final String source) throws IOException {
    boolean stdin = "-".equals(source);
    log.debug("Open source: {}", stdin ? "STDIN" : source);
    if (stdin) {
      return new InputStreamReader(System.in);
    }
    else {
      File file = new File(source).getCanonicalFile();
      log.debug("Reading file: {}", file);
      return new FileReader(file);
    }
  }

  /**
   * Open given argument as STDOUT (if '-') or as a file reference.
   */
  public static Writer openTarget(final String target) throws IOException {
    boolean stdout = "-".equals(target);
    log.debug("Open target: {}", stdout ? "STDOUT" : target);
    if (stdout) {
      return new OutputStreamWriter(System.out);
    }
    else {
      File file = new File(target).getCanonicalFile();
      log.debug("Writing file: {}", file);
      FileHelper.createDirectories(file.getParentFile().toPath());
      return new FileWriter(file);
    }
  }
}