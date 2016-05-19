package DistributeDataBase.client.controller;

import java.util.ArrayList;
import java.util.List;

public class OrderByMessagesImp
  implements OrderByMessages
{
  private final List<OrderByEle> orderbyList = new ArrayList();

  public OrderByMessagesImp(List<OrderByEle> orderbyList)
  {
    if (orderbyList != null)
      for (OrderByEle ele : orderbyList)
      {
        this.orderbyList.add(ele);
      }
  }

  public List<OrderByEle> getOrderbyList()
  {
    return this.orderbyList;
  }
}