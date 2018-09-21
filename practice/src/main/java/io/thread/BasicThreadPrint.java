package io.thread;

import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * @version 1.0
 * @description:
 * @author: 侯春兵 @Date: 9:55 2018/9/17
 */
public class BasicThreadPrint {

  private static int max = 3;

  public static void print(String threadName) {
    int count = 0;
    while (count++ < max) {
      System.out.println(threadName + " print num " + count);
    }
  }

  /** 两个线程 A B 输入123 , A 输出完后B再输出 * 可以使用thread.join 在B线程中调用A线程 会等A线程执行完后 接着执行B线程 */
  public static void print1() {
    Thread a =
        new Thread() {
          @Override
          public void run() {
            print("a");
          }
        };
    Thread b =
        new Thread() {
          @Override
          public void run() {
            print("b");
          }
        };

    // 直接启动线程 输出可能为 a1 b1 a2 b2 a3 b3 也有可能是 a1 a2 a3 b1 b2 b3 随机事件
    a.start();
    b.start();
  }
  /** 两个线程 A B 输入123 , A 输出完后B再输出 * 可以使用thread.join 在B线程中调用A线程 会等A线程执行完后 接着执行B线程 */
  public static void print2() {
    Thread a =
        new Thread() {
          @Override
          public void run() {
            print("a");
          }
        };
    Thread b =
        new Thread() {
          @Override
          public void run() {
            try {
              System.out.println("b 等待 a 执行完毕");
              a.join();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            print("b");
          }
        };

    a.start();
    b.start();

    // b 等待 a 执行完毕 a print num 1 a print num 2 a print num 3 b print num 1 b print num 2 b print
    // num3
  }
  /** 线程1负责打印a,b,c,d 线程2负责打印1,2,3,4 要求控制台中输出的内容为 a1b2c3d4 */
  private static Object lock = new Object();
  private static boolean flag = true;
  public static void print3() {
    Thread a =
        new Thread() {
          String[] arr = {"a", "b", "c", "d"};

          @Override
          public void run() {
            for (int i = 0; i < arr.length; i++) {
              //
              synchronized (lock) {
                System.out.println("线程A获得锁成功！");
                System.out.println(arr[i]);
                lock.notifyAll();
              }
            }
          }
        };
    Thread b =
        new Thread() {
          @Override
          public void run() {
            try {
              System.out.println("b 等待 a 执行完毕");
              a.join();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            print("b");
          }
        };

    a.start();
    b.start();

    /**
     * b 等待 a 执行完毕 a print num 1 a print num 2 a print num 3 b print num 1 b print num 2 b print
     * num3
     */
  }

  public static void main(String[] args) {
    //    print1();

    print2();
  }
}
