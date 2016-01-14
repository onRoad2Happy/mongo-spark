/*
 * Copyright 2016 MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mongodb.spark.api.java;

import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.spark.MongoConnector;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public final class MongoConnectorTest extends RequiresMongoDB {

    @Test
    public void shouldCreateMongoConnector() {
        String mongoClientURI = getMongoClientURI();
        ArrayList<ServerAddress> expectedServerAddresses = new ArrayList<>();
        for(String host: new MongoClientURI(mongoClientURI).getHosts()) {
            expectedServerAddresses.add(new ServerAddress(host));
        }
        MongoConnector mongoConnector = MongoConnectors.create(mongoClientURI, "db", "coll");

        assertEquals(mongoConnector.mongoClient().getServerAddressList(), expectedServerAddresses);
        assertEquals(mongoConnector.getDatabase().getName(), "db");
        assertEquals(mongoConnector.getCollection().getNamespace().getCollectionName(), "coll");
    }

}