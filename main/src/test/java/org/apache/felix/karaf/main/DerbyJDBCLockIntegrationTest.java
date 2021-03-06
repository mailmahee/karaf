/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.felix.karaf.main;

import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


@Ignore
public class DerbyJDBCLockIntegrationTest extends BaseJDBCLockIntegrationTest {

    @Before
    @Override
    public void setUp() throws Exception {
        password = "root";
        driver = "org.apache.derby.jdbc.ClientDriver";
        url = "jdbc:derby://127.0.0.1:1527/test";
        
        super.setUp();
    }
    
    @Override
    DefaultJDBCLock createLock(Properties props) {
        return new DerbyJDBCLock(props);
    }
    
    @Test
    public void initShouldCreateTheDatabaseIfItNotExists() throws Exception {
        String database = "test" + System.currentTimeMillis();
        url = "jdbc:derby://127.0.0.1:1527/" + database;
        props.put("karaf.lock.jdbc.url", url);
        lock = createLock(props);
        lock.lock();
        
        assertTrue(lock.lockConnection.getMetaData().getURL().contains(database));
    }
}