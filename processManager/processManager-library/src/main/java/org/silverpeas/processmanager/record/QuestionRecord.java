/*
 * Copyright (C) 2000 - 2018 Silverpeas
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * As a special exception to the terms and conditions of version 3.0 of
 * the GPL, you may redistribute this Program in connection with Free/Libre
 * Open Source Software ("FLOSS") applications as described in Silverpeas's
 * FLOSS exception. You should have recieved a copy of the text describing
 * the FLOSS exception, and it is also available here:
 * "http://www.silverpeas.org/docs/core/legal/floss_exception.html"
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.silverpeas.processmanager.record;

import java.util.HashMap;
import java.util.Map;

import org.silverpeas.core.ResourceReference;
import org.silverpeas.core.contribution.content.form.DataRecord;
import org.silverpeas.core.contribution.content.form.Field;
import org.silverpeas.core.contribution.content.form.FormException;
import org.silverpeas.core.contribution.content.form.field.TextFieldImpl;
import org.silverpeas.core.silvertrace.SilverTrace;

public class QuestionRecord implements DataRecord {

  private static final long serialVersionUID = 4978363746794966549L;
  String content = null;
  String id = null;
  TextFieldImpl contentField = null;

  /**
   * A QuestionRecord is built from a Question
   */
  public QuestionRecord(String content) {
    this.content = content;
    contentField = new TextFieldImpl();
    contentField.setStringValue(content);
  }

  /**
   * Returns the data record id. The record is known by its external id.
   */
  public String getId() {
    return id;
  }

  /**
   * Gives an id to the record. Caution ! the record is known by its external id.
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Returns all the fields
   */
  public Field[] getFields() {
    try {
      Field[] fields = new Field[1];
      fields[0] = getField("Content");
      return fields;
    } catch (FormException fe) {
      return null;
    }
  }

  /**
   * Returns the named field.
   * @throws FormException when the fieldName is unknown.
   */
  public Field getField(String fieldName) throws FormException {
    if (fieldName.equals("Content")) {
      return getField(0);
    } else {
      throw new FormException("QuestionRecord", "workflowEngine.ERR_FIELD_NOT_FOUND");
    }
  }

  @Override
  public Field getField(String fieldName, int occurrence) {
    try {
      return getField(fieldName);
    } catch (FormException e) {
      SilverTrace.warn("processManager", "QuestionRecord.getField", "form.EXP_UNKNOWN_FIELD", e);
    }
    return null;
  }

  /**
   * Returns the field at the index position in the record.
   * @throws FormException when the fieldIndex is unknown.
   */
  public Field getField(int fieldIndex) throws FormException {
    if (fieldIndex == 0) {
      return contentField;
    } else {
      throw new FormException("QuestionRecord", "workflowEngine.ERR_FIELD_NOT_FOUND");
    }
  }

  public String[] getFieldNames() {
    return null;
  }

  /**
   * Return true if this record has not been inserted in a RecordSet.
   */
  public boolean isNew() {
    return true;
  }

  public String getLanguage() {
    return null;
  }

  public void setLanguage(String language) {
  }

  @Override
  public Map<String, String> getValues(String language) {
    return new HashMap<>();
  }

  @Override
  public ResourceReference getResourceReference() {
    return null;
  }
}