/*
 * Copyright (C) 2000-2014 Silverpeas
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * As a special exception to the terms and conditions of version 3.0 of
 * the GPL, you may redistribute this Program in connection with Writer Free/Libre
 * Open Source Software ("FLOSS") applications as described in Silverpeas's
 * FLOSS exception.  You should have recieved a copy of the text describing
 * the FLOSS exception, and it is also available here:
 * "http://www.silverpeas.org/legal/licensing"
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.silverpeas.components.suggestionbox.web;

import com.silverpeas.web.TestResources;
import com.silverpeas.web.mock.UserDetailWithProfiles;
import org.silverpeas.components.suggestionbox.mock.SuggestionBoxServiceMockWrapper;
import org.silverpeas.components.suggestionbox.model.Suggestion;
import org.silverpeas.components.suggestionbox.model.SuggestionBox;
import org.silverpeas.components.suggestionbox.model.SuggestionBoxService;
import org.silverpeas.persistence.model.identifier.UuidIdentifier;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.silverpeas.components.suggestionbox.web.SuggestionBoxResourceURIs.BOX_BASE_URI;

/**
 * The resources required to run unit tests on the REST-based web services.
 * @author mmoquillon
 */
@Named(TestResources.TEST_RESOURCES_NAME)
public class SuggestionBoxTestResources extends TestResources {

  public static final String JAVA_PACKAGE = "org.silverpeas.components.suggestionbox.web";
  public static final String SPRING_CONTEXT = "spring-suggestion-box-webservice.xml";
  public static final String COMPONENT_INSTANCE_ID = "suggestionBox119";
  public static final String SUGGESTION_BOX_ID = "suggestion-box-1";
  public static final String SUGGESTION_ID = "suggestion_1";
  public static final String SUGGESTION_BOX_URI = BOX_BASE_URI + "/" + COMPONENT_INSTANCE_ID + "/"
      + SUGGESTION_BOX_ID;
  public static final String SUGGESTIONS_URI_BASE = SUGGESTION_BOX_URI + "/suggestions/";
  public static final String SUGGESTION_URI = SUGGESTIONS_URI_BASE + SUGGESTION_ID;

  @Inject
  private SuggestionBoxServiceMockWrapper mockWrapper;

  private UserDetailWithProfiles creator;

  @PostConstruct
  public void prepareMocks() {
    SuggestionBoxService service = getSuggestionBoxService();
    SuggestionBox box = aSuggestionBox();
    when(service.getByComponentInstanceId(COMPONENT_INSTANCE_ID)).thenReturn(box);
    when(service.findSuggestionById(eq(box), anyString())).thenReturn(Suggestion.NONE);
    when(service.findSuggestionById(box, SUGGESTION_ID)).thenReturn(aSuggestion());
  }


  public SuggestionBoxService getSuggestionBoxService() {
    return mockWrapper.getMock();
  }

  public UserDetailWithProfiles aUserCreator() {
    if (creator == null) {
      creator = aUser();
      creator.setId("creatorId");
    }
    return creator;
  }

  public SuggestionBox aSuggestionBox() {
    SuggestionBox box = new SuggestionBox(COMPONENT_INSTANCE_ID);
    ReflectionTestUtils.setField(box, "id", new UuidIdentifier().fromString(SUGGESTION_BOX_ID));
    box.setCreator(aUserCreator());
    return box;
  }

  public Suggestion aSuggestion() {
    Suggestion suggestion = new Suggestion("A suggestion title");
    ReflectionTestUtils.setField(suggestion, "id", new UuidIdentifier().fromString(SUGGESTION_ID));
    ReflectionTestUtils.setField(suggestion, "suggestionBox", aSuggestionBox());
    suggestion.setCreator(aUserCreator());
    suggestion.setContent("A suggestion content");
    return suggestion;
  }

  public Suggestion aRandomSuggestion() {
    Suggestion suggestion = new Suggestion("A suggestion title");
    ReflectionTestUtils
        .setField(suggestion, "id", new UuidIdentifier().fromString(UUID.randomUUID().toString()));
    ReflectionTestUtils.setField(suggestion, "suggestionBox", aSuggestionBox());
    suggestion.setCreator(aUserCreator());
    suggestion.setContent("A suggestion content");
    return suggestion;
  }
}