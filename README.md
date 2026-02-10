# State Design Pattern - Bank Account Management System

## Overview

This project demonstrates the implementation of the **State Design Pattern** to manage different states of customer accounts in a banking system. The pattern provides a clean, maintainable approach to handling account state transitions and their associated behaviors without using conditional statements (if-else or switch).

## Problem Statement

A bank needs to manage different states of customer accounts, including active, suspended, and closed. Each state has specific rules and restrictions regarding allowed operations, and accounts have associated attributes like account number and balance.

### Account State Rules:
- **Active accounts**: Allow deposits and withdrawals
- **Suspended accounts**: Disallow deposits and withdrawals transactions, but allow viewing account information
- **Closed accounts**: Disallow all transactions and viewing of account information

### Challenge:
The traditional approach relies on conditional statements within the Account class to check the account state and determine valid actions. This becomes cumbersome and error-prone as the number of states and their associated logic grows.

## Solution: State Design Pattern

The State Pattern encapsulates varying behavior for the same object based on its internal state. The object appears to change its class when its state changes.

### Key Benefits:
✅ **Eliminates conditional logic** - No if-else or switch statements  
✅ **Improved maintainability** - State-specific logic is isolated  
✅ **Easy to extend** - New states can be added without modifying existing code  
✅ **Single Responsibility** - Each state class handles its own behavior  
✅ **Open/Closed Principle** - Code is open for extension, closed for modification  

## Architecture

### State Transition Logic

```
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│    Active ──────suspend()──────> Suspended                  │
│      ▲                              │                       │
│      │                              │                       │
│      └──────activate()──────────────┘                       │
│                                                             │
│    Active ──────close()──────> Closed                       │
│                                  │                          │
│    Suspended ────close()────────>│                          │
│                                  │                          │
│    Closed: No state transitions allowed                     │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Class Diagram

```
┌──────────────────────────────────┐
│      AccountState (Interface)    │
├──────────────────────────────────┤
│ + deposit()                      │
│ + withdraw()                     │
│ + activate()                     │
│ + suspend()                      │
│ + close()                        │
└──────────┬───────────────────────┘
           │
    ┌──────┴──────┬─────────────┐
    │             │             │
┌───▼────────┐ ┌──▼───────────┐ ┌──▼─────────┐
│ActiveState │ │SuspendedState│ │ClosedState │
├────────────┤ ├──────────────┤ ├────────────┤
│ Implements │ │ Implements   │ │ Implements │
│ all methods│ │ all methods  │ │ all methods│
└────────────┘ └──────────────┘ └────────────┘

         ┌───────────────────────────────┐
         │        Account (Context)      │
         ├───────────────────────────────┤
         │ - accountNumber: String       │
         │ - balance: Double             │
         │ - accountState: AccountState  │
         ├───────────────────────────────┤
         │ + deposit()                   │
         │ + withdraw()                  │
         │ + activate()                  │
         │ + suspend()                   │
         │ + close()                     │
         │ + toString()                  │
         └───────────────────────────────┘
```

## Components

### 1. AccountState Interface
Defines the contract for all account states with methods for common actions:

```java
public interface AccountState {
    void deposit(Account account, double amount);
    void withdraw(Account account, double amount);
    void activate(Account account);
    void suspend(Account account);
    void close(Account account);
}
```

### 2. ActiveState Class
Represents an active account that allows deposits and withdrawals.

**Allowed Operations:**
- ✅ Deposit money
- ✅ Withdraw money
- ✅ Suspend account (transitions to SuspendedState)
- ✅ Close account (transitions to ClosedState)
- ❌ Activate (already active)

### 3. SuspendedState Class
Represents a suspended account that blocks transactions but allows state transitions.

**Allowed Operations:**
- ❌ Deposit money
- ❌ Withdraw money
- ✅ Activate account (transitions back to ActiveState)
- ✅ Close account (transitions to ClosedState)
- ❌ Suspend (already suspended)

### 4. ClosedState Class
Represents a closed account with no operations allowed.

**Allowed Operations:**
- ❌ Deposit money
- ❌ Withdraw money
- ❌ Activate account
- ❌ Suspend account
- ❌ Close (already closed)

### 5. Account Class (Context)
The main class that delegates all state-specific operations to the current AccountState object.

**Composition:**
- `accountNumber: String` - Unique account identifier
- `balance: Double` - Current account balance
- `accountState: AccountState` - Current state of the account

**Methods:**
- `deposit(double depositAmount)` - Delegates to current state
- `withdraw(double withdrawAmount)` - Delegates to current state
- `activate()` - Delegates to current state
- `suspend()` - Delegates to current state
- `close()` - Delegates to current state
- `toString()` - Displays account number and balance

### 6. AccountTest Class
Comprehensive test class demonstrating all operations and state transitions.

## Usage Example

```java
// Create account with initial balance - initialized in Active state
Account myAccount = new Account("1234", 10000.0);

// Try to activate already active account
myAccount.activate(); 
// Output: "Account is already activated!"

// Suspend the account
myAccount.suspend(); 
// Output: "Account is suspended!"

// Activate the account
myAccount.activate(); 
// Output: "Account is activated!"

// Deposit to the account
myAccount.deposit(1000.0); 
// Output: Account{accountNumber='1234', balance=11000.0}

// Withdraw from the account
myAccount.withdraw(100.0); 
// Output: Account{accountNumber='1234', balance=10900.0}

// Close the account
myAccount.close(); 
// Output: "Account is closed!"

// Try to activate closed account
myAccount.activate(); 
// Output: "You cannot activate a closed account!"

// Try to suspend closed account
myAccount.suspend(); 
// Output: "You cannot suspend a closed account!"

// Try to withdraw from closed account
myAccount.withdraw(500.0); 
// Output: "You cannot withdraw on a closed account!"
//         Account{accountNumber='1234', balance=10900.0}

// Try to deposit to closed account
myAccount.deposit(1000.0); 
// Output: "You cannot deposit on a closed account!"
//         Account{accountNumber='1234', balance=10900.0}
```

## Files Structure

```
StateDesignPatternLabActivity/
├── AccountState.java          # Interface defining all state behaviors
├── ActiveState.java           # Concrete state for active accounts
├── SuspendedState.java        # Concrete state for suspended accounts
├── ClosedState.java           # Concrete state for closed accounts
├── Account.java               # Context class delegating to states
├── AccountTest.java           # Test class demonstrating usage
└── README.md                  # This file
```

## Design Pattern Principles Applied

### Single Responsibility Principle (SRP)
Each state class is responsible for defining behavior specific to that state.

### Open/Closed Principle (OCP)
The system is open for extension (new states can be added) but closed for modification (existing state classes don't need to change).

### Dependency Inversion Principle (DIP)
The Account class depends on the AccountState abstraction, not on concrete state implementations.

### Composition Over Inheritance
States are composed into the Account object rather than using inheritance hierarchies.

## Key Features

- 🎯 **No Conditional Logic** - All state transitions handled by state objects
- 🔄 **Clean State Transitions** - Each state knows which states it can transition to
- 📊 **Account Tracking** - Maintains account number and balance throughout state changes
- ✨ **User-Friendly Messages** - Clear feedback for all operations
- 🧪 **Comprehensive Testing** - Complete test suite covering all scenarios
- 📖 **Well-Documented** - Clear comments and documentation throughout

## How It Works

1. **Account Initialization**: Account starts in Active state
2. **Operation Delegation**: When a method is called on Account, it delegates to the current state
3. **State Behavior**: Each state handles the operation according to its rules
4. **State Transition**: If applicable, the state changes the Account's state reference
5. **Polymorphism**: Different behaviors without conditional logic through method overriding

## Advantages Over Traditional Approach

| Aspect | Traditional (If-Else) | State Pattern |
|--------|----------------------|---------------|
| Code Complexity | Increases with states | Remains constant |
| Maintainability | Difficult | Easy |
| Adding New States | Modify existing code | Create new class |
| State Logic | Scattered | Encapsulated |
| Testing | Harder | Easier |
| Extensibility | Limited | Excellent |

## Real-World Applications

The State Pattern is commonly used in:
- 🏦 Banking systems (account states)
- 📱 Mobile applications (app lifecycle)
- 🎮 Game development (character states)
- 🚦 Traffic lights and state machines
- 📋 Workflow management systems
- 🛒 E-commerce order processing

## Conclusion

The State Design Pattern provides an elegant solution for managing object behavior that varies based on internal state. This implementation demonstrates how to build a maintainable, scalable banking system that can easily accommodate new account states and behaviors without modifying existing code.

## Author
**JLNerecina** - State Design Pattern Software Engineering 2 Lab Seatwork

## Date Created
February 10, 2026
