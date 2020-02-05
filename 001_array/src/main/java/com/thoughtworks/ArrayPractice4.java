package com.thoughtworks;

import java.util.Arrays;

public class ArrayPractice4 {

  /**
   * ��ɱ�����ʵ�ֹ���: ��һ�����ֲ��뵽�������Ѿ��ź���������У�Ҫ������Ժ�������Ȼ�������
   */
  public static int[] insert(int number) {
    int[] array = new int[]{1, 20, 50, 100};
    int[] result = new int[array.length + 1];
    int curIndex = Math.abs(Arrays.binarySearch(array, number));
    if (curIndex > 1) {
      System.arraycopy(array, 0, result, 0, curIndex - 1);
    }
    result[curIndex - 1] = number;
    if (curIndex < array.length) {
      System.arraycopy(array, curIndex - 1, result, curIndex, array.length - curIndex + 1);
    }
    return result;
  }
}
