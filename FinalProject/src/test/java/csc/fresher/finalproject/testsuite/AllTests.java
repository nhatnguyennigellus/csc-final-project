package csc.fresher.finalproject.testsuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import csc.fresher.finalproject.service.AccountServiceTest;
import csc.fresher.finalproject.service.InterestRateServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	AccountServiceTest.class,
//	InterestRateServiceTest.class
})
public class AllTests {

}
