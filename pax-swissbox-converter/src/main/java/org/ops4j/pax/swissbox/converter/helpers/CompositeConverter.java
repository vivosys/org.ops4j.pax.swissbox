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

package org.ops4j.pax.swissbox.converter.helpers;

import java.util.Collection;
import org.osgi.service.blueprint.container.Converter;
import org.ops4j.pax.swissbox.converter.internal.AbstractCompositeConverter;

/**
 * JAVADOC
 *
 * @author Alin Dreghiciu
 */
public class CompositeConverter
    extends AbstractCompositeConverter
    implements Converter
{

    public CompositeConverter( final Converter... converters )
    {
        super( converters );
    }

    public CompositeConverter( final Collection<Converter> converters )
    {
        super( converters );
    }

    public CompositeConverter add( final Converter converter )
    {
        assert converter != null : "Converter must be specified (cannot be null)";

        converters.add( converter );

        return this;
    }

    public CompositeConverter remove( final Converter converter )
    {
        assert converter != null : "Converter must be specified (cannot be null)";

        converters.remove( converter );

        return this;
    }

    public static CompositeConverter compositeConverter( final Converter... converters )
    {
        return new CompositeConverter( converters );
    }

    public static CompositeConverter compositeConverter( final Collection<Converter> converters )
    {
        return new CompositeConverter( converters );
    }

}
