package com.codingzombies.easytest.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="ronald@codingzombies.com">Ron de Leon</a>
 */
public final class Config {
	
	private static Properties props = new Properties();
	static {
		
	    try(InputStream stream = Config.class.getClassLoader().getResourceAsStream("app.properties")) {
	        props.load(stream);
	    } catch (final IOException e) {
	        e.printStackTrace();
	    }
	    
		try(InputStream stream = Config.class.getClassLoader().getResourceAsStream("test.properties")) {
			props.load(stream);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private Config() {}
	
	public static String get(final String key) {
		return props.getProperty(key);
	}

	public static String get(final String key, final String defaultValue) {
		return props.getProperty(key, defaultValue);
	}

	public static boolean getBoolean(final String key) {
		return Boolean.parseBoolean(props.getProperty(key));
	}
	
	public static boolean getBoolean(final String key, final boolean defaultValue) {
		final String property = props.getProperty(key);
		if(StringUtils.isEmpty(property)) {
			return defaultValue;
		}
		return getBoolean(key);
	}

	public static int getInt(final String key) {
		return Integer.parseInt(props.getProperty(key));
	}
	
	public static int getInt(final String key, final int defaultValue) {
		final String property = props.getProperty(key);
		if(StringUtils.isEmpty(property)) {
			return defaultValue;
		}
		return getInt(key);
	}
	
	public static List<String> getList(final String key, final List<String> emptyList) {
		final String property = props.getProperty(key);
		if(StringUtils.isBlank(property)) {
			return Collections.emptyList();
		}
		return Arrays.asList(StringUtils.split(property, ","));
	}
	
}
