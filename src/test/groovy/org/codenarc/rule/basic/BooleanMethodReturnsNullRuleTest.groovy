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
package org.codenarc.rule.basic

import org.codenarc.rule.AbstractRuleTestCase
import org.codenarc.rule.Rule

/**
 * Tests for BooleanMethodReturnsNullRule
 *
 * @author Hamlet D'Arcy
 * @version $Revision: 329 $ - $Date: 2010-04-29 04:20:25 +0200 (Thu, 29 Apr 2010) $
 */
class BooleanMethodReturnsNullRuleTest extends AbstractRuleTestCase {

    void testRuleProperties() {
        assert rule.priority == 2
        assert rule.name == "BooleanMethodReturnsNull"
    }

    void testSuccessScenario() {
        final SOURCE = '''
        	def c = {
                    if (foo()) {
                        return true
                    } else {
                        return false
                    }
            }
            def scriptMethod() {
                if (foo()) {
                    return true
                } else {
                    return false
                }
            }
            class MyClass {
                def x = new Object() {
                    def method1() {
                        if (foo()) {
                            return true
                        } else {
                            return false
                        }
                    }
                }
                def method2() {
                    def y = {
                        if (foo()) {
                            return true
                        } else {
                            return false
                        }
                    }
                    if (foo()) {
                        return true
                    } else {
                        return false
                    }
                }
                boolean method3() {
                    if (foo()) {
                        return true
                    } else {
                        return false
                    }
                }
                boolean method4() {
                    if (foo()) {
                        return ret() as Boolean
                    } else {
                        return ret() as Boolean
                    }
                }
            }
        '''
        assertNoViolations(SOURCE)
    }

    void testDefMethodReturnsNull() {
        final SOURCE = '''
            def scriptMethod() {
                if (foo()) {
                    return true
                } else {
                    return null
                }
            }
        '''
        assertSingleViolation(SOURCE, 6, 'return null')
    }

    void testDefClassMethodReturnsNull() {
        final SOURCE = '''
            class MyClass {
                def scriptMethod() {
                    if (foo()) {
                        return true
                    } else {
                        return null
                    }
                }
            }
        '''
        assertSingleViolation(SOURCE, 7, 'return null')
    }

    void testAnonymousClassMethodReturnsNull() {
        final SOURCE = '''
            def x = new Object() {
                def scriptMethod() {
                    if (foo()) {
                        return true
                    } else {
                        return null
                    }
                }
            }
        '''
        assertSingleViolation(SOURCE, 7, 'return null')
    }

    void testDefMethodReturnsNullAndTRUE() {
        final SOURCE = '''
            def scriptMethod() {
                if (foo()) {
                    return Boolean.TRUE
                } else {
                    return null
                }
            }
        '''
        assertSingleViolation(SOURCE, 6, 'return null')
    }

    void testDefMethodReturnsBooleanCast() {
        final SOURCE = '''
            def scriptMethod() {
                if (foo()) {
                    return ret() as Boolean
                } else {
                    return null
                }
            }
        '''
        assertSingleViolation(SOURCE, 6, 'return null')
    }

    void testBooleanMethodReturnsNull() {
        final SOURCE = '''
            boolean scriptMethod() {
                if (foo()) {
                    return ret()
                } else {
                    return null
                }
            }
        '''
        assertSingleViolation(SOURCE, 6, 'return null')
    }

    protected Rule createRule() {
        return new BooleanMethodReturnsNullRule()
    }
}