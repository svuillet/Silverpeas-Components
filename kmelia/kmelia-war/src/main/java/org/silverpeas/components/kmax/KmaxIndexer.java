/*
 * Copyright (C) 2000 - 2017 Silverpeas
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * As a special exception to the terms and conditions of version 3.0 of
 * the GPL, you may redistribute this Program in connection with Free/Libre
 * Open Source Software ("FLOSS") applications as described in Silverpeas's
 * FLOSS exception.  You should have received a copy of the text describing
 * the FLOSS exception, and it is also available here:
 * "http://www.silverpeas.org/docs/core/legal/floss_exception.html"
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.silverpeas.components.kmax;

import org.silverpeas.core.web.index.components.ComponentIndexation;
import org.silverpeas.core.admin.component.model.ComponentInst;
import org.silverpeas.components.kmelia.service.KmeliaService;
import org.silverpeas.core.contribution.publication.service.PublicationService;
import org.silverpeas.core.contribution.publication.model.PublicationDetail;
import org.silverpeas.core.contribution.publication.model.PublicationPK;
import org.silverpeas.core.contribution.attachment.AttachmentService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Collection;

@Singleton
@Named("kmax" + ComponentIndexation.QUALIFIER_SUFFIX)
public class KmaxIndexer implements ComponentIndexation {

  @Inject
  private AttachmentService attachmentService;
  @Inject
  private PublicationService publicationService;
  @Inject
  private KmeliaService kmeliaService;

  @Override
  public void index(ComponentInst componentInst) throws Exception {
    kmeliaService.indexKmax(componentInst.getId());

    Collection<PublicationDetail> publications =
        publicationService.getAllPublications(new PublicationPK("useless", componentInst.getId()),
            "pubId desc");
    for (PublicationDetail aPublication : publications) {
      attachmentService.indexAllDocuments(aPublication.getPK(), null, null);
    }
  }
}