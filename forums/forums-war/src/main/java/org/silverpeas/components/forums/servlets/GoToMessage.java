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
 * FLOSS exception. You should have received a copy of the text describing
 * the FLOSS exception, and it is also available here:
 * "https://www.silverpeas.org/legal/floss_exception.html"
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.silverpeas.components.forums.servlets;

import org.silverpeas.core.util.URLUtil;
import org.silverpeas.core.web.util.servlet.GoTo;
import org.silverpeas.components.forums.url.ActionUrl;
import org.apache.commons.lang3.CharEncoding;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

import static org.silverpeas.components.forums.service.ForumsServiceProvider
    .getForumsService;

public class GoToMessage extends GoTo {

  private static final long serialVersionUID = -2368464620933821332L;

  @Override
  public String getDestination(String objectId, HttpServletRequest req,
      HttpServletResponse res) throws Exception {
    int forumId = Integer.parseInt(req.getParameter("ForumId"));
    String componentName = getForumsService().getForumInstanceId(forumId);
    String messageUrl = ActionUrl.getUrl("viewMessage", "viewForum", 1, Integer.parseInt(objectId),
        forumId);
    String gotoURL = URLUtil.getURL(null, componentName) + messageUrl;
    return "goto=" + URLEncoder.encode(gotoURL, CharEncoding.UTF_8);
  }
}