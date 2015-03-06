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
@XmlType(name = "", propOrder = { "entity" })
@XmlRootElement(name = "Entities")
public class EntitiesUtility {
	@XmlElement(name = "Entity", required = true)
	protected EntitiesUtility.Entity entity;
	@XmlAttribute(name = "TotalResults", required = true)
	protected String totalResults;

	/**
	 * 
	 * @param entities
	 */

	public EntitiesUtility(EntitiesUtility entities) {
		totalResults = new String(entities.getTotalResults());
		entity = new EntitiesUtility.Entity(entities.getEntity());
	}

	/**
	 * Constructor of class Entities
	 */
	public EntitiesUtility() {
	}

	/**
	 * 
	 * @return entity
	 */
	public EntitiesUtility.Entity getEntity() {
		return entity;
	}

	/**
	 * 
	 * @param value  of type Entity
	 *           
	 */

	public void setEntity(EntitiesUtility.Entity value) {
		this.entity = value;
	}

	/**
	 * 
	 * @return totalResults
	 */
	public String getTotalResults() {
		return totalResults;
	}

	/**
	 * 
	 * @param value
	 *            of type String
	 */
	public void setTotalResults(String value) {
		this.totalResults = value;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "fields" })
	public static class Entity {
		@XmlElement(name = "Fields", required = true)
		protected EntitiesUtility.Entity.Fields fields;
		@XmlAttribute(name = "Type", required = true)
		protected String type;

		/**
		 * 
		 * @param entity
		 */

		public Entity(Entity entity) {
			type = new String(entity.getType());
			fields = new EntitiesUtility.Entity.Fields(entity.getFields());
		}

		/**
		 * Constructor of class Entity
		 */

		public Entity() {
		}

		/**
		 * 
		 * @return fields
		 */
		public Entity.Fields getFields() {
			return fields;
		}

		/**
		 * 
		 * @param value  of type Entities.Entity.Fields value
		 *           
		 */
		public void setFields(EntitiesUtility.Entity.Fields value) {
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
		 * @param value of type String
		 *            
		 */
		public void setType(String value) {
			this.type = value;
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "field" })
		public static class Fields {
			@XmlElement(name = "Field", required = true)
			protected List<EntitiesUtility.Entity.Fields.Field> field;

			/**
			 * 
			 * @param fields
			 */
			public Fields(Fields fields) {
				field = new ArrayList<Field>(fields.getField());
			}

			/**
			 * Constructor for the class Fields
			 */
			public Fields() {
			}

			/**
			 * 
			 * @return list of fields
			 */
			public List<EntitiesUtility.Entity.Fields.Field> getField() {
				if (field == null) {
					field = new ArrayList<EntitiesUtility.Entity.Fields.Field>();
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
				 * @param value  of type String
				 *           
				 */
				public void setName(String value) {
					this.name = value;
				}
			}
		}
	}
}
