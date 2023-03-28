package com.example.modeling_tools.service;

/**
 * Service used for generating HTML bodies for emails.
 */
public interface EmailBuilder {
    enum Crud {
        stored,
        deleted
    }

    default String buildEmail(String json, String feedback) {
        return String.format(
                """
                  <div style="font-family:Helvetica, Arial, sans-serif;font-size:16px;margin:0;color:#0b0c0c;">
                    <span style="display:none;font-size:1px;color:#fff;max-height:0;"></span>
                        
                    <table role="presentation" width="100%%"
                      style="border-collapse:collapse;min-width:100%%;width:100%%!important;" cellpadding="0" cellspacing="0"
                      border="0">
                      <tbody>
                        <tr>
                          <td width="100%%" height="53" bgcolor="#0b0c0c">
                        
                            <table role="presentation" width="100%%" style="border-collapse:collapse;max-width:580px;"
                              cellpadding="0" cellspacing="0" border="0" align="center">
                              <tbody>
                                <tr>
                                  <td width="70" bgcolor="#0b0c0c" valign="middle">
                                    <table role="presentation" cellpadding="0" cellspacing="0" border="0"
                                      style="border-collapse:collapse;">
                                      <tbody>
                                        <tr>
                                          <td style="padding-left:10px;">
                        
                                          </td>
                                          <td style="font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px;">
                                            <span
                                              style="font-family:Helvetica, Arial, sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block;">
                                              Modeling Tool Suggestion
                                            </span>
                                          </td>
                                        </tr>
                                      </tbody>
                                    </table>
                        
                                  </td>
                                </tr>
                              </tbody>
                            </table>
                        
                          </td>
                        </tr>
                      </tbody>
                    </table>
                    <table role="presentation" align="center" cellpadding="0" cellspacing="0" border="0"
                      style="border-collapse:collapse;max-width:580px;width:100%%!important;" width="100%%"
                      class="yiv5748049755m_-6186904992287805515content">
                      <tbody>
                        <tr>
                          <td width="10" height="10" valign="middle"></td>
                          <td>
                        
                            <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0"
                              style="border-collapse:collapse;">
                              <tbody>
                                <tr>
                                  <td bgcolor="#1D70B8" width="100%%" height="10"></td>
                                </tr>
                              </tbody>
                            </table>
                        
                          </td>
                          <td width="10" valign="middle" height="10"></td>
                        </tr>
                      </tbody>
                    </table>
                        
                        
                        
                    <table role="presentation" align="center" cellpadding="0" cellspacing="0" border="0"
                      style="border-collapse:collapse;max-width:580px;width:100%%!important;" width="100%%"
                      class="yiv5748049755m_-6186904992287805515content">
                      <tbody>
                        <tr>
                          <td height="30"><br></td>
                        </tr>
                        <tr>
                          <td width="10" valign="middle"><br></td>
                          <td
                            style="font-family:Helvetica, Arial, sans-serif;font-size:19px;line-height:1.315789474;max-width:560px;">
                        
                            <p style="Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c;">
                              Thank you for your contribution!
                            </p>
                            <p style="Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c;">
                              Below is the modeling tool that You have suggested to us.
                            </p>
                            %s
                            %s
                            <p>
                              Our team will evaluate your modeling tool suggestion and display it on the webpage once
                              we have successfully verified the modeling tool details.
                            </p>
                            <p>With kind regards,</p>
                            Business Informatics Group
                          </td>
                          <td width="10" valign="middle"><br></td>
                        </tr>
                        <tr>
                          <td height="30"><br></td>
                        </tr>
                      </tbody>
                    </table>
                    <div class="yiv5748049755yj6qo"></div>
                    <div class="yiv5748049755adL">
                        
                    </div>
                  </div>
                  """
                , json, feedback);
    }

    default String buildAdminEmail(String confirmLink, String json, String userEmail, String feedback, String deleteLink) {
        return String.format(
                """
                <div style="font-family:Helvetica, Arial, sans-serif;font-size:16px;margin:0;color:#0b0c0c;">
                    <span style="display:none;font-size:1px;color:#fff;max-height:0;"></span>
        
                    <table role="presentation" width="100%%"
                           style="border-collapse:collapse;min-width:100%%;width:100%%!important;" cellpadding="0" cellspacing="0"
                           border="0">
                      <tbody>
                      <tr>
                        <td width="100%%" height="53" bgcolor="#0b0c0c">
        
                          <table role="presentation" width="100%%" style="border-collapse:collapse;max-width:580px;"
                                 cellpadding="0" cellspacing="0" border="0" align="center">
                            <tbody>
                            <tr>
                              <td width="70" bgcolor="#0b0c0c" valign="middle">
                                <table role="presentation" cellpadding="0" cellspacing="0" border="0"
                                       style="border-collapse:collapse;">
                                  <tbody>
                                  <tr>
                                    <td style="padding-left:10px;">
        
                                    </td>
                                    <td style="font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px;">
                                                    <span
                                                      style="font-family:Helvetica, Arial, sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block;">
                                                      Modeling Tool Suggestion
                                                    </span>
                                    </td>
                                  </tr>
                                  </tbody>
                                </table>
        
                              </td>
                            </tr>
                            </tbody>
                          </table>
        
                        </td>
                      </tr>
                      </tbody>
                    </table>
                    <table role="presentation" align="center" cellpadding="0" cellspacing="0" border="0"
                           style="border-collapse:collapse;max-width:580px;width:100%%!important;" width="100%%"
                           class="yiv5748049755m_-6186904992287805515content">
                      <tbody>
                      <tr>
                        <td width="10" height="10" valign="middle"></td>
                        <td>
        
                          <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0"
                                 style="border-collapse:collapse;">
                            <tbody>
                            <tr>
                              <td bgcolor="#1D70B8" width="100%%" height="10"></td>
                            </tr>
                            </tbody>
                          </table>
        
                        </td>
                        <td width="10" valign="middle" height="10"></td>
                      </tr>
                      </tbody>
                    </table>
        
        
        
                    <table role="presentation" align="center" cellpadding="0" cellspacing="0" border="0"
                           style="border-collapse:collapse;max-width:580px;width:100%%!important;" width="100%%"
                           class="yiv5748049755m_-6186904992287805515content">
                      <tbody>
                      <tr>
                        <td height="30"><br></td>
                      </tr>
                      <tr>
                        <td width="10" valign="middle"><br></td>
                        <td
                          style="font-family:Helvetica, Arial, sans-serif;font-size:19px;line-height:1.315789474;max-width:560px;">
        
                          <p style="Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c;">
                            <a href="mailto: %s">%s</a> made the following modeling tool suggestion:
                          </p>
                          %s
                          %s
                          <p>
                            Please check if the provided data is correct. If so, you can add it to the other modeling tools by
                            clicking on "Confirm" link below.
                          </p>
                          <blockquote
                            style="Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px;">
                            <p style="Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c;"> <a
                              rel="nofollow noopener noreferrer" target="_blank"
                              onclick="return window.theMainWindow.showLinkWarning(this)"
                              href="%s">Confirm
                            </a> </p>
                          </blockquote>
                          <p>
                            If you wish to delete the suggestion from the system, click on the "Delete" link below
                          </p>
                          <blockquote
                            style="Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px;">
                            <p style="Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c;"> <a
                              rel="nofollow noopener noreferrer" target="_blank"
                              onclick="return window.theMainWindow.showLinkWarning(this)"
                              href="%s">Delete
                            </a> </p>
                          </blockquote>
                          <p>With kind regards,</p>
                          Business Informatics Group
                        </td>
                        <td width="10" valign="middle"><br></td>
                      </tr>
                      <tr>
                        <td height="30"><br></td>
                      </tr>
                      </tbody>
                    </table>
                    <div class="yiv5748049755yj6qo"></div>
                    <div class="yiv5748049755adL">
        
                    </div>
                  </div>
                """, userEmail, userEmail, json, feedback, confirmLink, deleteLink);
    }

    default String buildFeedbackEmail(String json, String userEmail, String feedback) {
        return String.format(
                """
                <div style="font-family:Helvetica, Arial, sans-serif;font-size:16px;margin:0;color:#0b0c0c;">
                    <span style="display:none;font-size:1px;color:#fff;max-height:0;"></span>
        
                    <table role="presentation" width="100%%"
                           style="border-collapse:collapse;min-width:100%%;width:100%%!important;" cellpadding="0" cellspacing="0"
                           border="0">
                      <tbody>
                      <tr>
                        <td width="100%%" height="53" bgcolor="#0b0c0c">
        
                          <table role="presentation" width="100%%" style="border-collapse:collapse;max-width:580px;"
                                 cellpadding="0" cellspacing="0" border="0" align="center">
                            <tbody>
                            <tr>
                              <td width="70" bgcolor="#0b0c0c" valign="middle">
                                <table role="presentation" cellpadding="0" cellspacing="0" border="0"
                                       style="border-collapse:collapse;">
                                  <tbody>
                                  <tr>
                                    <td style="padding-left:10px;">
        
                                    </td>
                                    <td style="font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px;">
                                                    <span
                                                      style="font-family:Helvetica, Arial, sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block;">
                                                      Modeling Tool Suggestion
                                                    </span>
                                    </td>
                                  </tr>
                                  </tbody>
                                </table>
        
                              </td>
                            </tr>
                            </tbody>
                          </table>
        
                        </td>
                      </tr>
                      </tbody>
                    </table>
                    <table role="presentation" align="center" cellpadding="0" cellspacing="0" border="0"
                           style="border-collapse:collapse;max-width:580px;width:100%%!important;" width="100%%"
                           class="yiv5748049755m_-6186904992287805515content">
                      <tbody>
                      <tr>
                        <td width="10" height="10" valign="middle"></td>
                        <td>
        
                          <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" border="0"
                                 style="border-collapse:collapse;">
                            <tbody>
                            <tr>
                              <td bgcolor="#1D70B8" width="100%%" height="10"></td>
                            </tr>
                            </tbody>
                          </table>
        
                        </td>
                        <td width="10" valign="middle" height="10"></td>
                      </tr>
                      </tbody>
                    </table>
        
        
        
                    <table role="presentation" align="center" cellpadding="0" cellspacing="0" border="0"
                           style="border-collapse:collapse;max-width:580px;width:100%%!important;" width="100%%"
                           class="yiv5748049755m_-6186904992287805515content">
                      <tbody>
                      <tr>
                        <td height="30"><br></td>
                      </tr>
                      <tr>
                        <td width="10" valign="middle"><br></td>
                        <td
                          style="font-family:Helvetica, Arial, sans-serif;font-size:19px;line-height:1.315789474;max-width:560px;">
        
                          <p style="Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c;">
                            <a href="mailto: %s">%s</a> provided feedback to the following modeling tool:
                          </p>
                          %s
                          %s
                          <p>With kind regards,</p>
                          Business Informatics Group
                        </td>
                        <td width="10" valign="middle"><br></td>
                      </tr>
                      <tr>
                        <td height="30"><br></td>
                      </tr>
                      </tbody>
                    </table>
                    <div class="yiv5748049755yj6qo"></div>
                    <div class="yiv5748049755adL">
        
                    </div>
                  </div>
                """, userEmail, userEmail, json, feedback);
    }

    default String buildFeedback(String feedback) {
        if (feedback == null || feedback.isEmpty()) {
            return "";
        }
        return String.format(
                """
                <div style="background: lightgrey; padding: 1em; border-radius: 20px;">
                <h5 style="margin: 0 0 0.5em 0;">Additional remarks</h5>
                <p style="margin-top: 0; font-size: 14px;">%s
                </p>
                </div>
                """, feedback
        );
    }

    default String buildComparisonEmail(String originalJson, String newJson) {
        return String.format(
                """
                <p style="margin:0 0 20px 0;font-size:19px;line-height:25px;font-weight: bold;color:#0b0c0c;background: #A3E5FC;padding: 1rem;border-radius: 14px;text-align: center;">Original modeling tool</p>
                %s
                <p style="margin:0 0 20px 0;font-size:19px;line-height:25px;font-weight: bold;color:#0b0c0c;background: #FFFF33;padding: 1rem;border-radius: 14px;text-align: center;">New modeling tool</p>
                %s
                """,
                originalJson,
                newJson
        );
    }

    default String build200ResponsePage(String name) {
        return String.format(
                """
                                <div style="font-family:Helvetica, Arial, sans-serif;font-size:16px;margin:0;color:#0b0c0c;">
                                  <span style="display:none;font-size:1px;color:#fff;max-height:0;"></span>
                                                
                                  <header style="margin-bottom: 2rem;">
                                    <div style="align-items: center; background: #0f385c;color: #FFFFFF;
                                    display: grid; font-size: 1.25rem; gap: 2rem; font-weight: bold;
                                    justify-content: center;padding: 1rem 2rem;">
                                      Modeling Tool Suggestion successfully deleted
                                    </div>
                                    <div style="background: #1d70b8; margin: 0 auto;
                                    width: 50%%; color: #FFF; text-align: center; padding: 0.5rem 0; font-weight: bold">
                                      Status: 200 OK
                                    </div>
                                  </header>
                                                
                                  <table role="presentation" align="center" cellpadding="0" cellspacing="0" border="0"
                                         style="border-collapse:collapse;max-width:580px;width:100%%!important;" width="100%%"
                                         class="yiv5748049755m_-6186904992287805515content">
                                    <tbody>
                                    <tr>
                                      <td height="30"><br></td>
                                    </tr>
                                    <tr>
                                      <td width="10" valign="middle"><br></td>
                                      <td
                                        style="font-family:Helvetica, Arial, sans-serif;font-size:19px;line-height:1.315789474;max-width:560px;">
                                                
                                        <p style="Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c;">
                                          The Modeling Tool %s has been successfully removed.
                                        </p>
                                        <p>
                                          Return to the <a href="http://localhost:4200">homepage</a>.
                                        </p>
                                                
                                        <p>With kind regards,</p>
                                        Business Informatics Group
                                      </td>
                                      <td width="10" valign="middle"><br></td>
                                    </tr>
                                    <tr>
                                      <td height="30"><br></td>
                                    </tr>
                                    </tbody>
                                  </table>
                                  <div class="yiv5748049755yj6qo"></div>
                                  <div class="yiv5748049755adL">
                                                
                                  </div>
                                </div>
                        """, name
        );
    }

    default String build201ResponsePage(Long id, String name, String json) {
        return String.format(
                """
                                <div style="font-family:Helvetica, Arial, sans-serif;font-size:16px;margin:0;color:#0b0c0c;">
                                  <span style="display:none;font-size:1px;color:#fff;max-height:0;"></span>
                                                
                                  <header style="margin-bottom: 2rem;">
                                    <div style="align-items: center; background: #1e854f;color: #FFFFFF;
                                    display: grid; font-size: 1.25rem; gap: 2rem; font-weight: bold;
                                    justify-content: center;padding: 1rem 2rem;">
                                      Modeling Tool Suggestion successfully stored
                                    </div>
                                    <div style="background: #32de84; margin: 0 auto;
                                    width: 50%%; color: #FFF; text-align: center; padding: 0.5rem 0; font-weight: bold">
                                      Status: 201 CREATED
                                    </div>
                                  </header>
                                                
                                  <table role="presentation" align="center" cellpadding="0" cellspacing="0" border="0"
                                         style="border-collapse:collapse;max-width:580px;width:100%%!important;" width="100%%"
                                         class="yiv5748049755m_-6186904992287805515content">
                                    <tbody>
                                    <tr>
                                      <td height="30"><br></td>
                                    </tr>
                                    <tr>
                                      <td width="10" valign="middle"><br></td>
                                      <td
                                        style="font-family:Helvetica, Arial, sans-serif;font-size:19px;line-height:1.315789474;max-width:560px;">
                                                
                                        <p style="Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c;">
                                          The Modeling Tool <a href="http://localhost:4200/modeling_tool/edit/%s">%s</a> has been successfully stored.
                                        </p>
                                        %s
                                        <p>
                                          Return to the <a href="http://localhost:4200">homepage</a>.
                                        </p>
                                                
                                        <p>With kind regards,</p>
                                        Business Informatics Group
                                      </td>
                                      <td width="10" valign="middle"><br></td>
                                    </tr>
                                    <tr>
                                      <td height="30"><br></td>
                                    </tr>
                                    </tbody>
                                  </table>
                                  <div class="yiv5748049755yj6qo"></div>
                                  <div class="yiv5748049755adL">
                                                
                                  </div>
                                </div>
                        """, id, name, json
        );
    }

    default String build401ResponsePage(Crud crud) {
        return String.format(
                """
                    <div style="font-family:Helvetica, Arial, sans-serif;font-size:16px;margin:0;color:#0b0c0c;">
                      <span style="display:none;font-size:1px;color:#fff;max-height:0;"></span>
                                    
                      <header style="margin-bottom: 2rem;">
                        <div style="align-items: center; background: #980000;color: #FFFFFF;
                        display: grid; font-size: 1.25rem; gap: 2rem; font-weight: bold;
                        justify-content: center;padding: 1rem 2rem;">
                          Modeling Tool Suggestion not %s
                        </div>
                        <div style="background: #FF0000; margin: 0 auto;
                        width: 50%%; color: #FFFFFF; text-align: center; padding: 0.5rem 0; font-weight: bold">
                          Status: 401 UNAUTHORIZED
                        </div>
                      </header>
                                    
                      <table role="presentation" align="center" cellpadding="0" cellspacing="0" border="0"
                             style="border-collapse:collapse;max-width:580px;width:100%%!important;" width="100%%"
                             class="yiv5748049755m_-6186904992287805515content">
                        <tbody>
                        <tr>
                          <td height="30"><br></td>
                        </tr>
                        <tr>
                          <td width="10" valign="middle"><br></td>
                          <td
                            style="font-family:Helvetica, Arial, sans-serif;font-size:19px;line-height:1.315789474;max-width:560px;">
                                    
                            <p style="Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c;">
                              Modeling Tool Suggestion could not be found and thus could not be %s.
                            </p>
                            <p>
                              Return to the <a href="http://localhost:4200">homepage</a>.
                            </p>
                                    
                            <p>With kind regards,</p>
                            Business Informatics Group
                          </td>
                          <td width="10" valign="middle"><br></td>
                        </tr>
                        <tr>
                          <td height="30"><br></td>
                        </tr>
                        </tbody>
                      </table>
                      <div class="yiv5748049755yj6qo"></div>
                      <div class="yiv5748049755adL">
                                    
                      </div>
                    </div>
                        """, crud, crud
        );
    }
}
