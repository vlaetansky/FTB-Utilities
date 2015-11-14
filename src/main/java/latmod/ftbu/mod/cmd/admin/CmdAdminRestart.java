package latmod.ftbu.mod.cmd.admin;

import java.io.File;

import ftb.lib.FTBLib;
import latmod.ftbu.cmd.*;
import latmod.lib.LMFileUtils;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.IChatComponent;

public class CmdAdminRestart extends CommandLM
{
	public CmdAdminRestart(String s)
	{ super(s, CommandLevel.OP); }
	
	public IChatComponent onCommand(ICommandSender ics, String[] args)
	{ restart(); return null; }
	
	public static void restart()
	{
		LMFileUtils.newFile(new File(FTBLib.folderMinecraft, "autostart.stamp"));
		FTBLib.getServer().initiateShutdown();
	}
}