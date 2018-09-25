package io.sf.structure;

import java.util.Stack;

/**
 * @version 1.0
 * @description:
 * @author: 侯春兵 @Date: 15:03 2018/9/25
 */
public class TwoStackToQueue {

  /** 两个栈实现队列 FIFO */

  /**
   * 两个栈同时只有一个有数据，一个为空
   *
   * <p>poll时将一个栈的数据pop到另外一个栈 则原先栈顶的先入栈 栈底的后入栈
   */
  private static Stack<Object> stack1 = new Stack<>();

  private static Stack<Object> stack2 = new Stack<>();

  public static Object offer(Object item) {
    if (!stack2.isEmpty()) {
      while (!stack2.isEmpty()) {
        stack1.push(stack2.pop());
      }
    }
    return stack1.push(item);
  }

  public static Object poll() {
    if (stack2.isEmpty()) {
      while (!stack1.isEmpty()) {
        stack2.push(stack1.pop());
      }
    }
    return stack2.pop();
  }

  public static boolean isEmpty() {
    return stack1.isEmpty() && stack2.isEmpty();
  }

  public static void main(String[] args) {
    TwoStackToQueue.offer(1);
    TwoStackToQueue.offer(2);
    TwoStackToQueue.offer(3);
    TwoStackToQueue.poll();
    TwoStackToQueue.offer(4);
    while (!TwoStackToQueue.isEmpty()) {
      System.out.println(TwoStackToQueue.poll());
    }
  }
}
