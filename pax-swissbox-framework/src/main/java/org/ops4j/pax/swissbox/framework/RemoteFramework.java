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

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.osgi.service.startlevel.StartLevel;

/**
 * An RMI-capable variant of the OSGi {@link Framework} interface.

 * @author Harald Wellmann
 */
public interface RemoteFramework extends Remote
{
    public static final String RMI_PORT_KEY = "pax.swissbox.framework.rmi.port";
    public static final String RMI_NAME_KEY = "pax.swissbox.framework.rmi.name";
    
    /**
     * Initializes the framework.
     * 
     * @see {@link Framework#init}
     * @throws RemoteException
     * @throws BundleException
     */
    void init() throws RemoteException, BundleException;

    /**
     * Starts the framework.
     * 
     * @see {@link Framework#start}
     * @throws RemoteException
     * @throws BundleException
     */
    void start() throws RemoteException, BundleException;

    /**
     * Stops the framework.
     * 
     * @see {@link Framework#start}
     * @throws RemoteException
     * @throws BundleException
     */
    void stop() throws RemoteException, BundleException;
    
    /**
     * Installs a bundle remotely.
     *
     * @param bundleUrl url of the bundle to be installed. The url must be accessible from the remote framework.
     * @return bundle id of the installed bundle
     * @throws RemoteException - Remote communication related exception (mandatory by RMI)
     * @throws BundleException - Re-thrown from installing the bundle
     */
    long installBundle(String bundleUrl)
            throws RemoteException, BundleException;

    /**
     * Installs a bundle remotely, given the bundle content.
     *
     * @param bundleLocation bundle location
     * @param bundle         bundle content as a byte array
     * @return bundle id of the installed bundle
     * @throws RemoteException - Remote communication related exception (mandatory by RMI)
     * @throws BundleException - Re-thrown from installing the bundle
     */
    long installBundle(String bundleLocation, byte[] bundle)
            throws RemoteException, BundleException;

    /**
     * Starts a bundle.
     *
     * @param bundleId id of the bundle to be started
     * @throws RemoteException - Remote communication related exception (mandatory by RMI)
     * @throws BundleException - Re-thrown from starting the bundle
     */
    void startBundle(long bundleId)
            throws RemoteException, BundleException;

    /**
     * Stops a bundle.
     *
     * @param bundleId id of the bundle to be stopped
     * @throws RemoteException - Remote communication related exception (mandatory by RMI)
     * @throws BundleException - Re-thrown from stopping the bundle
     */
    void stopBundle(long bundleId)
            throws RemoteException, BundleException;

    /**
     * Sets bundle start level.
     *
     * @param bundleId   id of the bundle to which the start level should be set
     * @param startLevel bundle start level
     * @throws RemoteException - Remote communication related exception (mandatory by RMI)
     * @throws BundleException - If bundle level cannot be set
     */
    void setBundleStartLevel(long bundleId, int startLevel)
            throws RemoteException, BundleException;

    /**
     * Waits for a bundle to be in a certain state and returns.
     *
     * @param bundleId        bundle id
     * @param state           expected state
     * @param timeoutInMillis max time to wait for state
     * @throws RemoteException  - Remote communication related exception (mandatory by RMI)
     * @throws BundleException  - If bundle cannot be found
     * @throws org.ops4j.pax.exam.TimeoutException - if timeout occured and expected state has not being reached
     */
    void waitForState(long bundleId, int state, long timeoutInMillis)
            throws RemoteException, BundleException;

    /**
     * @param id of bundle to uninstall
     * @throws RemoteException - Remote communication related exception (mandatory by RMI)
     * @throws BundleException - If bundle cannot be found
     */
    void uninstallBundle(long id)
            throws RemoteException, BundleException;
    
    void callService(String filter, String methodName) throws RemoteException, BundleException;
    
    /**
     * Sets the framework startlevel.
     * @see {@link StartLevel#setStartLevel(int)}
     * @param startLevel
     * @throws RemoteException
     */
    void setFrameworkStartLevel(int startLevel) throws RemoteException;
    

}
