package org.firstinspires.ftc.teamcode.FTCLibClasses;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.TestGroupC1;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.TestGroupC2;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.TestGroupS1;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.TestGroupS2;

public class CommandsGroup extends ParallelCommandGroup {

    public CommandsGroup(TestGroupS1 testGroupS1, TestGroupS2 testGroupS2) {
        addCommands(
            new TestGroupC1(testGroupS1),
            new TestGroupC2(testGroupS2)
        );
        addRequirements(testGroupS1, testGroupS2);
    }
}
