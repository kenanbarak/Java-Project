package presenter;

/**
 * defines a command interface
 * @author Barak Kenan
 */
public interface Command
{
	/**
	 * defines what each command should do
	 * @param string with parameters
	 */
	public void doCommand();
}
