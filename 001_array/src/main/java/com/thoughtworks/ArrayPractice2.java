package com.thoughtworks;

import java.util.Arrays;

public class ArrayPractice2 {

  /**
   * �Ѹ��������е����ֵ���浽�����еĵ�1��Ԫ����ԭ��һ�����������ֵ��λ��
   */
  public static int[] exchange() {
    int[] array = new int[]{10, 8, 1, 7, 0, 20, 16, 19};
    int maxValue = 0;
    int maxIndex = 0;
    int swap = 0;
    for (int i = 0, len = array.length; i < len; i++) {
      if (array[i] > maxValue) {
        maxIndex = i;
        maxValue = array[i];
      }
    }
    swap = array[0];
    array[0] = maxValue;
    array[maxIndex] = swap;
    return array;
  }
}
