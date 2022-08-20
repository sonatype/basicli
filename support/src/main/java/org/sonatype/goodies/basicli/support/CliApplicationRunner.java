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

import org.sonatype.goodies.basicli.common.ExitNotification;

import picocli.CommandLine;
import picocli.CommandLine.RunAll;

import static java.util.Objects.requireNonNull;

/**
 * {@link CliApplication} runner.
 *
 * @since ???
 */
public class CliApplicationRunner
{
  private final CliApplication application;

  public CliApplicationRunner(final CliApplication application) {
    this.application = requireNonNull(application);
  }

  public int run(final String[] args) {
    CommandLine cl = new CommandLine(application);

    cl.setUsageHelpAutoWidth(true);
    cl.setExecutionStrategy(parsed -> {
      application.init();
      int result;
      try {
        result = new RunAll().execute(parsed);
      }
      finally {
        application.destroy();
      }
      return result;
    });

    int code = 0;
    try {
      code = cl.execute(args);
    }
    catch (ExitNotification n) {
      code = n.getCode();
    }

    return code;
  }
}
