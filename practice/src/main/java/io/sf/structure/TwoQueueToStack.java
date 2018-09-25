package io.sf.structure;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @version 1.0
 * @description:
 * @author: 侯春兵 @Date: 15:25 2018/9/25
 */
public class TwoQueueToStack {

  /** 两个队列实现栈 FILO */

  /**
   * 两个队列同时只有一个有数据，一个为空
   *
   * <p>pop时 先将一个队列的length-1个数据全部offer到空的队列 此队列只剩一个数据 即最后入列的数据 再弹出
   */

  /**
   * queue队列操作
   *
   * <p>offer，add区别：
   *
   * <p>一些队列有大小限制，因此如果想在一个满的队列中加入一个新项，多出的项就会被拒绝。
   *
   * <p>这时新的 offer 方法就可以起作用了。它不是对调用 add() 方法抛出一个 unchecked 异常，而只是得到由 offer() 返回的 false。
   *
   * <p>poll，remove区别：
   *
   * <p>remove() 和 poll() 方法都是从队列中删除第一个元素。remove() 的行为与 Collection 接口的版本相似，
   *
   * <p>但是新的 poll() 方法在用空集合调用时不是抛出异常，只是返回 null。因此新的方法更适合容易出现异常条件的情况。
   *
   * <p>peek，element区别：
   *
   * <p>element() 和 peek() 用于在队列的头部查询元素。与 remove() 方法类似，在队列为空时， element() 抛出一个异常，而 peek() 返回 null
   */
  private static Queue<Object> queue1 = new ConcurrentLinkedQueue<>();

  private static Queue<Object> queue2 = new ConcurrentLinkedQueue<>();

  public static Object push(Object o) {
    if (queue1.isEmpty()) {
      while (!queue2.isEmpty()) {
        queue1.offer(queue2.poll());
      }
      return queue1.offer(o);
    } else {
      while (!queue1.isEmpty()) {
        queue2.offer(queue1.poll());
      }
      return queue2.offer(o);
    }
  }

  public static Object pop() {
    if (queue2.isEmpty()) {
      while (queue1.size() > 1) {
        queue2.offer(queue1.poll());
      }
      return queue1.poll();
    } else {
      while (queue2.size() > 1) {
        queue1.offer(queue2.poll());
      }
      return queue2.poll();
    }
  }

  public static boolean isEmpty() {
    return queue1.isEmpty() && queue2.isEmpty();
  }

  public static void main(String[] args) {
    //
    TwoQueueToStack.push(1);
    TwoQueueToStack.push(2);
    TwoQueueToStack.pop();
    TwoQueueToStack.push(3);
    TwoQueueToStack.push(4);
    TwoQueueToStack.pop();

    while (!TwoQueueToStack.isEmpty()) {
      System.out.println(TwoQueueToStack.pop());
    }
  }
}
