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

import org.junit.jupiter.api.Test

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.arrayWithSize
import static org.hamcrest.Matchers.is

// copied and adjusted from: https://raw.githubusercontent.com/sonatype/goodies/master/common/src/test/java/org/sonatype/goodies/common/Throwables2Test.java

/**
 * {@link ThrowableHelper} tests.
 */
class ThrowableHelperTest
{
  @Test
  void 'explain single throwable'() {
    String msg = ThrowableHelper.explain(new RuntimeException('foo'))
    println(msg)
    assertThat(msg, is('java.lang.RuntimeException: foo'))
  }

  @Test
  void 'explain nested throwable'() {
    String msg = ThrowableHelper.explain(
        new RuntimeException('foo',
            new Exception('bar')
        )
    )
    println(msg)
    assertThat(msg, is('java.lang.RuntimeException: foo, caused by: java.lang.Exception: bar'))
  }

  @Test
  void 'explain nested 3x throwable'() {
    String msg = ThrowableHelper.explain(
        new RuntimeException('foo',
            new Exception(
                new Exception('bar')
            )
        )
    )
    println(msg)
    assertThat(msg, is('java.lang.RuntimeException: foo, caused by: java.lang.Exception, caused by: java.lang.Exception: bar'))
  }

  @Test
  void 'explain nested 4x throwable'() {
    String msg = ThrowableHelper.explain(
        new RuntimeException('foo',
            new Exception('bar',
                new Exception('baz')
            )
        )
    )
    println(msg)
    assertThat(msg, is('java.lang.RuntimeException: foo, caused by: java.lang.Exception: bar, caused by: java.lang.Exception: baz'))
  }

  @Test
  void 'composite adds suppressed exceptions'() {
    Throwable foo = new Exception('foo')
    Throwable bar = new Exception('bar')
    try {
      throw ThrowableHelper.composite(new Exception('test'), foo, bar)
    }
    catch (Exception e) {
      assertThat(e.getSuppressed(), arrayWithSize(2))
      assertThat(e.getSuppressed()[0], is(foo))
      assertThat(e.getSuppressed()[1], is(bar))
    }
  }

  @Test
  void 'explain composite'() {
    try {
      ThrowableHelper.composite(
          new Exception('test'),
          new Exception('foo'),
          new Exception('bar')
      )
    }
    catch (e) {
      def msg = ThrowableHelper.explain(e)
      println msg
      assert msg == 'java.lang.Exception: test, suppressed: java.lang.Exception: foo, suppressed: java.lang.Exception: bar'
    }
  }
}