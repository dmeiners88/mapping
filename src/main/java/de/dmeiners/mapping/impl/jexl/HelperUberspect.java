package de.dmeiners.mapping.impl.jexl;

import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.internal.introspection.Uberspect;
import org.apache.commons.jexl3.introspection.JexlUberspect;
import org.apache.commons.logging.LogFactory;

public class HelperUberspect extends Uberspect {

	public HelperUberspect() {

		super(LogFactory.getLog(JexlEngine.class), JexlUberspect.JEXL_STRATEGY);
	}
}
