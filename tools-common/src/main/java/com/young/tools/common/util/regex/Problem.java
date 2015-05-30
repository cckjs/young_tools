package com.young.tools.common.util.regex;

import java.util.ArrayList;
import java.util.List;

public class Problem {

	private String name;
	
	private String desc;
	
	private String money;
	
	private List<Problem> subProblems = new ArrayList<Problem>();

	public Problem(String name,String money,String desc){
		this.name = name;
		this.money = money;
		this.desc = desc;
	}
	
	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public String getMoney() {
		return money;
	}
	
	public void addSubProblem(Problem p){
		this.subProblems.add(p);
	}
	
	public void addSubProblems(List<Problem> p){
		this.subProblems.addAll(p);
	}

	public List<Problem> getSubProblems() {
		return subProblems;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("["+name+","+money+"]"+"\n"+"{");
		for(Problem p:subProblems){
			sb.append(p.name+","+p.money+"\n");
		}
		sb.append("}\n");
		return sb.toString();
	}
	
	
}
