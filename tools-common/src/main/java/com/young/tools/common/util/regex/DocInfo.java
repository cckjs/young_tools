package com.young.tools.common.util.regex;

import java.util.ArrayList;
import java.util.List;

public class DocInfo {

	private String company;

	private String record;

	private List<Problem> problems = new ArrayList<Problem>();

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public List<Problem> getProblems() {
		return problems;
	}

	public void addProblem(List<Problem> problems) {
		this.problems.addAll(problems);
	}
	
	public void addProblem(Problem p){
		this.problems.add(p);
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("{"+"\n");
		sb.append("["+company+","+record+"]"+"\n");
		for(Problem p:problems){
			sb.append(p.toString());
		}
		sb.append("}"+"\n");
		return sb.toString();
	}

}
