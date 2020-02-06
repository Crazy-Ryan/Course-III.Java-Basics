package com.thoughtworks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AppTest {

  @Test
  public void bestCharge_should_use_half_promotion() {
    String result = App.bestCharge("ITEM0001 x 1,ITEM0013 x 2,ITEM0022 x 1");

    String excepted = "============= ������ϸ =============\n"
        + "���˼� x 1 = 18Ԫ\n"
        + "����� x 2 = 12Ԫ\n"
        + "��Ƥ x 1 = 8Ԫ\n"
        + "-----------------------------------\n"
        + "ʹ���Ż�:\n"
        + "ָ����Ʒ���(���˼�����Ƥ)��ʡ13Ԫ\n"
        + "-----------------------------------\n"
        + "�ܼƣ�25Ԫ\n"
        + "===================================";
    assertEquals(excepted, result);
  }

  @Test
  public void bestCharge_should_use_full_discount_promotion() {
    String result = App.bestCharge("ITEM0013 x 4,ITEM0022 x 1");

    String excepted = "============= ������ϸ =============\n"
        + "����� x 4 = 24Ԫ\n"
        + "��Ƥ x 1 = 8Ԫ\n"
        + "-----------------------------------\n"
        + "ʹ���Ż�:\n"
        + "��30��6Ԫ��ʡ6Ԫ\n"
        + "-----------------------------------\n"
        + "�ܼƣ�26Ԫ\n"
        + "===================================";
    assertEquals(excepted, result);
  }

  @Test
  public void bestCharge_should_not_use_promotion() {
    String result = App.bestCharge("ITEM0013 x 4");

    String excepted = "============= ������ϸ =============\n"
        + "����� x 4 = 24Ԫ\n"
        + "-----------------------------------\n"
        + "�ܼƣ�24Ԫ\n"
        + "===================================";
    assertEquals(excepted, result);
  }
}
