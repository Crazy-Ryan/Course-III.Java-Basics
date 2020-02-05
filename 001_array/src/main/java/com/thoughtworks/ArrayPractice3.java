package com.thoughtworks;

import java.util.Arrays;

public class ArrayPractice3 {

  /**
   * ��ɱ�����ʵ�ֹ���: ��һ����������array��ֵ0��Ԫ��ȥ��������ʣ�µ�Ԫ������µ�����
   */
  public static int[] filterZero(int[] array) {
    int[] result = new int[array.length];
    int curIndex = 0;
    int itemCount = 0;
    for (int value : array) {
      if (value != 0) {
        result[curIndex] = value;
        curIndex++;
        itemCount++;
      }
    }
    return Arrays.copyOfRange(result, 0, itemCount);
  }
}
