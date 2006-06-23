package org.apache.jcs.access.exception;

/*
 * Copyright 2001-2004 The Apache Software Foundation. Licensed under the Apache
 * License, Version 2.0 (the "License") you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

/**
 * This is the most general exception the cache throws.
 * <p>
 * TODO make nested with no external dependencies.
 */
public class CacheException
    extends Exception
// extends NestableException
{

    private static final long serialVersionUID = 8725795372935590265L;

    /**
     * Default
     */
    public CacheException()
    {
        super();
    }

    /**
     * Constructor for the CacheException object
     * <p>
     * @param nested
     */
    public CacheException( Throwable nested )
    {
        super( nested.getMessage() );
    }

    /**
     * Constructor for the CacheException object
     * <p>
     * @param message
     */
    public CacheException( String message )
    {
        super( message );
    }

}
