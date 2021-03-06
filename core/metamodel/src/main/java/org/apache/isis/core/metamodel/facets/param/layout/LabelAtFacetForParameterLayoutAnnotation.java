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

package org.apache.isis.core.metamodel.facets.param.layout;

import org.apache.isis.applib.annotation.LabelPosition;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.core.metamodel.facetapi.FacetHolder;
import org.apache.isis.core.metamodel.facets.propparam.layout.LabelAtFacet;
import org.apache.isis.core.metamodel.facets.propparam.layout.LabelAtFacetAbstract;

public class LabelAtFacetForParameterLayoutAnnotation extends LabelAtFacetAbstract {

    public static LabelAtFacet create(final ParameterLayout parameterLayout, FacetHolder holder) {
        if (parameterLayout == null) {
            return null;
        }
        final LabelPosition labelPosition = parameterLayout.labelPosition();
        return labelPosition != null ? new LabelAtFacetForParameterLayoutAnnotation(labelPosition, holder) : null;
    }

    private LabelAtFacetForParameterLayoutAnnotation(final LabelPosition value, final FacetHolder holder) {
        super(value, holder);
    }

}
