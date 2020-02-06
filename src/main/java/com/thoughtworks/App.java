package com.thoughtworks;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.ArrayList;


public class App {
  private static Maps maps = getMaps();
  private static HashMap<String, String> idNameMap = maps.idNameMap;
  private static HashMap<String, Double> idPriceMap = maps.idPriceMap;

  public void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    System.out.println("���ͣ���ƷId x �������ö��Ÿ�������");
    String selectedItems = scan.nextLine();
    String summary = bestCharge(selectedItems);
    System.out.println(summary);
  }

  public static String bestCharge(String selectedItems) {
    String[] itemsArray = selectedItems.split(",");
    CostDetails[] costDetailsArray = new CostDetails[itemsArray.length];
    double totalCost = 0;

    for (int i = 0, len = itemsArray.length; i < len; i++) {
      String[] idAndCount = itemsArray[i].split(" x ");
      CostDetails curCostDetails = new CostDetails(idAndCount[0], idAndCount[1]);
      costDetailsArray[i] = curCostDetails;
      totalCost += curCostDetails.price * curCostDetails.count;
    }

    String promotionInfo = "";
    double finalCost = totalCost;
    for (Promotion promotion : getPromotions()) {
      PromoDetails promoDetails = promotion.getPromoDetails(totalCost, costDetailsArray);
      if (promoDetails.cost < finalCost) {
        finalCost = promoDetails.cost;
        promotionInfo = promoDetails.info;
      }
    }

    return renderDetails(costDetailsArray) + renderPromo(promotionInfo) + renderFooter(finalCost);
  }

  private static String renderDetails(CostDetails[] costDetailsList) {
    StringBuilder detailsString = new StringBuilder("============= ������ϸ =============\n");
    for (CostDetails item : costDetailsList) {
      detailsString.append(MessageFormat.format("{0} x {1} = {2}Ԫ\n", item.name, item.count, item.price * item.count));
    }
    detailsString.append("-----------------------------------\n");
    return detailsString.toString();
  }

  private static String renderPromo(String promotionInfo) {
    if (!promotionInfo.equals("")) {
      return MessageFormat.format("ʹ���Ż�:\n{0}\n-----------------------------------\n", promotionInfo);
    } else {
      return "";
    }
  }

  private static String renderFooter(double totalCost) {
    return MessageFormat.format("�ܼƣ�{0}Ԫ\n===================================", totalCost);
  }

  private static class Maps {
    private HashMap<String, String> idNameMap;
    private HashMap<String, Double> idPriceMap;

    Maps(HashMap<String, String> idNameMap, HashMap<String, Double> idPriceMap) {
      this.idNameMap = idNameMap;
      this.idPriceMap = idPriceMap;
    }
  }

  private static Maps getMaps() {
    String[] ids = getItemIds();
    String[] names = getItemNames();
    double[] prices = getItemPrices();
    HashMap<String, String> idNameMap = new HashMap<>();
    HashMap<String, Double> idPriceMap = new HashMap<>();

    for (int i = 0, len = ids.length; i < len; i++) {
      idNameMap.put(ids[i], names[i]);
      idPriceMap.put(ids[i], prices[i]);
    }

    return new Maps(idNameMap, idPriceMap);
  }

  public static class CostDetails {
    String id;
    String name;
    double price;
    int count;

    CostDetails(String id, String count) {
      this.id = id;
      this.name = idNameMap.get(id);
      this.price = idPriceMap.get(id);
      this.count = Integer.parseInt(count);
    }
  }

  private static class PromoDetails {
    String type;
    double cost;
    String info;

    PromoDetails(String type, double cost, String info) {
      this.type = type;
      this.cost = cost;
      this.info = info;
    }
  }

  private abstract static class Promotion {
    abstract PromoDetails getPromoDetails(double fullCost, CostDetails[] costDetailsList);
  }


  private static class SimplePromotion extends Promotion {
    String type = "��30��6Ԫ";

    @Override
    PromoDetails getPromoDetails(double fullCost, CostDetails[] costDetailsList) {
      return new PromoDetails(this.type, fullCost >= 30 ? fullCost - 6 : fullCost, "��30��6Ԫ��ʡ6Ԫ");
    }
  }

  private static class HalfPricePromotion extends Promotion {
    String type = "ָ����Ʒ���";
    String[] items = getHalfPriceIds();

    @Override
    PromoDetails getPromoDetails(double fullCost, CostDetails[] costDetailsArray) {
      HashSet<String> promoSet = new HashSet<>(Arrays.asList(items));
      ArrayList<String> promoNames = new ArrayList<>();
      double discount = 0;

      for (CostDetails costDetails : costDetailsArray) {
        if (promoSet.contains(costDetails.id)) {
          promoNames.add(costDetails.name);
          discount += costDetails.price * costDetails.count / 2;
        }
      }

      return new PromoDetails(
              this.type,
              fullCost - discount,
              MessageFormat.format("ָ����Ʒ���({0})��ʡ{1}Ԫ", String.join("��", promoNames), discount));
    }
  }
  

  /**
   * ��ȡÿ����Ʒ���εı��
   */
  public static String[] getItemIds() {
    return new String[]{"ITEM0001", "ITEM0013", "ITEM0022", "ITEM0030"};
  }

  /**
   * ��ȡÿ����Ʒ���ε�����
   */
  public static String[] getItemNames() {
    return new String[]{"���˼�", "�����", "��Ƥ", "����"};
  }

  /**
   * ��ȡÿ����Ʒ���εļ۸�
   */
  public static double[] getItemPrices() {
    return new double[]{18.00, 6.00, 8.00, 2.00};
  }

  /**
   * ��ȡ��۲�Ʒ�ı��
   */
  public static String[] getHalfPriceIds() {
    return new String[]{"ITEM0001", "ITEM0022"};
  }

  /**
   * ��ȡ������ʽ
   */
  private static Promotion[] getPromotions() {
    return new Promotion[]{new SimplePromotion(), new HalfPricePromotion()};
  }
}
