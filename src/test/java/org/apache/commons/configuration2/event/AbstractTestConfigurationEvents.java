/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.configuration2.event;

import junit.framework.TestCase;

import org.apache.commons.configuration2.AbstractConfiguration;

/**
 * Base class for testing events generated by configuration classes derived from
 * AbstractConfiguration. This class implements a couple of tests related to
 * event generation. Concrete sub classes only have to implement the
 * <code>createConfiguration()</code> method for creating an instance of a
 * specific configuration class. Because tests for detail events depend on a
 * concrete implementation an exact sequence of events cannot be checked.
 * Instead the corresponding test methods check whether the enclosing events
 * (not the detail events) are of the expected type.
 *
 * @version $Id$
 */
public abstract class AbstractTestConfigurationEvents extends TestCase
{
    /** Constant for a test property name. */
    static final String TEST_PROPNAME = "event.test";

    /** Constant for a test property value. */
    static final String TEST_PROPVALUE = "a value";

    /** Constant for an existing property. */
    static final String EXIST_PROPERTY = "event.property";

    /** The configuration to be tested. */
    protected AbstractConfiguration config;

    /** A test event listener. */
    protected ConfigurationListenerTestImpl l;

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        config = createConfiguration();
        config.addProperty(EXIST_PROPERTY, "existing value");
        l = new ConfigurationListenerTestImpl(config);
        config.addConfigurationListener(l);
    }

    /**
     * Creates the configuration instance to be tested.
     *
     * @return the configuration instance under test
     */
    protected abstract AbstractConfiguration createConfiguration();

    /**
     * Tests events generated by addProperty().
     */
    public void testAddPropertyEvent()
    {
        config.addProperty(TEST_PROPNAME, TEST_PROPVALUE);
        l.checkEvent(AbstractConfiguration.EVENT_ADD_PROPERTY, TEST_PROPNAME,
                TEST_PROPVALUE, true);
        l.checkEvent(AbstractConfiguration.EVENT_ADD_PROPERTY, TEST_PROPNAME,
                TEST_PROPVALUE, false);
        l.done();
    }

    /**
     * Tests events generated by addProperty() when detail events are enabled.
     */
    public void testAddPropertyEventWithDetails()
    {
        config.setDetailEvents(true);
        config.addProperty(TEST_PROPNAME, TEST_PROPVALUE);
        l.checkEventCount(2);
        l.checkEvent(AbstractConfiguration.EVENT_ADD_PROPERTY, TEST_PROPNAME,
                TEST_PROPVALUE, true);
        l.skipToLast(AbstractConfiguration.EVENT_ADD_PROPERTY);
        l.checkEvent(AbstractConfiguration.EVENT_ADD_PROPERTY, TEST_PROPNAME,
                TEST_PROPVALUE, false);
        l.done();
    }

    /**
     * Tests events generated by clearProperty().
     */
    public void testClearPropertyEvent()
    {
        config.clearProperty(EXIST_PROPERTY);
        l.checkEvent(AbstractConfiguration.EVENT_CLEAR_PROPERTY,
                EXIST_PROPERTY, null, true);
        l.checkEvent(AbstractConfiguration.EVENT_CLEAR_PROPERTY,
                EXIST_PROPERTY, null, false);
        l.done();
    }

    /**
     * Tests events generated by clearProperty() when detail events are enabled.
     */
    public void testClearPropertyEventWithDetails()
    {
        config.setDetailEvents(true);
        config.clearProperty(EXIST_PROPERTY);
        l.checkEventCount(2);
        l.checkEvent(AbstractConfiguration.EVENT_CLEAR_PROPERTY,
                EXIST_PROPERTY, null, true);
        l.skipToLast(AbstractConfiguration.EVENT_CLEAR_PROPERTY);
        l.checkEvent(AbstractConfiguration.EVENT_CLEAR_PROPERTY,
                EXIST_PROPERTY, null, false);
        l.done();
    }

    /**
     * Tests events generated by setProperty().
     */
    public void testSetPropertyEvent()
    {
        config.setProperty(EXIST_PROPERTY, TEST_PROPVALUE);
        l.checkEvent(AbstractConfiguration.EVENT_SET_PROPERTY, EXIST_PROPERTY,
                TEST_PROPVALUE, true);
        l.checkEvent(AbstractConfiguration.EVENT_SET_PROPERTY, EXIST_PROPERTY,
                TEST_PROPVALUE, false);
        l.done();
    }

    /**
     * Tests events generated by setProperty() when detail events are enabled.
     */
    public void testSetPropertyEventWithDetails()
    {
        config.setDetailEvents(true);
        config.setProperty(EXIST_PROPERTY, TEST_PROPVALUE);
        l.checkEventCount(2);
        l.checkEvent(AbstractConfiguration.EVENT_SET_PROPERTY, EXIST_PROPERTY,
                TEST_PROPVALUE, true);
        l.skipToLast(AbstractConfiguration.EVENT_SET_PROPERTY);
        l.checkEvent(AbstractConfiguration.EVENT_SET_PROPERTY, EXIST_PROPERTY,
                TEST_PROPVALUE, false);
        l.done();
    }

    /**
     * Tests the events generated by the clear() method.
     */
    public void testClearEvent()
    {
        config.clear();
        l.checkEvent(AbstractConfiguration.EVENT_CLEAR, null, null, true);
        l.checkEvent(AbstractConfiguration.EVENT_CLEAR, null, null, false);
        l.done();
    }

    /**
     * Tests the events generated by the clear method when detail events are
     * enabled.
     */
    public void testClearEventWithDetails()
    {
        config.setDetailEvents(true);
        config.clear();
        l.checkEventCount(2);
        l.checkEvent(AbstractConfiguration.EVENT_CLEAR, null, null, true);
        l.skipToLast(AbstractConfiguration.EVENT_CLEAR);
        l.checkEvent(AbstractConfiguration.EVENT_CLEAR, null, null, false);
        l.done();
    }
}
