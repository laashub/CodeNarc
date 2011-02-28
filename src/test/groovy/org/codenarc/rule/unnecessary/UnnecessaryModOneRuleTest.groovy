/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codenarc.rule.unnecessary

import org.codenarc.rule.AbstractRuleTestCase
import org.codenarc.rule.Rule

/**
 * Tests for UnnecessaryModOneRule
 *
 * @author Hamlet D'Arcy
 * @version $Revision: 329 $ - $Date: 2010-04-29 04:20:25 +0200 (Thu, 29 Apr 2010) $
 */
class UnnecessaryModOneRuleTest extends AbstractRuleTestCase {

    void testRuleProperties() {
        assert rule.priority == 2
        assert rule.name == 'UnnecessaryModOne'
    }

    void testSuccessScenario() {
        final SOURCE = '''
            if (exp & 1) {}     // ok
            if (exp % 2) {}     // ok
        '''
        assertNoViolations(SOURCE)
    }

    void testViolations() {
        final SOURCE = '''
            if (exp % 1) {}         // violation
            if (method() % 1) {}    // violation
        '''
        assertTwoViolations(SOURCE,
                2, '(exp % 1)', '(exp % 1) is guaranteed to be zero. Did you mean (exp & 1) or (exp % 2)',
                3, '(method() % 1)', '(this.method() % 1) is guaranteed to be zero. Did you mean (this.method() & 1) or (this.method() % 2)')
    }

    protected Rule createRule() {
        new UnnecessaryModOneRule()
    }
}