package com.sqm.dashboard.util;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "fields" })
@XmlRootElement(name = "Entity")
public class EntityUtility {
	@XmlElement(name = "Fields", required = true)
	protected EntityUtility.Fields fields;
	@XmlAttribute(name = "Type", required = true)
	protected String type;
    /**
     * 
     * @param entity
     */
	public EntityUtility(EntityUtility entity) {
		type = new String(entity.getType());
		fields = new EntityUtility.Fields(entity.getFields());
	}

	
	/**
	 * DefaultConstructor for the class Entity
	 */
	public EntityUtility() {
	}

	/**
	 * 
	 * @return fields
	 */
	public EntityUtility.Fields getFields() {
		return fields;
	}

	/**
	 * 
	 * @param value of field
	 */
	public void setFields(EntityUtility.Fields value) {
		this.fields = value;
	}
	
	/**
	 * 
	 * @return type
	 */

	public String getType() {
		return type;
	}
	
	/**
	 * 
	 * @param value of type String for type
	 */

	public void setType(String value) {
		this.type = value;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "field" })
	public static class Fields {
		@XmlElement(name = "Field", required = true)
		protected List<EntityUtility.Fields.Field> field;

		/**
		 * 
		 * @param fields
		 */
		public Fields(Fields fields) {
			field = new ArrayList<Field>(fields.getField());
		}

		/**
		 * DefaultConstructor for the class Fields
		 */
		public Fields() {
		}

		/**
		 * 
		 * @return List of fields
		 */
		public List<EntityUtility.Fields.Field> getField() {
			if (field == null) {
				field = new ArrayList<EntityUtility.Fields.Field>();
			}
			return this.field;
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "value" })
		public static class Field {
			@XmlElement(name = "Value", required = true)
			protected List<String> value;
			@XmlAttribute(name = "Name", required = true)
			protected String name;

			/**
			 * 
			 * @return list of Strings
			 */
			public List<String> getValue() {
				if (value == null) {
					value = new ArrayList<String>();
				}
				return this.value;
			}
			/**
			 * 
			 * @return name
			 */

			public String getName() {
				return name;
			}
			/**
			 * 
			 * @param value of type String
			 */

			public void setName(String value) {
				this.name = value;
			}
		}
	}
}
