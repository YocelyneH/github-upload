/*
 * Class description:
 * 	 Class made for a bank account that holds information for a persons account
 * ALGORITHM:
 * 1.Create a Bank Account class with four instance variables:
 *		an account number (int), a last name (String), a first name (String) and the balance (double)
 * 2.Create default constructor and full constructor 
 * 3.Accessor methods
 * 
 * 4. and mutator methods
 * 5. Create a method that shortens the a name 
 * 		if the length of the name is bigger the the NAME_LENGTH //constant
 * 		shorten the name using substring 
 * 		else
 *  		return the name
 *  6. Create a method that extends the name
 *  	if the name is bigger than the length
 *  		then add whitespaces to the name using a for loop
 *  	else 
 * 			return the name
 * 7. Create method to write to the file that takes in a RandomAccessFile
 * 		using the methods
 * 			writeInt, writeUTF, writeDouble
 * 8. Create method to read to the file that takes in a RandomAccessFile
 * 		using the methods
 * 			readInt, readUTF, readDouble
 * 9. Create toString and equals method
 */

package ExtraCredit;
import java.io.*;
public class BankAccount
{
	private int accountNum;
	private String lastName;
	private String firstName;
	private double balance;
	
	private static final int NAME_BYTES = 10; //bytes for a string of length 8
	private static final int ACCOUNT_BYTES = 4;
	private static final int BALANCE_BYTES = 8; 
	private static final int NAME_LENGTH = 8; //character max for each string
	
	public static final int TOTAL_BYTES = NAME_BYTES * 2 +  ACCOUNT_BYTES + BALANCE_BYTES ; //32
	//used to move to the balance of each account
	public static final int BALANCE_OFFSET = NAME_BYTES * 2 +  ACCOUNT_BYTES; // 24
	
	
	public BankAccount()
	{
		accountNum = 0;
		lastName ="lastName";
		firstName ="firstName";
		balance = 0;
	}
	// full constructor
	public BankAccount(int account, String last, String first, double balance)
	{
		accountNum = account;
		setLastName(last);
		setFirstName(first);
		this.balance = balance;
	}
	// shorten the name
	public String shortenName(String name)
	{
		if(name.length() > NAME_LENGTH)
		{
			return name.substring(0, NAME_LENGTH);
		}
		else
			return name;
	}
	// extend the name
	public String extendName(String name)
	{
		if(name.length() < NAME_LENGTH)
		{
			int diff = NAME_LENGTH - name.length();
			for(int i = 0; i < diff; i++)
			{
				name = name + " ";
			}
			return name;
		}
		return name;
	}
	// write to the file
	public void writeToFile(RandomAccessFile file) throws IOException
	{
		file.writeInt(accountNum);
		file.writeUTF(lastName);
		file.writeUTF(firstName);
		file.writeDouble(balance);
	}
	// read from the file
	public void readFile(RandomAccessFile file) throws IOException
	{
		accountNum = file.readInt();
		lastName = file.readUTF();
		firstName = file.readUTF();
		balance = file.readDouble();
		
		
	}
	//__________SETTERS__________
	public void setAccountNum(int newAccountNum)
	{
		accountNum=newAccountNum;
	}
	public void setLastName(String last)
	{
		if(last.length() > NAME_LENGTH)
			lastName = shortenName(last);
		
		else if(last.length() < NAME_LENGTH)
			lastName = extendName(last);
		
		else
			lastName = last;
	}
	public void setFirstName(String first)
	{
		if(first.length() > NAME_LENGTH)
			firstName = shortenName(first);
		
		else if(first.length() < NAME_LENGTH)
			firstName = extendName(first);
		
		else
			firstName = first;
	}
	public void setBalance(double newBalance)
	{
		balance=newBalance;
	}
	//__________GETTERS__________
	public int getAccountNum()
	{
		return accountNum;
	}
	public String getLastName()
	{
		return lastName;
	}
	public String getFirstName()
	{
		return firstName;
	}
	public double getBalance()
	{
		return balance;
	}
	// toString
	public String toString()
	{
		String str = "Account number: " + getAccountNum() + "\nName: " + getFirstName() + " " + getLastName() + "\nBalance: " + getBalance();
		return str;
	}
	// equals method 
	public boolean equals(BankAccount otherAccount)
	{
		if(this.accountNum != otherAccount.getAccountNum())
			return false;
		if(this.lastName.equalsIgnoreCase(otherAccount.getLastName()) && this.firstName.equalsIgnoreCase(otherAccount.getFirstName()) && this.balance == otherAccount.getBalance())
				return true;
		return false;
		
	}
}