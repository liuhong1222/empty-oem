/**
 * 
 */
package com.cc.oem.common.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * @author ChuangLan
 *
 */
public class DateConverter implements Converter<String, Date> {
	
	private static final List<String> formarts = new ArrayList<>(4);
    static{
        formarts.add("yyyy-MM");
        formarts.add("yyyy-MM-dd");
        formarts.add("yyyy-MM-dd HH:mm");
        formarts.add("yyyy-MM-dd HH:mm:ss");
    }

	@Override
	public Date convert(String source) {
		if(StringUtils.isBlank(source)) {
			return null;
		}
		String value = source.trim();        
        if(source.matches("^\\d{4}-\\d{1,2}$")){
            return DateTimeUtil.parseDate(value, formarts.get(0));
        }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")){
            return DateTimeUtil.parseDate(value, formarts.get(1));
        }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")){
            return DateTimeUtil.parseDate(value, formarts.get(2));
        }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")){
            return DateTimeUtil.parseDate(value, formarts.get(3));
        }else {
            throw new IllegalArgumentException("Invalid boolean value '" + source + "'");
        }
	}

}
