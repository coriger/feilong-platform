/**
 * Copyright (c) 2008-2013 FeiLong, Inc. All Rights Reserved.
 * <p>
 * 	This software is the confidential and proprietary information of FeiLong Network Technology, Inc. ("Confidential Information").  <br>
 * 	You shall not disclose such Confidential Information and shall use it 
 *  only in accordance with the terms of the license agreement you entered into with FeiLong.
 * </p>
 * <p>
 * 	FeiLong MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, 
 * 	INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * 	PURPOSE, OR NON-INFRINGEMENT. <br> 
 * 	FeiLong SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * 	THIS SOFTWARE OR ITS DERIVATIVES.
 * </p>
 */
package elsepackage.temp;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Stack;

/** Instrumentation agent used */
public class SizeOfAgent{

	static Instrumentation	inst;

	/** initializes agent */
	public static void premain(String agentArgs,Instrumentation instP){
		inst = instP;
	}

	/**
	 * Returns object size without member sub-objects.
	 * 
	 * @param o
	 *            object to get size of
	 * @return object size
	 */
	public static long sizeOf(Object o){
		if (inst == null){
			throw new IllegalStateException("Can not access instrumentation environment.n"
					+ "Please check if jar file containing SizeOfAgent class is n"
					+ "specified in the java's \"-javaagent\" command line argument.");
		}
		return inst.getObjectSize(o);
	}

	/**
	 * Calculates full size of object iterating over 037: * its hierarchy graph.
	 * 
	 * @param obj
	 *            object to calculate size of
	 * @return object size
	 */
	public static long fullSizeOf(Object obj){
		Map<Object, Object> visited = new IdentityHashMap<Object, Object>();
		Stack<Object> stack = new Stack<Object>();
		long result = internalSizeOf(obj, stack, visited);
		while (!stack.isEmpty()){
			result += internalSizeOf(stack.pop(), stack, visited);
		}
		visited.clear();
		return result;
	}

	private static boolean skipObject(Object obj,Map<Object, Object> visited){
		if (obj instanceof String){
			// skip interned string
			if (obj == ((String) obj).intern()){
				return true;
			}
		}
		return (obj == null) // skip visited object
				|| visited.containsKey(obj);
	}

	private static long internalSizeOf(Object obj,Stack<Object> stack,Map<Object, Object> visited){
		if (skipObject(obj, visited)){
			return 0;
		}
		visited.put(obj, null);
		long result = 0;
		// get size of object + primitive variables + member pointers
		result += SizeOfAgent.sizeOf(obj);
		// process all array elements
		Class clazz = obj.getClass();
		if (clazz.isArray()){
			if (clazz.getName().length() != 2){// skip primitive type array
				int length = Array.getLength(obj);
				for (int i = 0; i < length; i++){
					stack.add(Array.get(obj, i));
				}
			}
			return result;
		}
		// process all fields of the object
		while (clazz != null){
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++){
				if (!Modifier.isStatic(fields[i].getModifiers())){
					if (fields[i].getType().isPrimitive()){
						continue; // skip primitive fields
					}else{
						fields[i].setAccessible(true);
						try{
							// objects to be estimated are put to stack
							Object objectToAdd = fields[i].get(obj);
							if (objectToAdd != null){
								stack.add(objectToAdd);
							}
						}catch (IllegalAccessException ex){
							assert false;
						}
					}
				}
			}
			clazz = clazz.getSuperclass();
		}
		return result;
	}
}
