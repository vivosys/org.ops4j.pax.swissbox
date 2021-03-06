/*
 * Copyright 2011 Harald Wellmann.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.pax.swissbox.framework;

import java.util.Map;
import java.util.Map.Entry;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.util.tracker.ServiceTracker;

/**
 * A utility class for looking up services from the OSGi registry. The methods of this class wait
 * for the service for a given timeout (default 10 seconds) and throw a
 * {@code TestContainerException} when no matching service becomes available during this period.
 * <p>
 * NOTE: Prefixing some method calls with our own class name is a workaround for a bug in the Oracle
 * Java compiler, which does not occur when compiling in Eclipse.
 * 
 * @author Harald Wellmann
 * 
 */
public class ServiceLookup
{

    public static final long DEFAULT_TIMEOUT = 10000;

    public static <T> T getService( BundleContext bc, String className )
    {
        return ServiceLookup.<T> getService( bc, className, DEFAULT_TIMEOUT, "" );
    }

    public static <T> T getService( BundleContext bc, Class<T> type )
    {
        return getService( bc, type, DEFAULT_TIMEOUT );
    }

    public static <T> T getService( BundleContext bc, Class<T> type, Map<String, String> props )
    {
        return getService( bc, type, DEFAULT_TIMEOUT, props );
    }

    /**
     * Returns a service matching the given criteria.
     * 
     * @param <T> class implemented or extended by the service
     * @param bc bundle context for accessing the OSGi registry
     * @param type class implemented or extended by the service
     * @param timeout maximum wait period in milliseconds
     * @param props properties to be matched by the service
     * @return matching service (not null)
     * @throws ServiceLookupException
     */
    public static <T> T getService( BundleContext bc, Class<T> type, long timeout,
            Map<String, String> props )
    {
        return ServiceLookup.<T> getService( bc, type.getName(), timeout, props );
    }

    public static <T> T getService( BundleContext bc, Class<T> type, long timeout )
    {
        return ServiceLookup.<T> getService( bc, type.getName(), timeout, "" );
    }

    public static <T> T getService( BundleContext bc, Class<T> type, long timeout, String filter )
    {
        return ServiceLookup.<T> getService( bc, type.getName(), timeout, filter );
    }

    @SuppressWarnings( "unchecked" )
    public static <T> T getService( BundleContext bc, String className, long timeout,
            Map<String, String> props )
    {
        ServiceTracker tracker = createServiceTracker( bc, className, props );
        try
        {
            tracker.open();
            Object svc = tracker.waitForService( timeout );
            if( svc == null )
            {
                throw new ServiceLookupException( "gave up waiting for service " + className );
            }
            return (T) svc;
        }
        catch ( InterruptedException exc )
        {
            throw new ServiceLookupException( exc );
        }
        finally
        {
            tracker.close();
        }
    }

    @SuppressWarnings( "unchecked" )
    public static <T> T getService( BundleContext bc, String className, long timeout,
            String filter )
    {
        ServiceTracker tracker = createServiceTracker( bc, className, filter );
        try
        {
            tracker.open();
            Object svc = tracker.waitForService( timeout );
            if( svc == null )
            {
                throw new ServiceLookupException( "gave up waiting for service " + className );
            }
            return (T) svc;
        }
        catch ( InterruptedException exc )
        {
            throw new ServiceLookupException( exc );
        }
        finally
        {
            tracker.close();
        }
    }

    private static ServiceTracker createServiceTracker( BundleContext bc, String className,
            Map<String, String> props )
    {
        if( props == null || props.isEmpty() )
        {
            return new ServiceTracker( bc, className, null );
        }

        StringBuilder builder = new StringBuilder( "(&(objectClass=" );
        builder.append( className );
        builder.append( ')' );
        for( Entry<String, String> entry : props.entrySet() )
        {
            builder.append( '(' );
            builder.append( entry.getKey() );
            builder.append( '=' );
            builder.append( entry.getValue() );
            builder.append( ')' );
        }
        builder.append( ')' );
        return createServiceTrackerWithFilter( bc, builder.toString() );
    }

    private static ServiceTracker createServiceTrackerWithFilter( BundleContext bc,
            String ldapFilter )
    {
        try
        {
            Filter filter;
            filter = bc.createFilter( ldapFilter );
            ServiceTracker tracker = new ServiceTracker( bc, filter, null );
            return tracker;
        }
        catch ( InvalidSyntaxException exc )
        {
            throw new ServiceLookupException( exc );
        }
    }

    private static ServiceTracker createServiceTracker( BundleContext bc, String className,
            String filterString )
    {
        StringBuilder builder = new StringBuilder( "(&(objectClass=" );
        builder.append( className );
        builder.append( ')' );
        if( filterString != null )
        {
            builder.append( filterString );
        }
        builder.append( ')' );
        return createServiceTrackerWithFilter( bc, builder.toString() );
    }

    public static Object getServiceByFilter(BundleContext bc, String ldapFilter ) {
        return getServiceByFilter( bc, ldapFilter, DEFAULT_TIMEOUT );
    }
    
    
    public static Object getServiceByFilter(BundleContext bc, String ldapFilter, long timeout ) {
        try
        {
            Filter filter = bc.createFilter( ldapFilter );
            ServiceTracker tracker = new ServiceTracker( bc, filter, null );
            tracker.open();
            Object svc = tracker.waitForService( timeout );
            return svc;
        }
        catch ( InvalidSyntaxException exc )
        {
            throw new ServiceLookupException( exc );
        }
        catch ( InterruptedException exc )
        {
            throw new ServiceLookupException( exc );
        }
    }
}
