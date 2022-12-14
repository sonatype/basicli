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

import java.io.File;
import java.io.IOException;

import com.google.common.collect.ImmutableMap;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration manager.
 *
 * @since ???
 */
public class Config
{
  private Config() {
    // empty
  }

  private static final Logger log = LoggerFactory.getLogger(Config.class);

  public static void apply(final File file) throws IOException {
    log.debug("Applying: {}", file);
    Binding binding = new Binding(ImmutableMap.of(
        "log", log
    ));
    GroovyShell shell = new GroovyShell(binding);
    shell.evaluate(file);
  }
}
