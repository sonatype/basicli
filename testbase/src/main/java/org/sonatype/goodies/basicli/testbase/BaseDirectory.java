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
package org.sonatype.goodies.basicli.testbase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Base-directory helper.
 *
 * @since ???
 */
public class BaseDirectory
{
  private final Class<?> type;

  private BaseDirectory(final Class<?> type) {
    this.type = type;
  }

  public File getFile() {
    File dir;
    try {
      dir = new File(type.getProtectionDomain().getCodeSource().getLocation().getFile()).getCanonicalFile();
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
    // expected class to be in target/test-classes
    return dir.getParentFile().getParentFile();
  }

  public Path getPath() {
    return getFile().toPath();
  }

  public File resolve(final String path) {
    try {
      return new File(getFile(), path).getCanonicalFile();
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public File require(final String path) {
    File file = resolve(path);
    if (!file.exists()) {
      throw new RuntimeException("Missing required file: " + path);
    }
    return file;
  }

  //
  // Factory
  //

  public static BaseDirectory of(final Object context) {
    requireNonNull(context);
    Class<?> type = context instanceof Class ? (Class<?>) context : context.getClass();
    return new BaseDirectory(type);
  }
}
