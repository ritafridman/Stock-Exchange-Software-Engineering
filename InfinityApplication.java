package Infinity;

import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;

import stockexchange.client.StockCommandType;
import stockexchange.client.StockExchangeClient;
import stockexchange.client.StockExchangeCommand;
import stockexchange.client.StockExchangeTransaction;


public class InfinityApplication{

	public static Scanner s = new Scanner(System.in);
	

	private StockExchangeClient client;
	
	Timer myTimer = new Timer();
	
	TimerTask task = new TimerTask() {

		@Override
		public void run() {
			checkTransactionsStatus(transactionFacade, client);
		}
	};


	public InfinityApplication(StockExchangeClient client) {
		super();
		this.client = client;
	}


	@Autowired
	private InvestorFacade investorFacade;

	@Autowired
	private AccountFacade accountFacade;

	@Autowired
	private TransactionFacade transactionFacade;
	
	

	public void execute() throws InterruptedException{
		
		startTimer();
		int choice , operation, select;
		Long userId , userIdForCheck;
		String password , passwordForCheck;
		boolean fContinue = true;


		Account account = new Account();
		Account accountToCreate = new Account();
		Investor investor = new Investor();
		Investor investorToCreate = new Investor();
		Transaction transaction;
		Transaction transactionForStockb = new Transaction();



		System.out.println("---------------------------------------------------");
		System.out.println("Hello! \n --Welcome to Infinity investments house--");
		System.out.println("---------------------------------------------------");
		System.out.println("\n---------------------------------------------------");
		System.out.println("Please choose one of the options mentioned below:");
		System.out.println("---------------------------------------------------");
		System.out.println("press 1 if you are a client");
		System.out.println("press 2 if you are a stockbroker\n");
		
		choice = s.nextInt();

		switch(choice){

		// --------------------- Client ---------------------
		case 1:
		{
			int month;
			int todayMonth =  Calendar.getInstance().get(Calendar.MONTH);
			System.out.println("enter user id: ");
			userId = s.nextLong();
			System.out.println("enter password:");
			password = s.next();
			s.nextLine();

			account = accountFacade.viewAccount(userId);
			investor = investorFacade.viewInvestorDetails(userId);
			
			
			
			
			userIdForCheck = account.getUserId();
			if (userIdForCheck != userId){
				System.out.println("Account do not exist");
				fContinue = false;
				break;
			}
			passwordForCheck = account.getPassword(); 
			if(!(passwordForCheck.equals(password))){
				System.out.println("Wrong password!");
				fContinue = false;
				break;
			}

			month = account.getLastPasswordUpdate();
			if(month != todayMonth){
				
				System.err.println("Password expired!!!\n");
			
				updatePassword(account, accountFacade);
			}
			
			System.out.println("\nLog in succeeded :)");


			do {
				System.out.println("\nOperations:");
				System.out.println("---------------------------------------------------");
				System.out.println("1 - view details");
				System.out.println("2 - update details");
				System.out.println("3 - propsal for trade");
				System.out.println("0 - exit");
				operation = s.nextInt();

				switch(operation){
				case 1: // -------- View Details --------
				{	
					viewAccountDetails(userId, account, investor);

					break;
				}

				case 2: // -------- Update Details --------
				{
					updateDetails(account, investor ,investorFacade ,accountFacade);
					break;
				}

				case 3: // -------- Proposal For Trade --------
				{

					int i;
					transaction = new Transaction();

					System.out.println("Please enter the details below:");
					System.out.println("---------------------------------------------------");

					System.out.println("Proposal type:");
					System.out.println("press 1 for bid");
					System.out.println("press 2 for ask");
					i = s.nextInt();

					if(i==1)
						transaction.setTType("BID");
					else if(i==2)
						transaction.setTType("ASK");

					
					client.getStocksId().stream().map(id->client.getQuote(id)).forEach(s->System.out.println(s));
					System.out.println("\nshare Id:");
					transaction.setShareName(s.next());

					System.out.print("Stock amount:");
					transaction.setStockAmount(s.nextInt());

					System.out.print("Min price:");
					transaction.setMinPrice(s.nextDouble());

					System.out.print("Max price:");
					transaction.setMaxPrice(s.nextDouble());

					transaction.setUserId(investor.getUserId());
					transaction.setTStatus("COMMITTED");

					transactionFacade.saveTransaction(transaction);
					System.out.println("\nProposal was sent to the stockbroker");

					break;
				}

				case 0:
					fContinue = false;
					break;


				default:
					System.out.println("Invalid option.");
					break;
				}

				System.out.println();
			}while(fContinue);
			System.out.println("Goodbye !");
			s.close();
			myTimer.cancel();
			break;

		}

		// --------------------- Stockbroker ---------------------
		case 2:
		{
			do{
				System.out.println("Please choose one of the options:");
				System.out.println("---------------------------------------------------");
				System.out.println("1 - If you want to open a new account");
				System.out.println("2 - if you want to use an existing account");
				System.out.println("0 - exit");
				select = s.nextInt();


				switch(select){
				case 1:// -------- Open new Account --------
				{
					System.out.println("please enter the details below:");
					System.out.println("---------------------------------------------------");
					System.out.print("user name:");
					investorToCreate.setUserName(s.next());
					System.out.print("user id:");
					long userIdToCreate = s.nextLong();
					investorToCreate.setUserId(userIdToCreate);
					accountToCreate.setUserId(userIdToCreate);
					s.nextLine();
					System.out.print("gender:");
					investorToCreate.setGender(s.next());
					System.out.print("password:");
					accountToCreate.setPassword(s.next());
					accountToCreate.setLastPasswordUpdate(Calendar.getInstance().get(Calendar.MONTH));

					investorFacade.saveInvestorDetails(investorToCreate);
					accountFacade.saveAccountDetails(accountToCreate);
					System.out.println("\nAccount was created succesfully");

					break;
				}
				case 2:
				{


					System.out.println("\nenter user id: ");
					userId = s.nextLong();
					s.nextLine();

					accountToCreate = accountFacade.viewAccount(userId);
					userIdForCheck = accountToCreate.getUserId();
					if (userIdForCheck != userId){
						System.out.println("Error: wrong id!");
						fContinue = false;
						break;
					}

					System.err.println("\n\nLog in succeeded :)\n");
					investorToCreate = investorFacade.viewInvestorDetails(userId);

					do{
						System.out.println("\nOperations:");
						System.out.println("---------------------------------------------------");
						System.out.println("1 - view details");
						System.out.println("2 - update details");
						System.out.println("3 - submit proposal");
						System.out.println("4 - To switch user");
						System.out.println("0 - exit");

						operation = s.nextInt();

						switch(operation){
						case 1: // -------- View Details --------
						{
							viewAccountDetails(userIdForCheck, accountToCreate, investorToCreate);
							break;
						}

						case 2: // -------- Update Details --------
						{
							updateDetails(accountToCreate, investorToCreate ,investorFacade ,accountFacade);
							break;
						}

						case 3:// -------- Submit Proposal --------
						{
							List<Transaction> transactionList;
							Long accouUsId;
							Long commandId = null;
							int listSize;
							String shareName; 
							double minPrice;
							double maxPrice;
							int stockAmount;


							String myId = "infinityInvestments"; 
							accouUsId = accountToCreate.getUserId();
							transactionList = transactionFacade.getAllTransactionByUserId(accouUsId);
							listSize = transactionList.size()-1;
							accouUsId = accountToCreate.getUserId();

							transactionForStockb = transactionList.get(listSize);
							shareName = transactionForStockb.getShareName();
							minPrice = transactionForStockb.getMinPrice();
							maxPrice = transactionForStockb.getMaxPrice();
							stockAmount = transactionForStockb.getStockAmount();


							client.startListening(myId, (o, event)->System.err.println(event));
							String tType =transactionForStockb.getTType();
							if( tType.equals("BID")){
								commandId = client.sendCommand(new StockExchangeCommand(StockCommandType.BID,myId, shareName, minPrice,maxPrice,stockAmount ));
							}

							if(tType.equals("ASK")){
								commandId = client.sendCommand(new StockExchangeCommand(StockCommandType.ASK,myId, shareName, minPrice,maxPrice,stockAmount ));

							}

							transactionForStockb.setCommandId(commandId);

							transactionFacade.saveTransaction(transactionForStockb);


							System.out.println("proposal was submited");

							break;
						}
						case 4:
						{
							System.out.println("\nenter user id: ");
							userId = s.nextLong();
							s.nextLine();

							accountToCreate = accountFacade.viewAccount(userId);
							investorToCreate = investorFacade.viewInvestorDetails(userId);
							userIdForCheck = accountToCreate.getUserId();
							if (userIdForCheck != userId){
								System.out.println("Error: wrong id!");
								fContinue = false;
							}
							break;
						}
						case 0:
							fContinue = false;
							break;

						default:
							System.out.println("Invalid option.");
							break;
						}
						System.out.println();
					}while(fContinue);
				}
				case 0:
					fContinue = false;
					break;

				default:
					System.out.println("Invalid option.");
					break;
				}
				System.out.println();
			}while(fContinue);
			System.out.println("Goodbye !");
			s.close();
			myTimer.cancel();
			break;
		}
		}
	}


	public static void updateDetails(Account account , Investor investor , InvestorFacade investorFacade , AccountFacade accountFacade){
		int answer;

		System.out.println("Please choose one of the options:");
		System.out.println("---------------------------------------------------");
		System.out.println("for user name update press 1");
		System.out.println("for user id update press 2");
		System.out.println("for gender update press 3");
		System.out.println("for password update press 4");
		answer = s.nextInt();

		if (answer == 1){
			System.out.print("\nPlease enter the new user name:");
			System.out.println("\n---------------------------------------------------");
			investor.setUserName(s.next());
			investorFacade.saveInvestorDetails(investor);
			System.out.print("\nNew user name was saved");
		}

		else if (answer == 2){
			System.out.print("\nPlease enter the new id:");
			System.out.println("\n---------------------------------------------------");
			Long newId = s.nextLong();
			investor.setUserId(newId);
			account.setUserId(newId);
			investorFacade.saveInvestorDetails(investor);
			accountFacade.saveAccountDetails(account);
			System.out.print("\nNew user id was saved");
		}
		else if (answer == 3){
			System.out.print("\nPlease enter the new gender:");
			System.out.println("\n---------------------------------------------------");
			investor.setGender(s.next());
			investorFacade.saveInvestorDetails(investor);
			System.out.print("\nNew gender was saved");
		}
		else if (answer == 4){
			updatePassword(account , accountFacade);
		}	
	}

	public static void updatePassword(Account account , AccountFacade accountFacade){
		System.out.print("\nPlease enter the new password:");
		System.out.println("\n---------------------------------------------------");
		account.setPassword(s.next());
		accountFacade.saveAccountDetails(account);
		System.out.print("\nNew password was saved");
		account.setLastPasswordUpdate( Calendar.getInstance().get(Calendar.MONTH));
	}
	
	public void checkTransactionsStatus(TransactionFacade transactionFacade, StockExchangeClient client){

		List <Transaction>transactionList = transactionFacade.getAllTransaction();
		List <StockExchangeTransaction>list;
		int size = transactionList.size();
		String tStatus;
		Long commandId;

		if(!transactionList.isEmpty()){

			for (int i=0; i<size; i++){
				Transaction t = (Transaction)transactionList.get(i);
				tStatus= t.getTStatus();
				if(tStatus.equals("COMMITTED")){
					commandId = t.getCommandId();
					if(commandId != null){
						list = client.getTransactionsForCommand(commandId);
						if(!list.isEmpty()){
							t.setTStatus("PERFORMED");
							transactionFacade.saveTransaction(t);
						}
					}//performed
				}//committed
			}//for
		}//transactionList empty
	}

	public void startTimer(){
		myTimer.scheduleAtFixedRate(task, 10000, 20000);
	}

	public void viewAccountDetails(Long userId, Account account, Investor investor) {
		List <StockExchangeTransaction>performedTransactions;
		Long commandId;
		String tStatus;
		System.out.println("Account details:");
		System.out.println(account.toString());
		System.out.println("Investor details:");
		System.out.println(investor.toString());

		List <Transaction>userTransactions = transactionFacade.getAllTransaction();
		StockExchangeTransaction set;

		for(int j=0; j<userTransactions.size(); j++){
			Transaction t = (Transaction)userTransactions.get(j);	
			Long tUserId = t.getUserId();


			if(userId.longValue() == tUserId.longValue()){
				System.out.println("Transaction details:");
				System.out.println(t.toString());

				tStatus = t.getTStatus();
				if(tStatus.equals("PERFORMED")){

					commandId = t.getCommandId();
					performedTransactions = client.getTransactionsForCommand(commandId);

					for(int k=0; k < performedTransactions.size(); k++){
						set = performedTransactions.get(k);
						System.out.println("\n ~The transaction was performed! here are the details:~");
						System.out.println("Actual amount: "+ set.getActualAmount());
						System.out.println("Actual price: " + set.getActualPrice());
						System.out.println();
					}//k
				}//performed
			}//userId
		}//j
	}


}




