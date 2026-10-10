## Requirements

- JDK 8 or newer
- Apache Maven 3.9.x
- Java and Maven available on your terminal's PATH

Maven downloads the required dependencies during the first build.

## Run the application

Open a terminal in the project directory and run:
```bash
mvn clean compile
java -cp target/classes Main
```

The application evaluates the example policy and claim defined in
`src/main/java/Main.java`, then prints the approval status, reason
code, and payout.

Expected output for the current example:

```text
Claim evaluated successfully
Was the claim approved: false
Reason: POLICY_INACTIVE
Payout Amount: 0
```

The rejection is expected because the incident occurred before the
policy's start date.

To evaluate a different example, edit the policy or claim in
`Main.java`, then repeat the build and run commands. The application
does not currently accept interactive input or command-line arguments.

## Run the tests

From the project directory, run:

```bash
mvn test
```

This executes the JUnit tests in `src/test/java/ClaimEvaluatorTest.java`

## Reflection

For this program, BigDecimal was decided to be used for money calculations to 
increase accuracy, and enums were used for fixed values to maintain simplicity. 
I used a separate evaluator class to make rules easy to test, and a 
concrete demo kept the scope of the project smaller, though
this does disallow any interactive input. The dates on 
policies are inclusive and zero-payout claims are counted as unapproved. The
evaluator also assumes valid input and that the given policy ID matches
the claim's policy ID.

Given a larger amount 
of time to dedicate to this project, a fully interactive program that
allows a user to create and evaluate policies and claims quickly and easily
would have been the obvious next step. While the main rules and 
boundaries are already accounted for in tests and the logic of the
evaluator, more comprehensive testing could also have taken place, including
amounts with decimal values, multiple covered incidents, zero deductible,
or multiple conditions for rejection.