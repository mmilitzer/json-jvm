/**
 * Copyright 2010-2015 Three Crickets LLC.
 * <p>
 * The contents of this file are subject to the terms of the Mozilla Public
 * License version 1.1: http://www.mozilla.org/MPL/MPL-1.1.html
 * <p>
 * Alternatively, you can obtain a royalty free commercial license with less
 * limitations, transferable or non-transferable, directly from Three Crickets
 * at http://threecrickets.com/
 */

package com.threecrickets.jvm.json.java;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import com.threecrickets.jvm.json.JsonContext;
import com.threecrickets.jvm.json.JsonEncoder;

public class MapEncoder implements JsonEncoder
{
	//
	// JsonEncoder
	//

	public boolean canEncode( Object object, JsonContext context )
	{
		return object instanceof Map;
	}

	public void encode( Object object, JsonContext context ) throws IOException
	{
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) object;

		context.out.append( '{' );

		if( !map.isEmpty() )
		{
			context.newline();

			for( Iterator<Map.Entry<String, Object>> i = map.entrySet().iterator(); i.hasNext(); )
			{
				Map.Entry<String, Object> entry = i.next();

				context.indentNested();
				context.quoted( entry.getKey() );
				context.colon();
				context.nest().encode( entry.getValue() );

				if( i.hasNext() )
					context.comma();
			}

			context.newline();
			context.indent();
		}

		context.out.append( '}' );
	}
}
