package sansam.io.lock;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;


/**
 * @version 1.0
 * @description:
 * @author: 侯春兵
 * @Date: 16:09 2018/9/21
 */
public class CuratorTest {

  public static void main(String[] args) {
          String zkAddr = "192.168.3.200:2181";
          String lockPath = "/distribute-lock";
          RetryPolicy retryPolicy = new ExponentialBackoffRetry(200000, 3);
          CuratorFramework cf = CuratorFrameworkFactory.builder()
                  .connectString(zkAddr)
                  .sessionTimeoutMs(200000)
                  .retryPolicy(retryPolicy)
                  .build();
          cf.start();

          InterProcessMutex lock = new InterProcessMutex(cf, lockPath);
          new Thread("thread-1"){
              @Override
              public void run() {
                  process(lock);
              }
          }.start();
          new Thread("thread-2"){
              @Override
              public void run() {
                  process(lock);
              }
          }.start();
      }

      private static void process(InterProcessLock lock) {
          System.out.println(Thread.currentThread().getName() + " acquire");
          try {
              lock.acquire();
              System.out.println(Thread.currentThread().getName() + " acquire success");
          } catch (Exception e) {
              e.printStackTrace();
          } finally {
              System.out.println(Thread.currentThread().getName() + " release");
              try {
                  lock.release();
              } catch (Exception e) {
                  e.printStackTrace();
              }
          }
          System.out.println(Thread.currentThread().getName() + " release success");
      }


}
