/**
 * Copyright 2010-2017 Three Crickets LLC.
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
import com.threecrickets.jvm.json.util.JsonUtil;

import jdk.nashorn.internal.objects.NativeNumber;

/**
 * A JSON encoder for Nashorn's {@link NativeNumber}.
 * 
 * @author Tal Liron
 */
public class NativeNumberEncoder implements JsonEncoder
{
	//
	// JsonEncoder
	//

	public boolean canEncode( Object object, JsonContext context )
	{
		return object instanceof NativeNumber;
	}

	public void encode( Object object, JsonContext context ) throws IOException
	{
		context.out.append( JsonUtil.numberToString( ( (NativeNumber) object ).getValue() ) );
	}
}
