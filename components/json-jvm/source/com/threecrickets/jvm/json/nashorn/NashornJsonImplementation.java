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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.threecrickets.jvm.json.JsonContext;
import com.threecrickets.jvm.json.JsonEncoder;
import com.threecrickets.jvm.json.JsonImplementation;
import com.threecrickets.jvm.json.JsonTransformer;
import com.threecrickets.jvm.json.java.JavaJsonImplementation;
import com.threecrickets.jvm.json.java.NullEncoder;

import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.objects.NativeArray;
import jdk.nashorn.internal.objects.NativeNumber;
import jdk.nashorn.internal.objects.NativeString;
import jdk.nashorn.internal.runtime.ScriptObject;

public class NashornJsonImplementation implements JsonImplementation
{
	//
	// Static operations
	//

	public static void addEncoders( Collection<JsonEncoder> encoders )
	{
		encoders.add( new ConsStringEncoder() );
		encoders.add( new NativeArrayEncoder() );
		encoders.add( new NativeBooleanEncoder() );
		encoders.add( new NativeNumberEncoder() );
		encoders.add( new NativeStringEncoder() );
		encoders.add( new ScriptObjectEncoder() );
		encoders.add( new ScriptObjectMirrorEncoder() );
	}

	//
	// JsonImplementation
	//

	public void initialize()
	{
		addEncoders( encoders );
		JavaJsonImplementation.addEncoders( encoders );
	}

	public String getName()
	{
		return "Nashorn";
	}

	public int getPriority()
	{
		return 0;
	}

	public JsonContext createContext( Appendable out, boolean expand, boolean allowCode, int depth )
	{
		return new JsonContext( this, out, expand, allowCode, depth );
	}

	public Collection<JsonEncoder> getEncoders()
	{
		return Collections.unmodifiableCollection( encoders );
	}

	public JsonEncoder getFallbackEncoder()
	{
		return fallbackEncoder;
	}

	public Collection<JsonTransformer> getTransformers()
	{
		return Collections.unmodifiableCollection( transformers );
	}

	public Object createObject()
	{
		return Global.newEmptyInstance();
	}

	public void putInObject( Object object, String key, Object value )
	{
		( (ScriptObject) object ).put( key, value, true );
	}

	public Object createArray( int length )
	{
		return NativeArray.construct( true, null, length );
	}

	public void setInArray( Object object, int index, Object value )
	{
		( (NativeArray) object ).set( index, value, 0 );
	}

	public Object createString( String value )
	{
		return NativeString.constructor( true, null, value );
	}

	public Object createDouble( double value )
	{
		return NativeNumber.constructor( true, null, value );
	}

	public Object createInteger( int value )
	{
		return Global.toObject( value );
	}

	public Object createLong( long value )
	{
		return Global.toObject( value );
	}

	// //////////////////////////////////////////////////////////////////////////
	// Protected

	protected final ArrayList<JsonEncoder> encoders = new ArrayList<JsonEncoder>();

	protected final ArrayList<JsonTransformer> transformers = new ArrayList<JsonTransformer>();

	// //////////////////////////////////////////////////////////////////////////
	// Private

	private final JsonEncoder fallbackEncoder = new NullEncoder();
}
