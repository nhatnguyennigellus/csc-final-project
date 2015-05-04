package csc.fresher.finalproject.testsuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import csc.fresher.finalproject.service.AccountServiceTest;
import csc.fresher.finalproject.service.InterestRateServiceTest;
import csc.fresher.finalproject.service.TransactionServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	AccountServiceTest.class,
	InterestRateServiceTest.class,
	TransactionServiceTest.class
})
public class AllTests {

}
