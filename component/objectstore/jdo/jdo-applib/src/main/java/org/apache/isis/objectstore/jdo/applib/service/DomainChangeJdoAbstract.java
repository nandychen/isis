/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.isis.objectstore.jdo.applib.service;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Immutable;
import org.apache.isis.applib.annotation.MemberGroupLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.TypicalLength;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.objectstore.jdo.applib.service.audit.AuditEntryJdo;
import org.apache.isis.objectstore.jdo.applib.service.command.CommandJdo;
import org.apache.isis.objectstore.jdo.applib.service.publish.PublishedEventJdo;

/**
 * An abstraction of some sort of recorded change to a domain object, either a {@link CommandJdo command}, an
 * {@link AuditEntryJdo audit enty} or a {@link PublishedEventJdo published event}.
 */
@MemberGroupLayout(
        columnSpans={6,0,6,12}, 
        left={"Identifiers"},
        right={"Target","Detail"})
@Named("Domain Change")
@Immutable
public abstract class DomainChangeJdoAbstract {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(DomainChangeJdoAbstract.class);

    public static enum ChangeType {
        COMMAND,
        AUDIT_ENTRY,
        PUBLISHED_EVENT;
        @Override
        public String toString() {
            return name().replace("_", " ");
        }
    }
    public DomainChangeJdoAbstract(final ChangeType changeType) {
        this.type = changeType;
    }
    
    private final ChangeType type;
    /**
     * Distinguishes {@link CommandJdo command}s, {@link AuditEntryJdo audit entries} and {@link PublishedEventJdo published event}s
     * when these are shown mixed together in a (standalone) table.
     */
    @MemberOrder(name="Identifiers", sequence = "1")
    @Hidden(where = Where.ALL_EXCEPT_STANDALONE_TABLES)
    public ChangeType getType() {
        return type;
    }


    // //////////////////////////////////////

    /**
     * The user that caused the change.
     * 
     * <p>
     * This dummy implementation is a trick so that Isis will render the property in a standalone table.  Each of the
     * subclasses override with the &quot;real&quot; implementation.
     */
    @MemberOrder(name="Identifiers", sequence = "10")
    public String getUser() {
        return null;
    }

    // //////////////////////////////////////

    
    /**
     * The time that the change occurred.
     * 
     * <p>
     * This dummy implementation is a trick so that Isis will render the property in a standalone table.  Each of the
     * subclasses override with the &quot;real&quot; implementation.
     */
    @MemberOrder(name="Identifiers", sequence = "20")
    public Timestamp getTimestamp() {
        return null;
    }
    
    // //////////////////////////////////////

    /**
     * The unique identifier (a GUID) of the transaction in which this change occurred.
     * 
     * <p>
     * This dummy implementation is a trick so that Isis will render the property in a standalone table.  Each of the
     * subclasses override with the &quot;real&quot; implementation.
     */
    @MemberOrder(name="Identifiers",sequence = "50")
    public UUID getTransactionId() {
        return null;
    }

    // //////////////////////////////////////

    /**
     * The class of the domain object being changed.
     * 
     * <p>
     * This dummy implementation is a trick so that Isis will render the property in a standalone table.  Each of the
     * subclasses override with the &quot;real&quot; implementation.
     */
    @MemberOrder(name="Target", sequence = "10")
    @Named("Class")
    public String getTargetClass() {
        return null;
    }

    // //////////////////////////////////////

    @Programmatic
    public Bookmark getTarget() {
        return Util.bookmarkFor(getTargetStr());
    }
    
    @Programmatic
    public void setTarget(Bookmark target) {
        setTargetStr(Util.asString(target));
    }

    // //////////////////////////////////////
    
    private String targetAction;
    
    /**
     * The action invoked which caused the domain object to be changed..
     * 
     * <p>
     * Populated only for {@link CommandJdo command}s.
     * 
     * <p>
     * This dummy implementation is a trick so that Isis will render the property in a standalone table.  Each of the
     * subclasses override with the &quot;real&quot; implementation.
     */
    @Hidden(where=Where.ALL_EXCEPT_STANDALONE_TABLES)
    @Optional
    @MemberOrder(name="Target", sequence = "20")
    @Named("Action")
    public String getTargetAction() {
        return targetAction;
    }


    // //////////////////////////////////////

    /**
     * The (string representation of the) {@link Bookmark} identifying the domain object that has changed.
     * 
     * <p>
     * This dummy implementation is a trick so that Isis will render the property in a standalone table.  Each of the
     * subclasses override with the &quot;real&quot; implementation.
     */
    @MemberOrder(name="Target", sequence="30")
    @Named("Object")
    public String getTargetStr() {
        return null;
    }
    
    /**
     * For {@link #setTarget(Bookmark)} to delegate to.
     */
    public abstract void setTargetStr(final String targetStr);


    // //////////////////////////////////////

    @Bulk
    @ActionSemantics(Of.SAFE)
    @MemberOrder(name="TargetStr", sequence="1")
    @Named("Open")
    public Object openTargetObject() {
        return Util.lookupBookmark(getTarget(), bookmarkService, container);
    }
    public boolean hideOpenTargetObject() {
        return getTarget() == null;
    }


    // //////////////////////////////////////
    
    /**
     * The property of the object that was changed.
     * 
     * <p>
     * Populated only for {@link AuditEntryJdo audit entries}.
     * 
     * <p>
     * This dummy implementation is a trick so that Isis will render the property in a standalone table.  Each of the
     * subclasses override with the &quot;real&quot; implementation.
     */
    @Hidden(where=Where.ALL_EXCEPT_STANDALONE_TABLES)
    @Optional
    @MemberOrder(name="Target",sequence = "21")
    public String getPropertyId() {
        return null;
    }

    // //////////////////////////////////////


    /**
     * The value of the property prior to it being changed.
     * 
     * <p>
     * Populated only for {@link AuditEntryJdo audit entries}.
     * 
     * <p>
     * This dummy implementation is a trick so that Isis will render the property in a standalone table.  Each of the
     * subclasses override with the &quot;real&quot; implementation.
     */
    @Hidden(where=Where.ALL_EXCEPT_STANDALONE_TABLES)
    @Optional
    @MemberOrder(name="Detail",sequence = "6")
    public String getPreValue() {
        return null;
    }

    
    // //////////////////////////////////////

    /**
     * The value of the property after it has changed.
     * 
     * <p>
     * Populated only for {@link AuditEntryJdo audit entries}.
     * 
     * <p>
     * This dummy implementation is a trick so that Isis will render the property in a standalone table.  Each of the
     * subclasses override with the &quot;real&quot; implementation.
     */
    @Hidden(where=Where.ALL_EXCEPT_STANDALONE_TABLES)
    @Optional
    @MemberOrder(name="Detail",sequence = "7")
    public String getPostValue() {
        return null;
    }
    

    // //////////////////////////////////////

    public static Comparator<DomainChangeJdoAbstract> compareByTimestampDescThenType(){
        return ObjectContracts.compareBy("timestamp desc,type");
    }

    public static Comparator<DomainChangeJdoAbstract> compareByTargetThenTimestampDescThenType(){
        return ObjectContracts.compareBy("targetStr,timestamp desc,type");
    }
    
    public static Comparator<DomainChangeJdoAbstract> compareByTargetThenUserThenTimestampDescThenType(){
        return ObjectContracts.compareBy("targetStr,user,timestamp desc,type");
    }
    
    public static Comparator<DomainChangeJdoAbstract> compareByUserThenTimestampDescThenType(){
        return ObjectContracts.compareBy("user,timestamp desc,type");
    }
    
    public static Comparator<DomainChangeJdoAbstract> compareByUserThenTargetThenTimestampDescThenType(){
        return ObjectContracts.compareBy("user,targetStr,timestamp desc,type");
    }

    // //////////////////////////////////////
    // dependencies
    // //////////////////////////////////////
    
    @javax.inject.Inject
    protected BookmarkService bookmarkService;
    
    @javax.inject.Inject
    protected DomainObjectContainer container;

}