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
package org.silverpeas.components.questionreply.service.notification;

import org.silverpeas.components.questionreply.QuestionReplyException;
import org.silverpeas.components.questionreply.model.Question;
import org.silverpeas.core.admin.user.model.UserDetail;
import org.silverpeas.core.notification.NotificationException;
import org.silverpeas.core.notification.user.client.NotificationMetaData;
import org.silverpeas.core.notification.user.client.NotificationParameters;
import org.silverpeas.core.notification.user.client.NotificationSender;
import org.silverpeas.core.notification.user.client.UserRecipient;
import org.silverpeas.core.template.SilverpeasTemplate;
import org.silverpeas.core.ui.DisplayI18NHelper;
import org.silverpeas.core.util.Link;
import org.silverpeas.core.util.LocalizationBundle;
import org.silverpeas.core.util.ResourceLocator;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ehugonnet
 */
public class QuestionNotifier extends Notifier {

  final Question question;
  final String componentLabel;
  final String subject;
  final String source;
  final NotificationSender notifSender;

  public QuestionNotifier(UserDetail sender, Question question, NotificationData data) {
    super(sender);
    this.question = question;
    this.componentLabel = data.getComponentLabel();
    this.subject = data.getSubject();
    this.source = data.getSource();
    this.notifSender = new NotificationSender(data.getComponentId());
  }

  /**
   * @param users list of users to notify
   * @throws QuestionReplyException
   */
  @Override
  public void sendNotification(Collection<UserRecipient> users) throws QuestionReplyException {
    try {
      // Get default resource bundle
      String resource = "org.silverpeas.questionReply.multilang.questionReplyBundle";
      LocalizationBundle message;

      Map<String, SilverpeasTemplate> templates = new HashMap<>();
      NotificationMetaData notifMetaData =
          new NotificationMetaData(NotificationParameters.PRIORITY_NORMAL, subject, templates, "question");

      List<String> languages = DisplayI18NHelper.getLanguages();
      for (String language : languages) {
        // initialize new resource locator
        message = ResourceLocator.getLocalizationBundle(resource, language);
        // Create a new silverpeas template
        SilverpeasTemplate template = loadTemplate();
        template.setAttribute("UserDetail", sender);
        template.setAttribute("userName", getSendername());
        template.setAttribute("QuestionDetail", question);
        template.setAttribute("questionTitle", question.getTitle());
        template.setAttribute("questionContent", question.getContent());
        template.setAttribute("silverpeasURL", question._getPermalink());
        templates.put(language, template);
        notifMetaData.addLanguage(language,
            message.getString("questionReply.notification") + componentLabel, "");

        Link link = new Link(question._getPermalink(), message.getString("questionReply.notifLinkLabel"));
        notifMetaData.setLink(link, language);
      }
      notifMetaData.setSender(sender.getId());
      notifMetaData.addUserRecipients(users);

      notifSender.notifyUser(notifMetaData);
    } catch (NotificationException e) {
      throw new QuestionReplyException(e);
    }
  }
}
