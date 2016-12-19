package com.infotree.qliktest.admin.common;

import java.util.Collection;
@SuppressWarnings("rawtypes")
public final class CollectionUtil {
	
	private CollectionUtil() {
	}
	

	public static boolean isObjectCollectionOfClazzObjects(Object obj,Class clazz) {
		if (obj instanceof Collection) {
			for (Object anObject : (Collection) obj) {
				if (! clazz.isInstance(anObject)) {
					return false;
				}
			}
		}
		else{
			return false; //not a collection
		}
		return true;
	}
}
