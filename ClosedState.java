public class ClosedState implements AccountState{
    @Override
    public void deposit(Account account, double amount){
        System.out.println("You cannot deposit on a closed account!");
        System.out.println(account.toString());
    }

    @Override
    public void withdraw(Account account, double amount){
        System.out.println("You cannot withdraw on a closed account!");
        System.out.println(account.toString());
    }

    @Override
    public void suspend(Account account){
        System.out.println("You cannot suspend closed account!");
    }

    @Override
    public void activate(Account account){
        System.out.println("You can not activate closed account!");
    }

    @Override
    public void close(Account account){
        System.out.println("Account is already closed!");
    }
}