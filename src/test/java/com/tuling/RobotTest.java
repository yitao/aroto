package com.tuling;

import com.aroto.tuling.core.Robot;
import com.aroto.tuling.core.RobotManager;
import org.junit.Test;

/**
 * Created by yitao on 2016/7/29.
 */
public class RobotTest {
    @Test
    public void testRobot(){
        Robot mu = RobotManager.mu();
        String msg = "你叫什么";
        String res = RobotManager.call(mu,msg);
        System.out.println();
        System.out.println( res );
    }
}
