package com.nayidisha.empdep.util;

import java.util.Collections;
import java.util.List;

public class MixUtils<T> {

	@SuppressWarnings("unchecked")
	public List<T> safeList(List<T> list){
		return (list == null?Collections.EMPTY_LIST:list);
	}
}
