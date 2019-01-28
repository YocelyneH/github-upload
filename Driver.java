/*-------------------------------------------------------------------------------------------------------------------
 *Course: CS 112                		Days & Time:  Tues/Thurs 1:30 - 3:20                                                                                                                                                                             
 *Chapter Number:  10            		Extra Credit RandomAccessFiles                                       
 *Programmer(s): Yocelyne Hernandez 	Last Modified:  10 /25 /2018
 *
 *Program Title: Driver
 *Program Description: This program the BankAccount class to create accounts and write or read them from a file
 *-------------------------------------------------------------------------------------------------------------------
 *
 *
 *ALGORITHM for MAIN  
 * declare variables:
 *		String fileName;
 *		int accountNum;
 *		int numAccounts = 0; //number of accounts in a file
 *		boolean again = true; 
 *
 *		RandomAccessFile file = null;
 *		Random rand = new Random();
 * Use while to loop while the user wants to add more accounts to the file
 * In while loop and loop while there are 10 accounts and try to open the random access file for reading and writing	
 * 	 Using the file length and the TOTAL_BYTES for each account calculate the number of accounts
 * 	
 * Ask user to input account number and use the findAccount method to find the account
 *		if the account number does not exist the create a unique number using Random and assign it 
 *			call the accountNotFound method
 *	if the account number does exist
 *		   call the accountFound method
 * Catch neccessary exceptions
 * 		EOFException
 * 		FileNotFoundException
 * 		IOException
 * close the file
 *
 * Use try block to try to open the file for reading
 * 		use for loop to read the file 
 * 		output the accounts to screen
 * Catch neccessary exceptions
 * 		EOFException
 * 		FileNotFoundException
 * 		IOException
 * Ask the user if they would like add more accounts
 * 		if they say no, set the again boolean to false to exit loop 
 * 		output bye message to user
 * 		
 * ALGORITHM FOR STATIC METHODS
 * addAccount:
 * 		takes in a RandomAccessFile, BankAccount obj, and int numAccounts
 * 		method will seek to where the new account will be written using the TOTAL_BYTES constant in BankAccount
 * 		call the writeToFile method in BankAccount to write to file
 * findAccount:
 *   	takes in a RandomAccessFile, account number, and number of accounts 
 *   	method will loop through the file using a for loop until it reaches the num of accounts to look through 
 *   	in for loop use the seek method to seek to the start of each account by multiplying the iterator by the TOTAL_BYTES
 *   	read the account number using the readInt method 
 *   	check if it is equal to the account number 
 *   	return the account index if it is found
 *   	else return -1 meaning it was not found
 * updateBalance:  
 *   	the update balance method will take in a RandomAccessFile, index of account and the depoist
 *   	use seek method to seek to where the balance is located for the account
 *   		seek method will use the index of the account, TOTAL_BYTES and BALANCE_OFFSET to seek
 *   	read the balance and store it
 *   	add the depoist to the balance
 *   	use the seek method to seek to where the balance is located
 *   	write the balance to the file
 *   	output the new balance
 * accountFound:
 * 	 	method will take in a RandomAccessFile, num of accounts, and account index
 * 	 	seek to the accountIndex
 * 	 	call the readFile method from BankAccount
 * 	 	output the account information
 * 	 	ask user if they want to deposit
 * 	 	if yes 
 * 			prompt to enter the deposit
 * 			call the updateBalance method
 * accountNotFound:
 *   	do
 * 	    	generate a random account number
 * 	    	check if the generated account number doesn't exist
 * 			use findAccount method 
 * 	 	loop while -1 is not returned
 *   	prompt user to enter last name and first name
 *	 	initialize by calling the full constructor with user input
 *	 	call the addAccount method to add the account to the file
 *	 	increment the number of accounts
 *   	ask user if they want to deposit
 * 	  	if yes 
 * 		 	prompt to enter the deposit
 * 		 	call the updateBalance method   
 * IMPORTED PACKAGES:
		import java.util.Scanner;   
		import java.io.*;
_____________________________________________________________________________
 */
package ExtraCredit;
import java.util.*;
import java.io.*;
public class Driver 
{
	public static void main(String []args) throws IOException
	{
		String fileName;
		int accountNum;
		int numAccounts = 0; //number of accounts in a file
		boolean again = true;
		
		Scanner keyboard= new Scanner(System.in);
		RandomAccessFile file = null;
		Random rand = new Random();
		
		System.out.println("Enter the name of the file: ");
		fileName = keyboard.nextLine();
		
		//while loop to keep adding accounts
		while(again)
		{
			try
			{	
				file = new RandomAccessFile(fileName, "rw");
				numAccounts = (int) (file.length() / BankAccount.TOTAL_BYTES);
				for(int i = 0; i < 10; i++) 
					{
						System.out.println("Enter your Account number: ");
						accountNum = keyboard.nextInt();
						keyboard.nextLine();
						
						int accountIndex = Driver.findAccount(file, accountNum, numAccounts);
						
						//case for the account not found
						if(accountIndex == -1)
						{ 
							Driver.accountNotFound(file, rand, numAccounts);
							numAccounts++;
						}
						
						//case if the account number exists
						else	
						{
							System.out.println("Account was found!");
							Driver.accountFound(file, numAccounts, accountIndex);
						}
						
					}
				
			}
			catch (EOFException e)
			{
				e.printStackTrace();
				System.out.println("End of file");
			}
			catch (FileNotFoundException e)
			{
				System.out.println("Problem opening the file.");
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			catch(Exception e)
			{
				System.out.println("error");
			}
			finally
			{
				file.close();
			}
			
			try
			{
				file = new RandomAccessFile(fileName, "r");
				BankAccount temp = new BankAccount();
				
				System.out.println("These are the accounts in the file: ");
				System.out.println("________________________________");
				
				for(int j = 0; j < numAccounts ; j++)
				{
					//file.seek(BankAccount.TOTAL_BYTES * i)
					temp.readFile(file);
					System.out.println(temp);
				}
			}
			catch(EOFException e)
			{
				System.out.print("End of the file");
			}
			catch (FileNotFoundException e)
			{
				System.out.println("Problem opening the file.");
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			catch(Exception e)
			{
				System.out.println("Exception error");
			}
			finally
			{
				file.close();
			}
			System.out.println("Would you like to add other accounts? Yes or No?");
			String answer = keyboard.nextLine();
			
			//if user enters no, while loop will exit
			if(answer.equalsIgnoreCase("no"))
			{
				again = false;
				System.out.println("Thank you. Goodbye!");
			}
			
	  }// while loop
   } // main
	
//_______________________Static methods_______________________________
/**
 * Precondition: file exists and user entered the account information
 * Postconition: account object will be written to file
 */
public static void addAccount(RandomAccessFile file, BankAccount account, int numAccounts) throws IOException
{	
	file.setLength((numAccounts) * BankAccount.TOTAL_BYTES);
	file.seek(numAccounts * BankAccount.TOTAL_BYTES);
	account.writeToFile(file);
}
/**
 * Precondition: file exists and account number is prompted to user
 * Postcondition: -1 will be returned if account does not exist and if it does exist the account index will be returned 
 */ 
public static int findAccount(RandomAccessFile file, int accountNum, int numAccounts) throws IOException
{
	int num;
	for(int i = 0; i < numAccounts; i++)
	{
		file.seek(i * BankAccount.TOTAL_BYTES);
		num = file.readInt();
		if(num == accountNum)
			{
				return i;
			}
	}
	return -1;
}

/**
 * Precondition: account exists 
 * Postcondition: balance will be updated to the file
 */
public static void updateBalance(RandomAccessFile file, int indexOfAccount, double deposit) throws IOException
{
	double balance;
	
	//seek to balance 
	file.seek((indexOfAccount * BankAccount.TOTAL_BYTES) + BankAccount.BALANCE_OFFSET);
	//read the balance
	balance = file.readDouble();
	
	balance = balance + deposit;
	//seek to balance 
	file.seek((indexOfAccount * BankAccount.TOTAL_BYTES) + BankAccount.BALANCE_OFFSET);
	file.writeDouble(balance);
	
	System.out.println("Updated balance is " + balance);
}
//method for an account that exists
public static void accountFound(RandomAccessFile file, int numAccounts, int accountIndex) throws IOException
{
	Scanner keyboard= new Scanner(System.in);
	BankAccount temp = new BankAccount();
	//seek to the account 
	file.seek(accountIndex * BankAccount.TOTAL_BYTES);
	temp.readFile(file);
	
	//show the user there account information
	System.out.println("Here is your account information:");
	System.out.println(temp);
	
	System.out.println("Would you like to deposit? Yes or No?");
	String answer = keyboard.nextLine();
	if(answer.equalsIgnoreCase("yes"))
	{
		System.out.println("Enter your deposit: ");
		double deposit = keyboard.nextDouble();
		keyboard.nextLine();
		Driver.updateBalance(file, accountIndex, deposit);
		
	}
	else
	{
		System.exit(0); //future plan: maybe show a menu of things they could do, like change their name 
	}
}
//method for an account that does not exist
public static void accountNotFound(RandomAccessFile file, Random rand, int numAccounts) throws IOException
{
	Scanner keyboard= new Scanner(System.in);
	System.out.println("Your account number was not found!");
	int checkValidAccount;
	int newAccountNum;
	int accountIndex;
	do
	{
			newAccountNum = rand.nextInt(10000);
			checkValidAccount = Driver.findAccount(file, newAccountNum, numAccounts);
					
	}while(checkValidAccount != -1);
	
	System.out.println("Here is your new account number: " + newAccountNum);
	System.out.println("Enter your last name: ");
	String lastName = keyboard.nextLine();
	
	System.out.println("Enter your first name: ");
	String firstName = keyboard.nextLine();
	
	
	BankAccount tempAccount = new BankAccount(newAccountNum, lastName , firstName , 0);
	file.seek(numAccounts * BankAccount.TOTAL_BYTES);
//	tempAccount.writeToFile(file);
//	System.out.println("numAccounts before increment: " + numAccounts);
	Driver.addAccount(file, tempAccount, numAccounts);
	
	numAccounts++;
	accountIndex = numAccounts - 1; 
	System.out.println("Would you like to deposit? Yes or No?"); 
	String answer = keyboard.nextLine();
	
	if(answer.equalsIgnoreCase("yes"))
	{
		System.out.println("Enter your deposit: ");
		double deposit = keyboard.nextDouble();
		keyboard.nextLine();
		Driver.updateBalance(file, accountIndex, deposit);
	}
	else 
		System.exit(0); //future plan: maybe show a menu of things they could do, like change their name 
}
}