/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package fixture.todo.scenarios;

import fixture.todo.items.delete.ToDoItemsDelete;
import fixture.todo.items.create.ToDoItemForBuyBread;
import fixture.todo.items.create.ToDoItemForBuyMilk;
import fixture.todo.items.create.ToDoItemForBuyStamps;
import fixture.todo.items.create.ToDoItemForMowLawn;
import fixture.todo.items.create.ToDoItemForOrganizeBrownBag;
import fixture.todo.items.create.ToDoItemForPickUpLaundry;
import fixture.todo.items.create.ToDoItemForSharpenKnives;
import fixture.todo.items.create.ToDoItemForStageIsisRelease;
import fixture.todo.items.create.ToDoItemForSubmitConferenceSession;
import fixture.todo.items.create.ToDoItemForVacuumHouse;
import fixture.todo.items.create.ToDoItemForWriteBlogPost;
import fixture.todo.items.create.ToDoItemForWriteToPenPal;
import fixture.todo.util.Util;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class ToDoItemsRecreate extends FixtureScript {

    public ToDoItemsRecreate() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    //region > ownedBy (optional)
    private String ownedBy;

    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(String ownedBy) {
        this.ownedBy = ownedBy;
    }
    //endregion

    @Override
    protected void execute(ExecutionContext executionContext) {

        // defaults
        executionContext.setParameterIfNotPresent(
                "ownedBy",
                Util.coalesce(getOwnedBy(), getContainer().getUser().getName()));

        // prereqs
        executeChild(new ToDoItemsDelete(), executionContext);

        // create items
        executeChild(new ToDoItemForBuyMilk(), executionContext);
        executeChild(new ToDoItemForBuyBread(), executionContext);
        executeChild(new ToDoItemForBuyStamps(), executionContext);
        executeChild(new ToDoItemForPickUpLaundry(), executionContext);
        executeChild(new ToDoItemForMowLawn(), executionContext);
        executeChild(new ToDoItemForVacuumHouse(), executionContext);
        executeChild(new ToDoItemForSharpenKnives(), executionContext);
        executeChild(new ToDoItemForWriteToPenPal(), executionContext);
        executeChild(new ToDoItemForWriteBlogPost(), executionContext);
        executeChild(new ToDoItemForOrganizeBrownBag(), executionContext);
        executeChild(new ToDoItemForSubmitConferenceSession(), executionContext);
        executeChild(new ToDoItemForStageIsisRelease(), executionContext);
    }
}