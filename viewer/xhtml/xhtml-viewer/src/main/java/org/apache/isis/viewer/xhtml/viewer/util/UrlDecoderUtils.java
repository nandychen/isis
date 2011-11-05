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
package org.apache.isis.viewer.xhtml.viewer.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.ws.rs.WebApplicationException;

import org.apache.isis.viewer.xhtml.viewer.Constants;

public final class UrlDecoderUtils {

    private UrlDecoderUtils() {
    }

    public static String urlDecode(final String oidStr) {
        try {
            return URLDecoder.decode(oidStr, Constants.URL_ENCODING_CHAR_SET);
        } catch (final UnsupportedEncodingException e) {
            throw new WebApplicationException(e);
        }
    }

}