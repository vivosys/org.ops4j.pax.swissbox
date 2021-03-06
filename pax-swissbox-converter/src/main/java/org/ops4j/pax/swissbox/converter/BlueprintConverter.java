/*
 * Copyright 2009 Alin Dreghiciu.
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

package org.ops4j.pax.swissbox.converter;

import org.osgi.service.blueprint.container.Converter;
import static org.ops4j.pax.swissbox.converter.JavaLangConverter.*;
import static org.ops4j.pax.swissbox.converter.JavaUtilConverter.*;
import org.ops4j.pax.swissbox.converter.helpers.ImmutableCompositeConverter;
import static org.ops4j.pax.swissbox.converter.helpers.ImmutableCompositeConverter.*;
import org.ops4j.pax.swissbox.converter.helpers.WrapperConverter;
import org.ops4j.pax.swissbox.converter.loader.Loader;

/**
 * JAVADOC
 *
 * @author Alin Dreghiciu
 */
public class BlueprintConverter
    extends WrapperConverter
    implements Converter
{

    public static final BlueprintConverter INSTANCE = new BlueprintConverter();

    public BlueprintConverter()
    {
        delegate(
            immutableCompositeConverter(
                javaLangConverter(),
                javaUtilConverter( this )
            )
        );
    }

    public BlueprintConverter( final Converter escape )
    {
        final ImmutableCompositeConverter includingThisEscape = immutableCompositeConverter( escape, this );

        delegate(
            immutableCompositeConverter(
                javaLangConverter(),
                javaUtilConverter( includingThisEscape )
            )
        );
    }

    public BlueprintConverter( final Loader loader )
    {
        delegate(
            immutableCompositeConverter(
                javaLangConverter( loader ),
                javaUtilConverter( this )
            )
        );
    }

    public BlueprintConverter( final Converter escape,
                               final Loader loader )
    {
        final ImmutableCompositeConverter includingThisEscape = immutableCompositeConverter( escape, this );

        delegate(
            immutableCompositeConverter(
                javaLangConverter( loader ),
                javaUtilConverter( includingThisEscape )
            )
        );
    }

    public static BlueprintConverter blueprintConverter()
    {
        return INSTANCE;
    }

    public static BlueprintConverter blueprintConverter( final Converter escape )
    {
        return new BlueprintConverter( escape );
    }

    public static BlueprintConverter blueprintConverter( final Loader loader )
    {
        return new BlueprintConverter( loader );
    }

    public static BlueprintConverter blueprintConverter( final Converter escape,
                                                         final Loader loader )
    {
        return new BlueprintConverter( escape, loader );
    }

}