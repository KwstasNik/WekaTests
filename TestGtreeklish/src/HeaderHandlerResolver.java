
import java.util.*;
import javax.xml.ws.handler.*;

public class HeaderHandlerResolver implements HandlerResolver {

@Override
public List<Handler> getHandlerChain(PortInfo portInfo) {
    List<Handler> handlerChain = new ArrayList<Handler>();

    HeaderHandler hh = new HeaderHandler();

    handlerChain.add(hh);

    return handlerChain;
}
}