package com.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Business

{

    private Lock      lock       = new ReentrantLock();

    private Condition conditionA = lock.newCondition();

    private Condition conditionB = lock.newCondition();

    private Condition conditionC = lock.newCondition();

    private String    type       = "A";

    public void A()

    {

        lock.lock();

        try

        {

            while (type != "A")

            {

                try

                {

                    conditionA.await();

                } catch (InterruptedException e)

                {

                    e.printStackTrace();

                }

            }

            System.out.println(Thread.currentThread().getName() + " 正在打印A");

            type = "B";

            conditionB.signal();

        } finally

        {

            lock.unlock();

        }

    }

    public void B()

    {

        lock.lock();

        try

        {

            while (type != "B")

            {

                try

                {

                    conditionB.await();

                } catch (InterruptedException e)

                {

                    e.printStackTrace();

                }

            }

            System.out.println(Thread.currentThread().getName() + " 正在打印B");

            type = "C";

            conditionC.signal();

        } finally

        {

            lock.unlock();

        }

    }

    public void C()

    {

        lock.lock();

        try

        {

            while (type != "C")

            {

                try

                {

                    conditionC.await();

                } catch (InterruptedException e)

                {

                    e.printStackTrace();

                }

            }

            System.out.println(Thread.currentThread().getName() + " 正在打印C");

            type = "A";

            conditionA.signal();

        } finally

        {

            lock.unlock();

        }

    }

}
