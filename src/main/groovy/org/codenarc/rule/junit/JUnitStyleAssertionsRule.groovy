/*
 * Copyright 2009 the original author or authors.
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
package org.codenarc.rule.junit

import org.codenarc.rule.AbstractAstVisitor
import org.codenarc.rule.AbstractAstVisitorRule
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codenarc.util.AstUtil

/**
 * This rule detects calling JUnit style assertions like assertEquals, assertTrue, assertFalse, assertNull,
 * assertNotNull. Groovy 1.7 ships with a feature called the "power assert", which is an assert statement with
 * better error reporting. This is preferable to the JUnit assertions.
 *
 * @author Hamlet D'Arcy
 * @version $Revision: 24 $ - $Date: 2009-01-31 13:47:09 +0100 (Sat, 31 Jan 2009) $
 */
class JUnitStyleAssertionsRule extends AbstractAstVisitorRule {
    String name = 'JUnitStyleAssertions'
    int priority = 3
    Class astVisitorClass = JUnitStyleAssertionsAstVisitor
    String applyToClassNames = DEFAULT_TEST_CLASS_NAMES
}

class JUnitStyleAssertionsAstVisitor extends AbstractAstVisitor {

    def void visitMethodCallExpression(MethodCallExpression call) {

        def objects = ['Assert', 'this']
        (1..2).each {
            if (AstUtil.isMethodCall(call, objects, ['assertTrue', 'assertFalse', 'assertNull', 'assertNotNull'], it)) {
                addViolation call
            }
        }
        (2..3).each {
            if (AstUtil.isMethodCall(call, objects, ['assertEquals'], it)) {
                addViolation call
            }
        }
        super.visitMethodCallExpression call
    }


}