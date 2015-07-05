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

package com.threecrickets.jvm.json.nashorn;

import java.io.IOException;

import com.threecrickets.jvm.json.JsonContext;
import com.threecrickets.jvm.json.JsonEncoder;

import jdk.nashorn.internal.objects.NativeArray;
import jdk.nashorn.internal.runtime.arrays.ArrayData;

public class NativeArrayEncoder implements JsonEncoder
{
	//
	// JsonEncoder
	//

	public boolean canEncode( Object object, JsonContext context )
	{
		return object instanceof NativeArray;
	}

	public void encode( Object object, JsonContext context ) throws IOException
	{
		ArrayData data = ( (NativeArray) object ).getArray();

		context.out.append( '[' );

		int length = (int) data.length();
		if( length > 0 )
		{
			context.newline();

			for( int i = 0; i < length; i++ )
			{
				Object value = data.getObject( i );

				context.indentNested();
				context.nest().encode( value );

				if( i < length - 1 )
					context.comma();
			}

			context.newline();
			context.indent();
		}

		context.out.append( ']' );
	}
}
