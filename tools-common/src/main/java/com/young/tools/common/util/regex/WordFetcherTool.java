package com.young.tools.common.util.regex;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class WordFetcherTool {

	private static final String company_pattern = "被审计单位";

	private static final String record_pattern = "审计项目";

	private static final String comany_record_fetch_pattern = ":|：";

	private static final String contain_number = ".*\\d.*";

	private static final String sentence_pattern = "，|,|。|；|;|:|：|、";

	private static final String money_pattern = "((?:\\d+年?)?\\D+)(\\d+.?\\d{0,5}万?亿?元)";

	private static final int pattern_group = 2;

	private static List<String> splitParagraphs(File docFile)
			throws FileNotFoundException, IOException {
		if (docFile.getName().endsWith("doc")) {
			return getDocParaphs(docFile);
		} else if (docFile.getName().endsWith("docx")) {
			return getDocxParaphs(docFile);
		} else {
			return null;
		}
	}

	private static List<String> getDocxParaphs(File docFile)
			throws FileNotFoundException, IOException {
		XWPFDocument document = new XWPFDocument(new FileInputStream(docFile));
		List<XWPFParagraph> paragraphs = document.getParagraphs();
		List<String> result = new ArrayList<String>();
		for (XWPFParagraph p : paragraphs) {
			if (!p.getText().trim().equals("")) {
				result.add(p.getText().trim());
			}
		}
		return result;
	}

	private static List<String> getDocParaphs(File docFile)
			throws FileNotFoundException, IOException {
		HWPFDocument document = new HWPFDocument(new FileInputStream(docFile));
		Range range = document.getRange();
		int sectionNum = range.numSections();
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < sectionNum; i++) {
			int paraNum = range.getSection(i).numParagraphs();
			for (int j = 0; j < paraNum; j++) {
				if (!range.getSection(i).getParagraph(j).text().trim()
						.equals("")) {
					result.add(range.getSection(i).getParagraph(j).text()
							.trim());
				}
			}
		}
		return result;
	}

	private static List<Problem> fetchProblem(String sentence) {
		List<List<String>> result = RegexUtils.matcher(money_pattern, sentence,
				pattern_group);
		List<Problem> problems = new ArrayList<Problem>();
		if (result != null && result.size() != 0) {
			for (List<String> list : result) {
				problems.add(new Problem(list.get(0), list.get(1), sentence));
			}
		}
		return problems;
	}

	public static DocInfo fetchDocInfo(File docFile)
			throws FileNotFoundException, IOException {
		DocInfo docInfo = new DocInfo();
		List<String> paragraphs = splitParagraphs(docFile);
		if (paragraphs == null || paragraphs.size() == 0) {
			return docInfo;
		}
		String[] temp = null;
		Problem p = null;
		for (String para : paragraphs) {
			if (para.startsWith(company_pattern)) {
				docInfo.setCompany(para.split(comany_record_fetch_pattern)[1]);
			} else if (para.startsWith(record_pattern)) {
				docInfo.setRecord(para.split(comany_record_fetch_pattern)[1]);
			} else if (para.contains("元") && para.matches(contain_number)) {
				 System.out.println(para);
				temp = para.split(sentence_pattern);
				int count = 0;
				for (String str : temp) {
					if (str.contains("元") && str.matches(contain_number)) {
						if (count == 0) {
							// System.out.println(str);
							p = fetchProblem(str.replaceAll(" ", "")).get(0);
						} else {
							p.addSubProblems(fetchProblem(str.replaceAll(" ",
									"")));
						}
						count++;
					}

				}
				docInfo.addProblem(p);
			}
		}
		return docInfo;
	}

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		File file = new File("G:\\工作\\捷云项目\\云南审计厅\\");
		File[] files = file.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith("doc") || name.endsWith("docx");
			}
		});
		for (File f : files) {
			DocInfo docInfo = WordFetcherTool.fetchDocInfo(f);
			System.out.println(docInfo);
		}
	}
}
