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

I don't feel like there were too many trade-offs to be made in the process of
writing this program. If I had been more strict on my own time, 
or had I completed this task in one sitting rather than a few
pieces at a time, I am sure I would
have had quite the similar outcome, minus a few tests that I had accounted 
for in the evaluator prior to explicitly testing them.

Given a larger amount 
of time to dedicate to this project, however, a fully interactive program that
allows a user to create and evaluate policies and claims quickly and easily
would have been the obvious next step. While the main rules and 
boundaries are already accounted for in tests and the logic of the
evaluator, more comprehensive testing could also have taken place, including
amounts with decimal values, multiple covered incidents, zero deductible,
or multiple conditions for rejection.