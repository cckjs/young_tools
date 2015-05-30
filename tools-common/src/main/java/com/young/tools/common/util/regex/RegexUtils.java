package com.young.tools.common.util.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

	public static List<List<String>> matcher(String regex,String input,int gnum){
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		List<List<String>> result = new ArrayList<List<String>>();
		List<String> temp = null;
		while(matcher.find()){
			temp = new ArrayList<String>();
			for(int i=1;i<=gnum;i++){
				temp.add(matcher.group(i));
			}
			result.add(temp);
		}
		return result;
	}
}
