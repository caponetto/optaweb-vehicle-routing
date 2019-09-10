/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaweb.vehiclerouting.plugin.planner.change;

import java.util.ArrayList;
import java.util.Objects;

import org.optaplanner.core.impl.score.director.ScoreDirector;
import org.optaplanner.core.impl.solver.ProblemFactChange;
import org.optaweb.vehiclerouting.plugin.planner.VehicleRoutingSolution;
import org.optaweb.vehiclerouting.plugin.planner.domain.PlanningLocation;
import org.optaweb.vehiclerouting.plugin.planner.domain.PlanningVisit;

public class AddVisit implements ProblemFactChange<VehicleRoutingSolution> {

    private final PlanningVisit visit;

    public AddVisit(PlanningVisit visit) {
        this.visit = Objects.requireNonNull(visit);
    }

    @Override
    public void doChange(ScoreDirector<VehicleRoutingSolution> scoreDirector) {
        VehicleRoutingSolution workingSolution = scoreDirector.getWorkingSolution();
        workingSolution.setLocationList(new ArrayList<>(workingSolution.getLocationList()));

        PlanningLocation location = visit.getLocation();
        scoreDirector.beforeProblemFactAdded(location);
        workingSolution.getLocationList().add(location);
        scoreDirector.afterProblemFactAdded(location);

        scoreDirector.beforeEntityAdded(visit);
        workingSolution.getVisitList().add(visit);
        scoreDirector.afterEntityAdded(visit);

        scoreDirector.triggerVariableListeners();
    }
}