package biz.sudden;

import java.util.HashMap;

import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.AnyMetaDefs;
import org.hibernate.annotations.MetaValue;

import biz.sudden.baseAndUtility.domain.connectable.Connectable;

public class Util {
	private static HashMap<Class<?>, String> metadef = null;

	synchronized public static String getPlayerMetaDef(Connectable instance) {
		if (instance != null) {
			Class<?> connectable = instance.getClass();

			if (metadef == null) {
				metadef = new HashMap<Class<?>, String>();
				AnyMetaDefs anyMetaDefs = Package.getPackage("biz.sudden")
						.getAnnotation(AnyMetaDefs.class);
				for (AnyMetaDef anyDef : anyMetaDefs.value()) {
					if (anyDef.name().equals("connectable")) {
						for (MetaValue value : anyDef.metaValues()) {
							metadef.put(value.targetEntity(), value.value());
						}
					}
				}
			}

			// logger.debug("getPlayerMetadef - connectable: "+connectable.getCanonicalName()
			// + " value 4 key: "+metadef.get(connectable));
			if (metadef.containsKey(connectable)) {
				return metadef.get(connectable);
			} else if (instance instanceof org.hibernate.proxy.HibernateProxy) {
				Object instance2 = ((org.hibernate.proxy.HibernateProxy) instance)
						.getHibernateLazyInitializer().getImplementation();
				return metadef.get(instance2.getClass());
			} else {
				System.err.println("METADEF!!");
				System.err.println(connectable);
				System.err.println(connectable.getCanonicalName());
				System.err.println(instance.toString());
				return "";
			}
		} else
			return "";
	}
}
