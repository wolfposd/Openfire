/**
 * $Revision$
 * $Date$
 *
 * Copyright (C) 2006 Jive Software. All rights reserved.
 *
 * This software is published under the terms of the GNU Public License (GPL),
 * a copy of which is included in this distribution.
 */

package org.jivesoftware.wildfire.gateway.protocols.simple;

import javax.sip.ListeningPoint;
import javax.sip.SipFactory;
import org.jivesoftware.util.LocaleUtils;
import org.jivesoftware.wildfire.gateway.BaseTransport;
import org.jivesoftware.wildfire.gateway.PresenceType;
import org.jivesoftware.wildfire.gateway.Registration;
import org.jivesoftware.wildfire.gateway.TransportSession;
import org.xmpp.packet.JID;

/**
 * A transport implementation for SIMPLE protocol.
 * @author  Patrick Siu
 * @version 0.0.1
 */
public class SimpleTransport extends BaseTransport {
	SipFactory sipFactory = null;
	
	public SimpleTransport() {
		super();
		
		// Initialize the SipFactory
		sipFactory = SipFactory.getInstance();
		sipFactory.setPathName("gov.nist");
	}
	
	public TransportSession registrationLoggedIn(Registration registration, JID jid, PresenceType presenceType, String verboseStatus, Integer priority) {
		TransportSession session = new SimpleSession(registration, jid, this, priority); 
		
		// Possibly more work here!
		((SimpleSession) session).login(presenceType, verboseStatus);
		
		return session;
	}

	public void registrationLoggedOut(TransportSession session) {
		((SimpleSession) session).logout();
		
		session.sessionDone();
		
        // Just in case.
//		session.setLoginStatus(TransportLoginStatus.LOGGED_OUT);
	}

	public String getTerminologyUsername() {
		return LocaleUtils.getLocalizedString("gateway.sip.username", "gateway");
	}

	public String getTerminologyPassword() {
		return LocaleUtils.getLocalizedString("gateway.sip.password", "gateway");
	}

	public String getTerminologyNickname() {
		// If this string is needed, then take it.  Just put a draft code to ensure integrity.

        return null;
	}

	public String getTerminologyRegistration() {
		return LocaleUtils.getLocalizedString("gateway.sip.registration", "gateway");
	}

	public Boolean isPasswordRequired() {
		// Just put a draft code to ensure integrity.

        return true;
	}

	public Boolean isNicknameRequired() {
		// Just put a draft code to ensure integrity.

        return false;
	}

	public Boolean isUsernameValid(String username) {
		// Just put a draft code to ensure integrity.

        return username.matches("\\w+");
	}
	
	
	// The following code are generic custom classes for SIP-XMPP conversion.
	public void convertJabStatusToSIP(PresenceType jabStatus) {
	}
	
	public void convertSIPStatusToJap() {
	}
	
	
	/**
	 * An improved method to do the trick.
	 */
	public String convertJIDToID(JID jid) {
		String node = jid.getNode();
		while (!JID.unescapeNode(node).equals(node)) {
			node = JID.unescapeNode(node);
		}
		return node;
	}
	
	
	int portOffset = 0;
	synchronized int generateListenerPort() {
		return (ListeningPoint.PORT_5060 + (++portOffset));
	}
}