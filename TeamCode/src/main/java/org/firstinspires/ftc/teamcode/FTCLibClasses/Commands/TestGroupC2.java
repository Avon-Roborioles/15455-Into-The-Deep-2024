package org.firstinspires.ftc.teamcode.FTCLibClasses.Commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.TestGroupS1;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.TestGroupS2;

public class TestGroupC2 extends CommandBase {
    private TestGroupS2 testGroupS2;

    public TestGroupC2(TestGroupS2 testGroupS2) {
        this.testGroupS2 = testGroupS2;
    }

    @Override
    public void initialize() {
        testGroupS2.runMotor();
    }

    @Override
    public void end(boolean bool) {
        testGroupS2.resetPower();
    }

    @Override
    public boolean isFinished() {
        return testGroupS2.isFinished();
    }


}
