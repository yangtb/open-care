package com.sm.open.care.core.utils;

import java.util.Arrays;

public class ToStringUtils {

	private final String className;
	private final ValueHolder holderHead = new ValueHolder();
	private ValueHolder holderTail = holderHead;
	private boolean holdNullValues = false;

	private static final String default_separator = ";";

	private String separatorStr = default_separator;

	public ToStringUtils(Object obj) {
		this.className = obj.getClass().getSimpleName();
	}

	public ToStringUtils(String className) {
		this.className = className;
	}

	public ToStringUtils(Class<?> clazz) {
		this.className = clazz.getSimpleName();
	}

	public ToStringUtils holdNullValues() {
		holdNullValues = true;
		return this;
	}

	public ToStringUtils setSeparator(String separator) {
		separatorStr = separator;
		return this;
	}

	public ToStringUtils append(String name, Object value) {
		return addHolder(name, value);
	}

	public ToStringUtils append(String name, boolean value) {
		return addHolder(name, String.valueOf(value));
	}

	public ToStringUtils append(String name, char value) {
		return addHolder(name, String.valueOf(value));
	}

	public ToStringUtils append(String name, double value) {
		return addHolder(name, String.valueOf(value));
	}

	public ToStringUtils append(String name, float value) {
		return addHolder(name, String.valueOf(value));
	}

	public ToStringUtils append(String name, int value) {
		return addHolder(name, String.valueOf(value));
	}

	public ToStringUtils append(String name, long value) {
		return addHolder(name, String.valueOf(value));
	}

	public ToStringUtils append(String name, byte value) {
		return addHolder(name, String.valueOf(value));
	}

	public ToStringUtils append(String name, short value) {
		return addHolder(name, String.valueOf(value));
	}

	@Override
	public String toString() {
		boolean holdNullValuesSnapshot = holdNullValues;
		String nextSeparator = "";
		StringBuilder builder = new StringBuilder(32).append(className).append('{');
		for (ValueHolder valueHolder = holderHead.next; valueHolder != null; valueHolder = valueHolder.next) {
			Object value = valueHolder.value;
			if (holdNullValuesSnapshot || value != null) {
				builder.append(nextSeparator);
				nextSeparator = separatorStr + " ";
				builder.append(valueHolder.name).append('=');
				if (value != null && value.getClass().isArray()) {
					Object[] objectArray = { value };
					String arrayString = Arrays.deepToString(objectArray);
					builder.append(arrayString, 1, arrayString.length() - 1);
				} else {
					builder.append(value);
				}
			}
		}
		return builder.append('}').toString();
	}

	private ValueHolder addHolder() {
		ValueHolder valueHolder = new ValueHolder();
		holderTail = holderTail.next = valueHolder;
		return valueHolder;
	}

	private ToStringUtils addHolder(String name, Object value) {
		ValueHolder valueHolder = addHolder();
		valueHolder.value = value;
		valueHolder.name = name;
		return this;
	}

	private static final class ValueHolder {
		String name;
		Object value;
		ValueHolder next;
	}

}
