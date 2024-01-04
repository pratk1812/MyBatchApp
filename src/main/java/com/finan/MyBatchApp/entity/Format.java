package com.finan.MyBatchApp.entity;


import org.apache.commons.lang3.StringUtils;


public class Format {
	
	private static String formatName(String name) {
        return StringUtils.substringBefore(name, " ");
    }

    private static String formatClass(String classString) {
        return StringUtils.leftPad(classString, 2, '0');
    }

    private static String formatDiv(String div) {
        return StringUtils.rightPad(div, 2, '0');
    }

	private static String formatAddress(String address) {
		return StringUtils.join(StringUtils.split(address.replaceAll("-", ""), ","), ",");
	}
	private static String formatFee(double fee) {
        return StringUtils.leftPad(String.valueOf(fee/1000), 4, '0');
	}
	public static String entityString(StudentEntity student) {
		
		String[] formattedStrings = {
			formatName(student.getName()),
			formatClass(student.getClassString()),
			formatDiv(student.getDivision()),
			String.valueOf(student.getId()),
			formatAddress(student.getAddress()),
			formatFee(student.getFee())
		};
		
		return StringUtils.join(formattedStrings, "|");
	}
}
