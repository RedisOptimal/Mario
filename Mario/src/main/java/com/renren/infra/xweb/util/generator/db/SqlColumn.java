package com.renren.infra.xweb.util.generator.db;

import org.apache.commons.lang3.StringUtils;

/**
 * sql的列信息
 * 
 * @author yong.cao
 * 
 */
public class SqlColumn {

	private String colName;
	private String attrName;
	private short colType;
	private int colSize;
	private int digits;
	private String colTypeName;
	private boolean nullable;
	private boolean isContainDefault;
	private String remark;
	private String attrType;
	private boolean key = false;

	public SqlColumn(String colName, String attrName, short colType,
			int colSize, int digits, String colTypeName, boolean nullable,
			boolean isContainDefault, String remark) {
		this.colName = colName;
		if (StringUtils.isBlank(attrName)) {
			this.attrName = attrName;
		} else {
			this.attrName = colName;
		}
		this.colType = colType;
		this.colSize = colSize;
		this.digits = digits;
		this.colTypeName = colTypeName;
		this.nullable = nullable;
		this.isContainDefault = isContainDefault;
		this.remark = remark;

		initAttrType();
	}

	/**
	 * 初始化attr的类型
	 */
	private void initAttrType() {
		attrType = "String";

		if (StringUtils.containsIgnoreCase(colTypeName, "char"))
			attrType = "String";

		else if (StringUtils.containsIgnoreCase(colTypeName, "date"))
			attrType = "java.util.Date";

		else if (StringUtils.containsIgnoreCase(colTypeName, "decimal"))
			attrType = "java.math.BigDecimal";

		else if (StringUtils.containsIgnoreCase(colTypeName, "numeric"))
			attrType = "java.math.BigDecimal";

		else if (StringUtils.containsIgnoreCase(colTypeName, "timestamp"))
			attrType = "java.sql.Timestamp";

		else if (StringUtils.containsIgnoreCase(colTypeName, "small"))
			attrType = "Short";

		else if (StringUtils.containsIgnoreCase(colTypeName, "bigint"))
			attrType = "Long";

		else if (StringUtils.containsIgnoreCase(colTypeName, "int8"))
			attrType = "Long";

		else if (StringUtils.containsIgnoreCase(colTypeName, "bigserial"))
			attrType = "Long";

		else if (StringUtils.containsIgnoreCase(colTypeName, "int"))
			attrType = "Integer";

		else if (StringUtils.containsIgnoreCase(colTypeName, "serial"))
			attrType = "Integer";

		else if (StringUtils.containsIgnoreCase(colTypeName, "double"))
			attrType = "Double";

		else if (StringUtils.containsIgnoreCase(colTypeName, "money"))
			attrType = "Float";

		else if (StringUtils.containsIgnoreCase(colTypeName, "bit"))
			attrType = "Boolean";

		else if (StringUtils.containsIgnoreCase(colTypeName, "Float"))
			attrType = "Float";
	}

	public String getColName() {
		return colName;
	}

	public String getAttrName() {
		return attrName;
	}

	public short getColType() {
		return colType;
	}

	public int getColSize() {
		return colSize;
	}

	public int getDigits() {
		return digits;
	}

	public String getColTypeName() {
		return colTypeName;
	}

	public boolean isNullable() {
		return nullable;
	}

	public boolean isContainDefault() {
		return isContainDefault;
	}

	public String getRemark() {
		return remark;
	}

	public String getAttrType() {
		return attrType;
	}

	public boolean isKey() {
		return key;
	}

	public void setKey(boolean key) {
		this.key = key;
	}

}
