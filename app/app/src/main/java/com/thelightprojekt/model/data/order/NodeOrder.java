package com.thelightprojekt.model.data.order;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "order_rows")
public class NodeOrder {
    @Attribute(name = "nodeType", required = false)
    private String nodeType;

    @Attribute(name = "virtualEntity", required = false)
    private String virtualEntity;

    @ElementList(entry = "order_row", inline=true)
    private List<OrderRow> orderRows;

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getVirtualEntity() {
        return virtualEntity;
    }

    public void setVirtualEntity(String virtualEntity) {
        this.virtualEntity = virtualEntity;
    }

    public List<OrderRow> getOrderRows() {
        return orderRows;
    }

    public void setOrderRows(List<OrderRow> orderRows) {
        this.orderRows = orderRows;
    }
}
