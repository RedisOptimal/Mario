package com.renren.Wario;

import java.util.HashSet;
import java.util.Set;

public class WarioUtilTools {
	
	public static Set<String> getUnion(Set<String> a, Set<String> b) {
		Set<String> res = new HashSet<String>();
		res.clear();
		res.addAll(a);
		res.addAll(b);
		return res;
	}

	public static Set<String> getDifference(Set<String> a, Set<String> b) {
		Set<String> res = new HashSet<String>();
		res.clear();
		res.addAll(a);
		res.removeAll(b);
		return res;
	}

	public static Set<String> getIntersection(Set<String> a, Set<String> b) {
		Set<String> res = new HashSet<String>();
		res.clear();
		res.addAll(a);
		res.retainAll(b);
		return res;
	}
}
