package org.jivesoftware.openfire.forward;

import org.dom4j.Element;
import org.dom4j.QName;
import org.xmpp.packet.Message;
import org.xmpp.packet.PacketExtension;

/**
 * @author Christian Schudt
 */
public class Forwarded extends PacketExtension {
    public Forwarded(Message message) {
        super("forwarded", "urn:xmpp:forward:0");

        Message copy = message.createCopy();

        copy.getElement().setQName(QName.get("message", "jabber:client"));

        for (Object element : copy.getElement().elements()) {
            if (element instanceof Element) {
                Element el = (Element) element;
                el.setQName(QName.get(el.getName(), "jabber:client"));
            }
        }
        element.add(copy.getElement());
    }
}
