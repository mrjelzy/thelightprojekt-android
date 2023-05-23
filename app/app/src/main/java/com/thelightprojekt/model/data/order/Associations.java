package com.thelightprojekt.model.data.order;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "associations")
class Associations {

    @Element(name = "order_rows")
    private NodeOrder orderRows;

}
