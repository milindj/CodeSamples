package com.excite.atmsim.console;

import java.io.IOException;

import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.ShellFactory;

public class ATMConsole {

	public static final String QUICK_HELP = ("########   QUICK HELP   ########\n")
			+("##\n")
			+("##\n")
			+("## ENTER==>'?list' for help on list of commands & descriptions\n")
			+("## ENTER==>'?help <cmd>' for help on specific command\n")
			
			+("##\n")
			+("##\n")
			+("## ENTER==>'wd <amount>' to withdraw cash\n")
			+("## ENTER==>'ac' to get count of all notes of each type\n")
			+("## ENTER==>'nc <denomination>' to get count of a particular note type\n")
			+("## ENTER==>'ri <50,12;20,12;10,12;5,12>' to re-initialise ATMsim for given bundles\n")
			+("##\n")
			+("##\n")
			+("## ENTER==>'exit' to exit the application\n")
			+("##\n")
			+("## ENTER==>'help' to see this menu again \n");
	
	private ATMShellClient atmShellclient;
	
	@Command(name = "help")
	public String help(){
		return QUICK_HELP;
	}

	@Command(name = "notecount", abbrev = "nc", header = "Note-Count:")
	public String getNoteCount(@Param(name="NoteType", description="A Note Denomination")String strNoteType) {
		return this.getAtmShellclient().getCountOfGivenNote(strNoteType);
	}

	@Command(name = "allnotecount", abbrev = "ac", header = "Fetching...")
	public String getAllNoteCount() {
		return this.getAtmShellclient().getCountOfAllNotes();
	}

	@Command(name = "withdraw", abbrev = "wd", header = "Processing...")
	public String withdraw(@Param(name="amount", description="Amount to be withdrawn")String strAmount) {
		return this.getAtmShellclient().withdrawCash(strAmount);
	}

	@Command(name = "reinit", abbrev = "ri", header = "Reinitialising...")
	public String reInitATM(@Param(name="bundles", description="Follow format: " + StringBundleUtil.SAMPLE_BUNDLE_STRING)String strBundles) {
		return this.getAtmShellclient().initialiseATMFromBundle(strBundles);
	}
	
	@Command
	public void exit() { }

	public static void main(String[] args) {

		//Initialise ATM.
		ATMConsole atmConsole = new ATMConsole();
		atmConsole.setAtmShellclient(new ATMShellClient());

		System.out.println(QUICK_HELP);
		System.out.println("## Initialising deafault ATM setup ...\n");
		
		String strBundles = atmConsole.getAtmShellclient()
				.initialiseDefaultATM();
		//Display the notes available.
		System.out.println("\nThe ATM has the following Type and Quantity of Notes: \n"
						+ strBundles);

		//Let cliche manage the console.
		try {
			ShellFactory.createConsoleShell("ATM",
					" Enter ?list to get a list of commands", atmConsole)
					.commandLoop();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public ATMShellClient getAtmShellclient() {
		return atmShellclient;
	}

	public void setAtmShellclient(ATMShellClient atmShellclient) {
		this.atmShellclient = atmShellclient;
	}

}
