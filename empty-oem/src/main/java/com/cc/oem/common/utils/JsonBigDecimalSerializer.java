package com.cc.oem.common.utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author songb
 * @since  2018年6月20日
 *
 */
public class JsonBigDecimalSerializer extends JsonSerializer<BigDecimal> {
	
	/**
	 * format pattern
	 */
	private static String FORMAT_PATTERN = "##############.##";
	

	@Override
	public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		DecimalFormat df = new DecimalFormat(FORMAT_PATTERN);
		gen.writeString(df.format(value));
	}

}
