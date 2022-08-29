/*
 * Copyright (c) 2022-present Sonatype, Inc. All rights reserved.
 * "Sonatype" is a trademark of Sonatype, Inc.
 */
package org.sonatype.goodies.basicli.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import javax.annotation.Nonnull;

// Copied and adjusted from: https://github.com/sonatype/dropwizard-support/blob/61adfb77a2540c688b526469b57235ca9e9f42e0/core/src/main/java/org/sonatype/goodies/dropwizard/util/PrintBuffer.java

/**
 * Helper to wrap a {@link PrintWriter} with a {@link StringWriter} back-end buffer.
 *
 * @since ???
 */
public class PrintBuffer
    extends PrintWriter
{
  private final StringWriter buffer;

  public PrintBuffer(@Nonnull final StringWriter buffer) {
    super(buffer, false);
    this.buffer = (StringWriter)out;
  }

  public PrintBuffer() {
    this(new StringWriter());
  }

  public StringWriter getBuffer() {
    return buffer;
  }

  @Override
  public String toString() {
    flush();
    return buffer.toString();
  }
}